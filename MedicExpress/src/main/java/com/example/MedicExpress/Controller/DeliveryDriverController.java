package com.example.MedicExpress.Controller;
import com.example.MedicExpress.Model.NotificationEntity;
import com.example.MedicExpress.SerializationClass.AuthRequest;
import com.example.MedicExpress.Service.DeliveryDriverService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/deliveryDriver")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DeliveryDriverController {

    @Autowired
    private DeliveryDriverService deliveryDriverService;

    @GetMapping("/notifications/{id}")
    public ResponseEntity<List<NotificationEntity>> login(@PathVariable Long id) {
        List<NotificationEntity> notifications = deliveryDriverService.getNotificationsForDriver(id);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/{orderId}/accept")
    public ResponseEntity<?> acceptOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(deliveryDriverService.acceptOrder(orderId));
    }

    @PostMapping("/{orderId}/refuse")
    public ResponseEntity<?> refuseOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(deliveryDriverService.declineOrder(orderId));
    }


}
