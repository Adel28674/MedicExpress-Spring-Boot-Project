package com.example.MedicExpress.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MedicExpress.Model.DeliveryDriverEntity;
import com.example.MedicExpress.Model.OrderEntity;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Repository.DeliveryDriverRepository;
import com.example.MedicExpress.Repository.OrderRepository;
import com.example.MedicExpress.Repository.UserRepository;

@Service
public class DeliveryDriverService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DeliveryDriverRepository deliveryDriverRepository;

    /**
     * Get dashboard statistics for delivery driver
     */
    public Map<String, Object> getDashboardStats(String driverEmail) {
        System.out.println("🚚 DRIVER SERVICE: Getting dashboard stats for " + driverEmail);
        
        UserEntity driver = userRepository.findByEmail(driverEmail)
                .orElseThrow(() -> new RuntimeException("Driver not found: " + driverEmail));
        
        List<OrderEntity> driverOrders = orderRepository.findAll().stream()
                .filter(order -> order.getDeliveryDriver() != null)
                .collect(Collectors.toList());
        
        long assignedCount = driverOrders.stream().filter(o -> "DISPATCHED".equalsIgnoreCase(o.getStatus())).count();
        long inTransitCount = driverOrders.stream().filter(o -> "IN_TRANSIT".equalsIgnoreCase(o.getStatus())).count();
        long deliveredCount = driverOrders.stream().filter(o -> "DELIVERED".equalsIgnoreCase(o.getStatus())).count();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalDeliveries", driverOrders.size());
        stats.put("assignedDeliveries", assignedCount);
        stats.put("inTransitDeliveries", inTransitCount);
        stats.put("deliveredCount", deliveredCount);
        stats.put("availableForDelivery", true); // Default to available
        
        return stats;
    }

    /**
     * Get assigned deliveries for driver
     */
    public List<OrderEntity> getAssignedDeliveries(String driverEmail, String status) {
        System.out.println("🚚 DRIVER SERVICE: Getting deliveries for " + driverEmail + " with status filter: " + status);
        
        UserEntity driver = userRepository.findByEmail(driverEmail)
                .orElseThrow(() -> new RuntimeException("Driver not found: " + driverEmail));
        
        List<OrderEntity> allDeliveries = orderRepository.findAll().stream()
                .filter(order -> order.getDeliveryDriver() != null)
                .filter(order -> isDeliveryStatus(order.getStatus()))
                .collect(Collectors.toList());
        
        if (status != null && !status.trim().isEmpty()) {
            return allDeliveries.stream()
                    .filter(order -> order.getStatus().equalsIgnoreCase(status.trim()))
                    .collect(Collectors.toList());
        }
        
        return allDeliveries;
    }

    /**
     * Check if status is delivery-related
     */
    private boolean isDeliveryStatus(String status) {
        return "DISPATCHED".equalsIgnoreCase(status) || 
               "IN_TRANSIT".equalsIgnoreCase(status) || 
               "DELIVERED".equalsIgnoreCase(status);
    }

    /**
     * Get specific delivery details
     */
    public OrderEntity getDeliveryDetails(Long deliveryId, String driverEmail) {
        System.out.println("🚚 DRIVER SERVICE: Getting delivery details for " + deliveryId);
        
        return orderRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + deliveryId));
    }

    /**
     * Update delivery status
     */
    public OrderEntity updateDeliveryStatus(Long deliveryId, String newStatus, String driverEmail) {
        System.out.println("🚚 DRIVER SERVICE: Updating delivery " + deliveryId + " to status " + newStatus);
        
        OrderEntity delivery = orderRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + deliveryId));

        String currentStatus = delivery.getStatus();
        
        // Validate delivery workflow transitions
        if (isValidDeliveryStatusTransition(currentStatus, newStatus)) {
            delivery.setStatus(newStatus.toUpperCase());
            
            // Generate new code for status changes
            int randomCode = (int)(Math.random() * 900000) + 100000;
            delivery.setCode(String.valueOf(randomCode));
            
            return orderRepository.save(delivery);
        } else {
            throw new RuntimeException("Invalid delivery status transition from " + currentStatus + " to " + newStatus);
        }
    }

    /**
     * Validate delivery workflow status transitions
     */
    private boolean isValidDeliveryStatusTransition(String currentStatus, String newStatus) {
        if (currentStatus == null || newStatus == null) return false;
        
        String current = currentStatus.toUpperCase();
        String next = newStatus.toUpperCase();
        
        // Delivery workflow: DISPATCHED → IN_TRANSIT → DELIVERED
        switch (current) {
            case "DISPATCHED":
                return "IN_TRANSIT".equals(next);
            case "IN_TRANSIT":
                return "DELIVERED".equals(next);
            default:
                return false;
        }
    }

    /**
     * Accept delivery assignment
     */
    public void acceptDelivery(Long deliveryId, String driverEmail) {
        System.out.println("🚚 DRIVER SERVICE: Accepting delivery " + deliveryId + " for " + driverEmail);
        
        OrderEntity delivery = orderRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + deliveryId));
        
        // CRITICAL FIX: Change status from DISPATCHED to IN_TRANSIT when accepted
        delivery.setStatus("IN_TRANSIT");
        
        // Generate new code for status change
        int randomCode = (int)(Math.random() * 900000) + 100000;
        delivery.setCode(String.valueOf(randomCode));
        
        orderRepository.save(delivery);
    }

    /**
     * Refuse delivery assignment
     */
    public void refuseDelivery(Long deliveryId, String driverEmail) {
        System.out.println("🚚 DRIVER SERVICE: Refusing delivery " + deliveryId + " by " + driverEmail);
        
        OrderEntity delivery = orderRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + deliveryId));
        
        // Set back to READY so it can be reassigned to another driver
        delivery.setStatus("READY");
        
        // Generate new code for status change
        int randomCode = (int)(Math.random() * 900000) + 100000;
        delivery.setCode(String.valueOf(randomCode));
        
        orderRepository.save(delivery);
    }

    /**
     * Get courier profile information
     */
    public Map<String, Object> getCourierProfile(String driverEmail) {
        System.out.println("🚚 DRIVER SERVICE: Getting profile for " + driverEmail);
        
        UserEntity driver = userRepository.findByEmail(driverEmail)
                .orElseThrow(() -> new RuntimeException("Driver not found: " + driverEmail));

        Map<String, Object> profile = new HashMap<>();
        profile.put("email", driver.getEmail());
        profile.put("firstName", driver.getFirstName());
        profile.put("name", driver.getName());
        profile.put("role", driver.getRole());
        
        // Get associated delivery driver record
        List<DeliveryDriverEntity> drivers = deliveryDriverRepository.findAll();
        if (!drivers.isEmpty()) {
            DeliveryDriverEntity driverEntity = drivers.get(0); // For now, use first available
            profile.put("driverId", driverEntity.getId());
            profile.put("available", driverEntity.isAvailable());
            profile.put("kbis", driverEntity.getKbis());
            profile.put("vehicleInfo", "Standard Delivery Vehicle"); // Placeholder
        }
        
        return profile;
    }

    /**
     * Get delivery history for driver
     */
    public List<OrderEntity> getDeliveryHistory(String driverEmail) {
        System.out.println("🚚 DRIVER SERVICE: Getting delivery history for " + driverEmail);
        
        // For now, return all delivered orders
        return orderRepository.findAll().stream()
                .filter(order -> "DELIVERED".equalsIgnoreCase(order.getStatus()))
                .collect(Collectors.toList());
    }

    /**
     * Update driver availability status
     */
    public Map<String, Object> updateAvailability(String driverEmail, Boolean available) {
        System.out.println("🚚 DRIVER SERVICE: Updating availability for " + driverEmail + " to " + available);
        
        UserEntity driver = userRepository.findByEmail(driverEmail)
                .orElseThrow(() -> new RuntimeException("Driver not found: " + driverEmail));

        // Update delivery driver availability
        List<DeliveryDriverEntity> drivers = deliveryDriverRepository.findAll();
        if (!drivers.isEmpty()) {
            DeliveryDriverEntity driverEntity = drivers.get(0); // For now, use first available
            driverEntity.setAvailable(available);
            deliveryDriverRepository.save(driverEntity);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("email", driverEmail);
        result.put("available", available);
        result.put("message", "Availability updated successfully");
        
        return result;
    }
}
