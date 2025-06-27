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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MedicExpress.Model.OrderEntity;
import com.example.MedicExpress.Model.PrescriptionEntity;
import com.example.MedicExpress.Service.OrderService;
import com.example.MedicExpress.Service.PharmacyService;
import com.example.MedicExpress.Service.PrescriptionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pharmacist")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PrescriptionService prescriptionService;

    /**
     * Get all orders for pharmacist dashboard
     * Optional status filter: ?status=PENDING
     */
    @GetMapping("/orders")
    public ResponseEntity<List<OrderEntity>> getOrders(@RequestParam(required = false) String status) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String pharmacistEmail = auth.getName();
        
        System.out.println("🔍 PHARMACIST DEBUG: Getting orders for " + pharmacistEmail + ", status filter: " + status);
        
        List<OrderEntity> orders = pharmacyService.getOrdersForPharmacist(pharmacistEmail, status);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get specific order details with full prescription info
     */
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderEntity> getOrderDetails(@PathVariable Long orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String pharmacistEmail = auth.getName();
        
        System.out.println("🔍 PHARMACIST DEBUG: Getting order details for order " + orderId);
        
        OrderEntity order = pharmacyService.getOrderDetails(orderId, pharmacistEmail);
        return ResponseEntity.ok(order);
    }

    /**
     * Update order status in pharmacist workflow
     * Body: {"status": "PREPARING"} or {"status": "READY"}
     */
    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<OrderEntity> updateOrderStatus(
            @PathVariable Long orderId, 
            @RequestBody Map<String, String> statusUpdate) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String pharmacistEmail = auth.getName();
        String newStatus = statusUpdate.get("status");
        
        System.out.println("🔍 PHARMACIST DEBUG: Updating order " + orderId + " status to " + newStatus);
        
        OrderEntity updatedOrder = pharmacyService.updateOrderStatus(orderId, newStatus, pharmacistEmail);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Get prescription details for an order
     */
    @GetMapping("/prescriptions/{prescriptionId}")
    public ResponseEntity<PrescriptionEntity> getPrescriptionDetails(@PathVariable Long prescriptionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String pharmacistEmail = auth.getName();
        
        System.out.println("🔍 PHARMACIST DEBUG: Getting prescription details for " + prescriptionId);
        
        PrescriptionEntity prescription = pharmacyService.getPrescriptionDetails(prescriptionId, pharmacistEmail);
        return ResponseEntity.ok(prescription);
    }

    /**
     * Get pharmacist profile information
     */
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getPharmacistProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String pharmacistEmail = auth.getName();
        
        System.out.println("🔍 PHARMACIST DEBUG: Getting profile for " + pharmacistEmail);
        
        Map<String, Object> profile = pharmacyService.getPharmacistProfile(pharmacistEmail);
        return ResponseEntity.ok(profile);
    }

    /**
     * Get dashboard statistics
     */
    @GetMapping("/dashboard/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String pharmacistEmail = auth.getName();
        
        Map<String, Object> stats = pharmacyService.getDashboardStats(pharmacistEmail);
        return ResponseEntity.ok(stats);
    }
}
