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

        OrderEntity order = new OrderEntity();
        order.setDate(new java.sql.Date(System.currentTimeMillis()));
        order.setStatus(OrderStatus.PENDING_DRIVER_RESPONSE);

        order.setPharmacy(pharmacyRepository.findById(request.getPharmacyId()).get());
        order.setPatient(patientRepository.findById(request.getPatientId()).get());
        order.setPrescription(prescriptionRepository.findById(request.getPrescriptionId()).get());

        DeliveryDriverEntity driver = deliveryDriverRepository.findById(request.getDeliveryDriverId())
                .orElseThrow(() -> new RuntimeException("Livreur introuvable"));

        order.setDeliveryDriver(driver);
        order.setCode(String.valueOf((int)(Math.random() * 900000) + 100000));

        return orderRepository.save(order);

    }



    //protéger et sécuriser une série d'opérations qui touchent la base de données

    @Transactional
    public OrderEntity updateOrderStatus(Long orderId) {
        // Récupérer la commande
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        OrderStatus currentStatus = order.getStatus();


        switch (currentStatus) {
            case PENDING_DRIVER_RESPONSE:
                order.setStatus(OrderStatus.WAITING_FOR_DRIVER);
                break;
            case WAITING_FOR_DRIVER:
                order.setStatus(OrderStatus.IN_DELIVERY);
                break;
            case IN_DELIVERY:
                order.setStatus(OrderStatus.DELIVERED);
                break;
            default:
                throw new RuntimeException("Invalid status transition for order id: " + orderId);
        }

        int randomCode = (int) (Math.random() * 900000) + 100000;
        order.setCode(String.valueOf(randomCode));

        return orderRepository.save(order);
    }



}