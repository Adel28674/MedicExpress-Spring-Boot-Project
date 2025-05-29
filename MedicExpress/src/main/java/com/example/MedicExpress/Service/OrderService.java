package com.example.MedicExpress.Service;

import com.example.MedicExpress.Model.*;
import com.example.MedicExpress.Repository.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryDriverRepository deliveryDriverRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

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
        order.setStatus("WAITING_FOR_DRIVER");

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
            return orderRepository.save(order); // mise à jour avec le QR
        } catch (Exception e) {
            throw new RuntimeException("Erreur QR code");
        }


    }



    //protéger et sécuriser une série d'opérations qui touchent la base de données

    @Transactional
    public OrderEntity updateOrderStatus(Long orderId) {
        // Récupérer la commande
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Lire le statut actuel
        String currentStatus = order.getStatus();

        // Logique de changement de statut
        if (currentStatus.equalsIgnoreCase("WAITING_FOR_DRIVER")) {
            order.setStatus("PENDING");
        }else if (currentStatus.equalsIgnoreCase("PENDING")) {
            order.setStatus("IN_DELIVERY");
        } else if (currentStatus.equalsIgnoreCase("IN_DELIVERY")) {
            order.setStatus("DELIVERED");
        } else {
            throw new RuntimeException("Invalid status transition for order id: " + orderId);
        }

        // code aleatoire
        int randomCode = (int)(Math.random() * 900000) + 100000; // entre 100000 et 999999
        order.setCode(String.valueOf(randomCode));

        order = orderRepository.save(order); // Sauvegarde avant génération du QR


        // Sauvegarder la commande mise à jour

        return orderRepository.save(order);
    }


}