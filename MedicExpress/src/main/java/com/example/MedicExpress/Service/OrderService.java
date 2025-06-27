package com.example.MedicExpress.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MedicExpress.Model.CreateOrderRequest;
import com.example.MedicExpress.Model.OrderEntity;
import com.example.MedicExpress.Model.QRCodeGenerator;
import com.example.MedicExpress.Repository.DeliveryDriverRepository;
import com.example.MedicExpress.Repository.OrderRepository;
import com.example.MedicExpress.Repository.PharmacyRepository;
import com.example.MedicExpress.Repository.PrescriptionRepository;
import com.example.MedicExpress.Repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryDriverRepository deliveryDriverRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public OrderEntity createOrder(CreateOrderRequest request) {

        if (!deliveryDriverRepository.existsById(request.getDeliveryDriverId())) {
            throw new RuntimeException("Livreur introuvable.");
        }

        if (!pharmacyRepository.existsById(request.getPharmacyId())) {
            throw new RuntimeException("Pharmacie introuvable.");
        }

        if (!userRepository.existsById(request.getPatientId())) {
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
        order.setPatient(userRepository.findById(request.getPatientId()).get());
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
        if (currentStatus.equalsIgnoreCase("PENDING")) {
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