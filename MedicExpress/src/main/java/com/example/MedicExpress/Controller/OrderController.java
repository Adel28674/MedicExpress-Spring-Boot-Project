package com.example.MedicExpress.Controller;

import com.example.MedicExpress.Model.*;
import com.example.MedicExpress.Repository.*;
import com.example.MedicExpress.Service.DoctorService;

import com.example.MedicExpress.Service.OrderService;
import com.example.MedicExpress.Service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;


@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public OrderEntity createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }


    @PutMapping("/updateStatus/{orderId}")
    public OrderEntity updateOrderStatus(@PathVariable Long orderId) {
        return orderService.updateOrderStatus(orderId);
    }

    @GetMapping("/generateQRCode/{orderId}")
    public String generateQRCode(@PathVariable Long orderId) {
        return orderService.generateOrderQRCode(orderId);
    }
}
