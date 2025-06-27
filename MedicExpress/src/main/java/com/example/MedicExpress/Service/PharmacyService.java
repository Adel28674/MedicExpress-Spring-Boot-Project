package com.example.MedicExpress.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MedicExpress.Model.OrderEntity;
import com.example.MedicExpress.Model.PharmacyEntity;
import com.example.MedicExpress.Model.PrescriptionEntity;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Repository.OrderRepository;
import com.example.MedicExpress.Repository.PharmacyRepository;
import com.example.MedicExpress.Repository.PrescriptionRepository;
import com.example.MedicExpress.Repository.UserRepository;

@Service
public class PharmacyService {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all orders for pharmacist dashboard
     * For now, shows ALL orders (global view) - can be filtered by pharmacy later
     */
    public List<OrderEntity> getOrdersForPharmacist(String pharmacistEmail, String status) {
        System.out.println("🔍 PHARMACY SERVICE: Getting orders for " + pharmacistEmail + " with status filter: " + status);
        
        List<OrderEntity> allOrders = orderRepository.findAll();
        
        if (status != null && !status.trim().isEmpty()) {
            return allOrders.stream()
                    .filter(order -> order.getStatus().equalsIgnoreCase(status.trim()))
                    .collect(Collectors.toList());
        }
        
        return allOrders;
    }

    /**
     * Get specific order details with full prescription info
     */
    public OrderEntity getOrderDetails(Long orderId, String pharmacistEmail) {
        System.out.println("🔍 PHARMACY SERVICE: Getting order details for order " + orderId);
        
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    /**
     * Update order status in pharmacist workflow
     * Validates status transitions for pharmacy workflow
     */
    public OrderEntity updateOrderStatus(Long orderId, String newStatus, String pharmacistEmail) {
        System.out.println("🔍 PHARMACY SERVICE: Updating order " + orderId + " to status " + newStatus);
        
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        String currentStatus = order.getStatus();
        
        // Validate pharmacy workflow transitions
        if (isValidPharmacyStatusTransition(currentStatus, newStatus)) {
            order.setStatus(newStatus.toUpperCase());
            
            // Generate new code for status changes
            if (!"PENDING".equalsIgnoreCase(newStatus)) {
                int randomCode = (int)(Math.random() * 900000) + 100000;
                order.setCode(String.valueOf(randomCode));
            }
            
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Invalid status transition from " + currentStatus + " to " + newStatus);
        }
    }

    /**
     * Validate pharmacy workflow status transitions
     */
    private boolean isValidPharmacyStatusTransition(String currentStatus, String newStatus) {
        if (currentStatus == null || newStatus == null) return false;
        
        String current = currentStatus.toUpperCase();
        String next = newStatus.toUpperCase();
        
        // Pharmacy workflow: PENDING → PREPARING → READY → DISPATCHED
        switch (current) {
            case "PENDING":
                return "PREPARING".equals(next);
            case "PREPARING":
                return "READY".equals(next);
            case "READY":
                return "DISPATCHED".equals(next);
            default:
                return false;
        }
    }

    /**
     * Get prescription details for an order
     */
    public PrescriptionEntity getPrescriptionDetails(Long prescriptionId, String pharmacistEmail) {
        System.out.println("🔍 PHARMACY SERVICE: Getting prescription details for " + prescriptionId);
        
        return prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + prescriptionId));
    }

    /**
     * Get pharmacist profile information
     */
    public Map<String, Object> getPharmacistProfile(String pharmacistEmail) {
        System.out.println("🔍 PHARMACY SERVICE: Getting profile for " + pharmacistEmail);
        
        UserEntity pharmacist = userRepository.findByEmail(pharmacistEmail)
                .orElseThrow(() -> new RuntimeException("Pharmacist not found: " + pharmacistEmail));

        Map<String, Object> profile = new HashMap<>();
        profile.put("email", pharmacist.getEmail());
        profile.put("firstName", pharmacist.getFirstName());
        profile.put("name", pharmacist.getName());
        profile.put("role", pharmacist.getRole());
        
        // For now, assign to first pharmacy (since no direct relationship exists)
        List<PharmacyEntity> pharmacies = pharmacyRepository.findAll();
        if (!pharmacies.isEmpty()) {
            PharmacyEntity pharmacy = pharmacies.get(0);
            profile.put("pharmacy", Map.of(
                "id", pharmacy.getId(),
                "name", pharmacy.getName(),
                "address", pharmacy.getAddress()
            ));
        }
        
        return profile;
    }

    /**
     * Get dashboard statistics for pharmacist
     */
    public Map<String, Object> getDashboardStats(String pharmacistEmail) {
        System.out.println("🔍 PHARMACY SERVICE: Getting dashboard stats for " + pharmacistEmail);
        
        List<OrderEntity> allOrders = orderRepository.findAll();
        
        long pendingCount = allOrders.stream().filter(o -> "PENDING".equalsIgnoreCase(o.getStatus())).count();
        long preparingCount = allOrders.stream().filter(o -> "PREPARING".equalsIgnoreCase(o.getStatus())).count();
        long readyCount = allOrders.stream().filter(o -> "READY".equalsIgnoreCase(o.getStatus())).count();
        long dispatchedCount = allOrders.stream().filter(o -> "DISPATCHED".equalsIgnoreCase(o.getStatus())).count();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalOrders", allOrders.size());
        stats.put("pendingOrders", pendingCount);
        stats.put("preparingOrders", preparingCount);
        stats.put("readyOrders", readyCount);
        stats.put("dispatchedOrders", dispatchedCount);
        
        return stats;
    }
}
