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
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public OrderEntity createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
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


    public ResponseEntity<List<NotificationEntity>> getNotificationsForDriver(@RequestParam Long driverId) {
        return ResponseEntity.ok(orderService.getNotificationsForDriver(driverId));
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptOrder(@RequestParam Long orderId) {
        orderService.acceptOrder(orderId);
        return ResponseEntity.ok("Commande acceptée");
    }

    @PostMapping("/refuse")
    public ResponseEntity<String> refuseOrder(@RequestParam Long orderId) {
        orderService.refuseOrder(orderId);
        return ResponseEntity.ok("Commande refusée");
    }


}
