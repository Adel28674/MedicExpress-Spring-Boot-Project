package com.example.MedicExpress.Service;

import com.example.MedicExpress.Model.*;
import com.example.MedicExpress.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;


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

    public OrderEntity createOrder(String treatmentId, String status) {
        DeliveryDriverEntity deliveryDriver = deliveryDriverRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Delivery Driver not found"));
        PharmacyEntity pharmacy = pharmacyRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Pharmacy not found"));
        PatientEntity patient = patientRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        PrescriptionEntity prescription = prescriptionRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        OrderEntity order = new OrderEntity();
        order.setDate(new java.sql.Date(System.currentTimeMillis()));
        order.setTreatment_id(treatmentId);
        order.setStatus(status);
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
}