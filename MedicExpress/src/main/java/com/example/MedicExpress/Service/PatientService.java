package com.example.MedicExpress.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MedicExpress.Model.CreateOrderRequest;
import com.example.MedicExpress.Model.DeliveryDriverEntity;
import com.example.MedicExpress.Model.MedicamentEntity;
import com.example.MedicExpress.Model.OrderEntity;
import com.example.MedicExpress.Model.PharmacyEntity;
import com.example.MedicExpress.Model.PrescriptionEntity;
import com.example.MedicExpress.Model.QRCodeGenerator;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Repository.DeliveryDriverRepository;
import com.example.MedicExpress.Repository.DoctorRepository;
import com.example.MedicExpress.Repository.OrderRepository;
import com.example.MedicExpress.Repository.PatientRepository;
import com.example.MedicExpress.Repository.PharmacyRepository;
import com.example.MedicExpress.Repository.PrescriptionRepository;
import com.example.MedicExpress.Repository.UserRepository;
import com.example.MedicExpress.SerializationClass.PatientPrescriptionResponse;
import com.google.zxing.WriterException;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private DeliveryDriverRepository deliveryDriverRepository;
    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<PrescriptionEntity> getPrescriptionsForPatient(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }

    public List<PatientPrescriptionResponse> getEnhancedPrescriptionsForPatient(Long patientId) {
        List<PrescriptionEntity> prescriptions = prescriptionRepository.findByPatientId(patientId);
        List<PatientPrescriptionResponse> responses = new ArrayList<>();

        for (PrescriptionEntity prescription : prescriptions) {
            PatientPrescriptionResponse response = new PatientPrescriptionResponse();
            response.setId(prescription.getId());
            response.setStatus("ACTIVE"); // Default status since we don't have this field yet
            response.setDate(java.time.LocalDate.now().toString()); // Default to today since we don't have date field

            // Extraction des informations du docteur
            PatientPrescriptionResponse.DoctorInfo doctorInfo = new PatientPrescriptionResponse.DoctorInfo();
            if (prescription.getDoctor() != null) {
                // Since doctor is now a UserEntity, we can get the info directly
                UserEntity doctorUser = prescription.getDoctor();
                doctorInfo.setId(doctorUser.getId());
                doctorInfo.setName(doctorUser.getName());
                doctorInfo.setFirstName(doctorUser.getFirstName());
                doctorInfo.setEmail(doctorUser.getEmail());
                // Note: RPPS is not available in UserEntity anymore
                // If RPPS is needed, additional lookup in doctors table would be required
                doctorInfo.setRpps(null); // or remove this field from the response
            }
            response.setDoctor(doctorInfo);

            // Get medications
            List<PatientPrescriptionResponse.MedicationInfo> medications = new ArrayList<>();
            if (prescription.getMedicaments() != null) {
                for (MedicamentEntity med : prescription.getMedicaments()) {
                    PatientPrescriptionResponse.MedicationInfo medInfo = new PatientPrescriptionResponse.MedicationInfo();
                    medInfo.setId(med.getId());
                    medInfo.setName(med.getNom());
                    medications.add(medInfo);
                }
            }
            response.setMedications(medications);

            // Check if there's an order for this prescription
            List<OrderEntity> orders = orderRepository.findByPatientId(patientId);
            Optional<OrderEntity> prescriptionOrder = orders.stream()
                    .filter(order -> order.getPrescription() != null && order.getPrescription().getId().equals(prescription.getId()))
                    .findFirst();

            if (prescriptionOrder.isPresent()) {
                response.setHasOrder(true);
                response.setOrderId(prescriptionOrder.get().getId());
                response.setOrderStatus(prescriptionOrder.get().getStatus());
            } else {
                response.setHasOrder(false);
                response.setOrderId(null);
                response.setOrderStatus(null);
            }

            responses.add(response);
        }

        return responses;
    }

    public List<OrderEntity> getOrdersForPatient(Long patientId) {
        return orderRepository.findByPatientId(patientId);
    }

    public OrderEntity createOrder(CreateOrderRequest request) {
        DeliveryDriverEntity deliveryDriver = deliveryDriverRepository.findById(request.getDeliveryDriverId())
                .orElseThrow(() -> new RuntimeException("Delivery Driver not found"));

        PharmacyEntity pharmacy = pharmacyRepository.findById(request.getPharmacyId())
                .orElseThrow(() -> new RuntimeException("Pharmacy not found"));

        // Find user with PATIENT role by ID (same architecture as prescriptions)
        UserEntity patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        
        if (patient.getRole() != com.example.MedicExpress.Model.Role.PATIENT) {
            throw new RuntimeException("User with ID " + request.getPatientId() + " is not a patient");
        }

        PrescriptionEntity prescription = prescriptionRepository.findById(request.getPrescriptionId())
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        OrderEntity order = new OrderEntity();
        order.setDate(new java.sql.Date(System.currentTimeMillis()));
        order.setStatus("Pending");
        order.setDeliveryDriver(deliveryDriver);
        order.setPharmacy(pharmacy);
        order.setPatient(patient);
        order.setPrescription(prescription);

        try {
            String qrText = "Commande ID: " + System.currentTimeMillis(); // ou tout autre identifiant unique
            String qrCodeBase64 = QRCodeGenerator.generateQRCodeBase64(qrText, 200, 200);
            order.setQrcode(qrCodeBase64);
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Erreur lors de la génération du QR code", e);
        }

        return orderRepository.save(order);
    }
}
