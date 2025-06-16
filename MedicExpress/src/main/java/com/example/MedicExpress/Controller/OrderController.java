package com.example.MedicExpress.Controller;

import com.example.MedicExpress.Model.*;
import com.example.MedicExpress.Repository.*;
import com.example.MedicExpress.Service.DoctorService;

import com.example.MedicExpress.Service.OrderService;
import com.example.MedicExpress.Service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryDriverRepository deliveryDriverRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public OrderEntity createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @PostMapping("/{orderId}/accept")
    public ResponseEntity<?> acceptOrder(@PathVariable Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));

        if (order.getStatus() != OrderStatus.PENDING_DRIVER_RESPONSE) {
            return ResponseEntity.badRequest().body("La commande n'est pas en attente.");
        }

        order.setStatus(OrderStatus.WAITING_FOR_DRIVER);

        try {
            String qrText = "https://votre-domaine.com/verify-order/" + order.getId();
            String qrCodeBase64 = QRCodeGenerator.generateQRCodeBase64(qrText, 200, 200);
            order.setQrcode(qrCodeBase64);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du QR code");
        }

        return ResponseEntity.ok(orderRepository.save(order));
    }

    @PostMapping("/{orderId}/refuse")
    public ResponseEntity<?> refuseOrder(@PathVariable Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));

        if (order.getStatus() != OrderStatus.PENDING_DRIVER_RESPONSE) {
            return ResponseEntity.badRequest().body("La commande n'est pas en attente.");
        }

        List<DeliveryDriverEntity> drivers = deliveryDriverRepository.findAll();
        for (DeliveryDriverEntity newDriver : drivers) {
            if (!newDriver.getId().equals(order.getDeliveryDriver().getId())) {
                order.setDeliveryDriver(newDriver);
                order.setStatus(OrderStatus.PENDING_DRIVER_RESPONSE);
                return ResponseEntity.ok(orderRepository.save(order));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun autre livreur disponible.");
    }



    @PutMapping("/updateStatus")
    public OrderEntity updateOrderStatus(@RequestBody UpdateOrderStatusRequest request) {
        return orderService.updateOrderStatus(request.getOrderId());
    }


    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestParam Long orderId, @RequestParam String code) {
        Optional<OrderEntity> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Commande introuvable");
        }

        OrderEntity order = orderOpt.get();

        if (!order.getCode().equals(code)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Code invalide");
        }

        orderService.updateOrderStatus(orderId); // ou passez l'objet directement
        return ResponseEntity.ok("Commande validée !");
    }

    @GetMapping("/by-prescription/{prescriptionId}")
    public ResponseEntity<Map<String, String>> getOrderByPrescription(@PathVariable Long prescriptionId) {
        Optional<OrderEntity> orderOpt = orderRepository.findFirstByPrescriptionIdAndStatusNotIn(
                prescriptionId,
                List.of(OrderStatus.DELIVERED, OrderStatus.CANCELLED)
        );

        if (orderOpt.isPresent()) {
            OrderEntity order = orderOpt.get();
            Map<String, String> response = new HashMap<>();
            response.put("status", order.getStatus().toString());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
