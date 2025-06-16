package com.example.MedicExpress.Service;

import com.example.MedicExpress.Model.*;
import com.example.MedicExpress.Repository.DeliveryDriverRepository;
import com.example.MedicExpress.Repository.NotificationRepository;
import com.example.MedicExpress.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryDriverService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryDriverRepository deliveryDriverRepository;


    @Autowired
    private NotificationRepository notificationRepository;

    public List<NotificationEntity> getNotificationsForDriver(Long driverId) {
        return notificationRepository.findByDeliveryDriverId(driverId);
    }


    public ResponseEntity<String> acceptOrder(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));

        if (order.getStatus().equals(OrderStatus.PENDING_DRIVER_RESPONSE)) {
            return ResponseEntity.badRequest().body("La commande n'est pas en attente.");
        }

        order.setStatus(String.valueOf(OrderStatus.WAITING_FOR_DRIVER));

        try {
            String qrText = "https://votre-domaine.com/verify-order/" + order.getId();
            String qrCodeBase64 = QRCodeGenerator.generateQRCodeBase64(qrText, 200, 200);
            order.setQrcode(qrCodeBase64);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du QR code");
        }

        orderRepository.save(order);
        return ResponseEntity.ok("La commande a été accepté");
    }

    public ResponseEntity<String> declineOrder(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));

        if (order.getStatus().equals(OrderStatus.PENDING_DRIVER_RESPONSE)) {
            return ResponseEntity.badRequest().body("La commande n'est pas en attente.");
        }

        List<DeliveryDriverEntity> drivers = deliveryDriverRepository.findAll();
        for (DeliveryDriverEntity newDriver : drivers) {
            if (!newDriver.getId().equals(order.getDeliveryDriver().getId())) {
                order.setDeliveryDriver(newDriver);
                order.setStatus(String.valueOf(OrderStatus.PENDING_DRIVER_RESPONSE));
                return ResponseEntity.ok("L'order a été déclinée");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun autre livreur disponible.");
    }
}
