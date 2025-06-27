package com.example.MedicExpress.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MedicExpress.Model.CreateOrderRequest;
import com.example.MedicExpress.Model.OrderEntity;
import com.example.MedicExpress.Model.UpdateOrderStatusRequest;
import com.example.MedicExpress.Repository.OrderRepository;
import com.example.MedicExpress.Service.OrderService;

import lombok.RequiredArgsConstructor;


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
        System.out.println("🔍 ORDER DEBUG: createOrder method called");
        System.out.println("🔍 ORDER DEBUG: Request: " + request);
        System.out.println("🔍 ORDER DEBUG: Authentication: " + SecurityContextHolder.getContext().getAuthentication());
        return orderService.createOrder(request);
    }

    @PostMapping("/create-order-test")
    public OrderEntity createOrderTest(@RequestBody CreateOrderRequest request) {
        System.out.println("🔍 ORDER DEBUG: createOrderTest method called");
        System.out.println("🔍 ORDER DEBUG: Request: " + request);
        System.out.println("🔍 ORDER DEBUG: Authentication: " + SecurityContextHolder.getContext().getAuthentication());
        return orderService.createOrder(request);
    }

    @GetMapping("/test-auth")
    public ResponseEntity<String> testAuth() {
        System.out.println("🔍 ORDER DEBUG: test-auth endpoint called");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🔍 ORDER DEBUG: Authentication: " + auth);
        if (auth != null) {
            System.out.println("🔍 ORDER DEBUG: Principal: " + auth.getPrincipal());
            System.out.println("🔍 ORDER DEBUG: Authorities: " + auth.getAuthorities());
        }
        return ResponseEntity.ok("Authentication test successful! User: " + (auth != null ? auth.getName() : "anonymous"));
    }

    @GetMapping("/test-post-simulation")
    public ResponseEntity<String> testPostSimulation() {
        System.out.println("🔍 ORDER DEBUG: test-post-simulation endpoint called");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🔍 ORDER DEBUG: Authentication: " + auth);
        return ResponseEntity.ok("POST simulation test successful! User: " + (auth != null ? auth.getName() : "anonymous"));
    }

    @PostMapping("/test-simple-post")
    public ResponseEntity<String> testSimplePost() {
        System.out.println("🔍 ORDER DEBUG: test-simple-post endpoint called");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🔍 ORDER DEBUG: Authentication: " + auth);
        return ResponseEntity.ok("Simple POST test successful! User: " + (auth != null ? auth.getName() : "anonymous"));
    }

    @PostMapping("/test-create-request")
    public ResponseEntity<String> testCreateRequest(@RequestBody CreateOrderRequest request) {
        System.out.println("🔍 ORDER DEBUG: test-create-request endpoint called");
        System.out.println("🔍 ORDER DEBUG: Request: " + request);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🔍 ORDER DEBUG: Authentication: " + auth);
        return ResponseEntity.ok("CreateOrderRequest test successful! User: " + (auth != null ? auth.getName() : "anonymous"));
    }

    @PostMapping("/test-service-call")
    public ResponseEntity<String> testServiceCall(@RequestBody CreateOrderRequest request) {
        System.out.println("🔍 ORDER DEBUG: test-service-call endpoint called");
        System.out.println("🔍 ORDER DEBUG: About to call orderService.createOrder()");
        try {
            OrderEntity result = orderService.createOrder(request);
            System.out.println("🔍 ORDER DEBUG: Service call successful, order ID: " + result.getId());
            return ResponseEntity.ok("Service call successful! Order ID: " + result.getId());
        } catch (Exception e) {
            System.out.println("🔍 ORDER DEBUG: Service call failed: " + e.getMessage());
            return ResponseEntity.status(500).body("Service call failed: " + e.getMessage());
        }
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




}
