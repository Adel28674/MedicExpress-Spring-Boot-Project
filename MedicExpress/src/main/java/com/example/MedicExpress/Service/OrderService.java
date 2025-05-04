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
        DeliveryDriverEntity deliveryDriver = deliveryDriverRepository.findById(request.getDeliveryDriverId())
                .orElseThrow(() -> new RuntimeException("Delivery Driver not found"));

        PharmacyEntity pharmacy = pharmacyRepository.findById(request.getPharmacyId())
                .orElseThrow(() -> new RuntimeException("Pharmacy not found"));

        PatientEntity patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        PrescriptionEntity prescription = prescriptionRepository.findById(request.getPrescriptionId())
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        OrderEntity order = new OrderEntity();
        order.setDate(new java.sql.Date(System.currentTimeMillis()));
        order.setStatus("Pending");
        order.setDeliveryDriver(deliveryDriver);
        order.setPharmacy(pharmacy);
        order.setPatient(patient);
        order.setPrescription(prescription);

        return orderRepository.save(order);
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
        if (currentStatus.equalsIgnoreCase("PENDING")) {
            order.setStatus("READY_FOR_PICKUP");
        } else if (currentStatus.equalsIgnoreCase("READY_FOR_PICKUP")) {
            order.setStatus("IN_DELIVERY");
        } else if (currentStatus.equalsIgnoreCase("IN_DELIVERY")) {
            order.setStatus("DELIVERED");
        } else {
            throw new RuntimeException("Invalid status transition for order id: " + orderId);
        }

        // Sauvegarder la commande mise à jour
        return orderRepository.save(order);
    }

    public String generateOrderQRCode(Long orderId) {
        try {
            // Créer le contenu du QRCode (vous pouvez mettre ce que vous voulez)
            String qrContent = "Order ID: " + orderId;

            // Générer le QRCode
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 250, 250);

            // Convertir en image
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            // Encoder en Base64 pour envoyer facilement en JSON
            byte[] qrImageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(qrImageBytes);

        } catch (WriterException | IOException e) {
            throw new RuntimeException("Could not generate QR Code", e);
        }
    }
}