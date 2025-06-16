package com.example.MedicExpress.Service;

import com.example.MedicExpress.Model.*;
import com.example.MedicExpress.Repository.*;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
        return prescriptionRepository.findByPatientId(patientId);
    }

    public List<OrderEntity> getOrdersForPatient(Long patientId) {
        return orderRepository.findByPatientId(patientId);
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

        // Création d'une commande avec les entités récupérées simplement
        OrderEntity order = new OrderEntity();
        order.setDate(new java.sql.Date(System.currentTimeMillis()));
        order.setStatus("Pending");

        order.setDeliveryDriver(deliveryDriverRepository.findById(request.getDeliveryDriverId()).get());
        order.setPharmacy(pharmacyRepository.findById(request.getPharmacyId()).get());
        order.setPatient(patientRepository.findById(request.getPatientId()).get());
        order.setPrescription(prescriptionRepository.findById(request.getPrescriptionId()).get());

        // code aleatoire
        int randomCode = (int)(Math.random() * 900000) + 100000; // entre 100000 et 999999
        order.setCode(String.valueOf(randomCode));

        order = orderRepository.save(order); // Sauvegarde avant génération du QR

        try {
            String qrText = "https://votre-domaine.com/verify-order/" + order.getId();
            String qrCodeBase64 = QRCodeGenerator.generateQRCodeBase64(qrText, 200, 200);
            order.setQrcode(qrCodeBase64);
            NotificationEntity notif = new NotificationEntity();
            notif.setDeliveryDriver(order.getDeliveryDriver());
            notif.setOrder(order);
            notif.setMessage("Nouvelle commande disponible");
            notificationRepository.save(notif);
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Erreur QR code");
        }


    }

    public List<OrderEntity> getDeliveredOrdersForPatient(Long patientId) {
        return orderRepository.findByStatusIgnoreCaseAndPatientId("DELIVERED", patientId);
    }



}
