package com.example.MedicExpress.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MedicExpress.Model.OrderEntity;
import com.example.MedicExpress.Service.DeliveryDriverService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/deliveryDriver")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DeliveryDriverController {

    @Autowired
    private DeliveryDriverService deliveryDriverService;

    /**
     * Get courier dashboard statistics
     */
    @GetMapping("/dashboard/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String driverEmail = auth.getName();
        
        System.out.println("🚚 COURIER DEBUG: Getting dashboard stats for " + driverEmail);
        
        Map<String, Object> stats = deliveryDriverService.getDashboardStats(driverEmail);
        return ResponseEntity.ok(stats);
    }

    /**
     * Get assigned deliveries/orders for courier
     * Optional status filter: ?status=ASSIGNED
     */
    @GetMapping("/deliveries")
    public ResponseEntity<List<OrderEntity>> getDeliveries(@RequestParam(required = false) String status) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String driverEmail = auth.getName();
        
        System.out.println("🚚 COURIER DEBUG: Getting deliveries for " + driverEmail + ", status filter: " + status);
        
        List<OrderEntity> deliveries = deliveryDriverService.getAssignedDeliveries(driverEmail, status);
        return ResponseEntity.ok(deliveries);
    }

    /**
     * Get specific delivery details
     */
    @GetMapping("/deliveries/{deliveryId}")
    public ResponseEntity<OrderEntity> getDeliveryDetails(@PathVariable Long deliveryId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String driverEmail = auth.getName();
        
        System.out.println("🚚 COURIER DEBUG: Getting delivery details for " + deliveryId);
        
        OrderEntity delivery = deliveryDriverService.getDeliveryDetails(deliveryId, driverEmail);
        return ResponseEntity.ok(delivery);
    }

    /**
     * Update delivery status
     * Body: {"status": "IN_TRANSIT"} or {"status": "DELIVERED"}
     */
    @PutMapping("/deliveries/{deliveryId}/status")
    public ResponseEntity<OrderEntity> updateDeliveryStatus(
            @PathVariable Long deliveryId, 
            @RequestBody Map<String, String> statusUpdate) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String driverEmail = auth.getName();
        String newStatus = statusUpdate.get("status");
        
        System.out.println("🚚 COURIER DEBUG: Updating delivery " + deliveryId + " status to " + newStatus);
        
        OrderEntity updatedDelivery = deliveryDriverService.updateDeliveryStatus(deliveryId, newStatus, driverEmail);
        return ResponseEntity.ok(updatedDelivery);
    }

    /**
     * Accept delivery assignment
     */
    @PostMapping("/deliveries/{deliveryId}/accept")
    public ResponseEntity<Map<String, String>> acceptDelivery(@PathVariable Long deliveryId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String driverEmail = auth.getName();
        
        System.out.println("🚚 COURIER DEBUG: Accepting delivery " + deliveryId);
        
        deliveryDriverService.acceptDelivery(deliveryId, driverEmail);
        return ResponseEntity.ok(Map.of("message", "Delivery accepted successfully"));
    }

    /**
     * Refuse delivery assignment
     */
    @PostMapping("/deliveries/{deliveryId}/refuse")
    public ResponseEntity<Map<String, String>> refuseDelivery(@PathVariable Long deliveryId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String driverEmail = auth.getName();
        
        System.out.println("🚚 COURIER DEBUG: Refusing delivery " + deliveryId);
        
        deliveryDriverService.refuseDelivery(deliveryId, driverEmail);
        return ResponseEntity.ok(Map.of("message", "Delivery refused"));
    }

    /**
     * Get courier profile information
     */
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getCourierProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String driverEmail = auth.getName();
        
        System.out.println("🚚 COURIER DEBUG: Getting profile for " + driverEmail);
        
        Map<String, Object> profile = deliveryDriverService.getCourierProfile(driverEmail);
        return ResponseEntity.ok(profile);
    }

    /**
     * Get delivery history
     */
    @GetMapping("/history")
    public ResponseEntity<List<OrderEntity>> getDeliveryHistory() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String driverEmail = auth.getName();
        
        System.out.println("🚚 COURIER DEBUG: Getting delivery history for " + driverEmail);
        
        List<OrderEntity> history = deliveryDriverService.getDeliveryHistory(driverEmail);
        return ResponseEntity.ok(history);
    }

    /**
     * Update courier availability status
     */
    @PutMapping("/availability")
    public ResponseEntity<Map<String, Object>> updateAvailability(@RequestBody Map<String, Boolean> availabilityUpdate) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String driverEmail = auth.getName();
        Boolean available = availabilityUpdate.get("available");
        
        System.out.println("🚚 COURIER DEBUG: Updating availability for " + driverEmail + " to " + available);
        
        Map<String, Object> result = deliveryDriverService.updateAvailability(driverEmail, available);
        return ResponseEntity.ok(result);
    }
}
