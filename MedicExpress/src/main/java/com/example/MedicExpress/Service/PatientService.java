package com.example.MedicExpress.Service;

import com.example.MedicExpress.Model.*;
import com.example.MedicExpress.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private NotificationRepository notificationRepository;

    public List<PrescriptionEntity> getPrescriptionsForPatient(Long patientId) {
        return prescriptionRepository.findByPatient_Id(patientId);
    }

    public List<OrderEntity> getOrdersForPatient(Long patientId) {
        return orderRepository.findByPatient(patientId);
    }




    public OrderEntity createOrder(CreateOrderRequest request) {

        if (!deliveryDriverRepository.existsById(request.getDeliveryDriverId())) {
            throw new RuntimeException("Livreur introuvable.");
        }
        if (!pharmacyRepository.existsById(request.getPharmacyId())) {
            throw new RuntimeException("Pharmacie introuvable.");
        }
        if (!patientRepository.existsById(request.getPatientId())) {
            throw new RuntimeException("Patient introuvable.");
        }
        if (!prescriptionRepository.existsById(request.getPrescriptionId())) {
            throw new RuntimeException("Ordonnance introuvable.");
        }

        OrderEntity order = new OrderEntity();
        order.setDate(new java.sql.Date(System.currentTimeMillis()));
        order.setStatus(OrderStatus.PENDING_DRIVER_RESPONSE);

        order.setPharmacy(pharmacyRepository.findById(request.getPharmacyId()).get());
        order.setPatient(request.getPatientId());
        order.setPrescription(prescriptionRepository.findById(request.getPrescriptionId()).get());

        DeliveryDriverEntity driver = deliveryDriverRepository.findById(request.getDeliveryDriverId())
                .orElseThrow(() -> new RuntimeException("Livreur introuvable"));

        order.setDeliveryDriver(driver);
        order.setCode(String.valueOf((int)(Math.random() * 900000) + 100000));

        return orderRepository.save(order);

    }

    public List<OrderEntity> getDeliveredOrdersForPatient(Long patientId) {

        return orderRepository.findByStatusAndPatient("DELIVERED", patientId);
    }



}
