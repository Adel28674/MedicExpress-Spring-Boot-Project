package com.example.MedicExpress.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MedicExpress.Model.DeliveryDriverEntity;
import com.example.MedicExpress.Model.PharmacyEntity;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    public AdminService adminService;

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserEntity>> getUsers(){
        return ResponseEntity.ok(adminService.getUsers());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(adminService.getUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pharmacies")
    public ResponseEntity<List<PharmacyEntity>> getAllPharmacies() {
        return ResponseEntity.ok(adminService.getAllPharmacies());
    }

    @PostMapping("/pharmacies")
    public ResponseEntity<PharmacyEntity> addPharmacy(@RequestBody PharmacyEntity pharmacy) {
        PharmacyEntity saved = adminService.savePharmacy(pharmacy);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/pharmacies/{id}")
    public ResponseEntity<Void> deletePharmacy(@PathVariable Long id) {
        adminService.deletePharmacyById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/deliveryDrivers")
    public ResponseEntity<List<DeliveryDriverEntity>> getAllDeliveryDrivers() {
        return ResponseEntity.ok(adminService.getAllDeliveryDrivers());
    }

    @PostMapping("/deliveryDrivers")
    public ResponseEntity<DeliveryDriverEntity> addDeliveryDriver(@RequestBody DeliveryDriverEntity driver) {
        DeliveryDriverEntity saved = adminService.saveDeliveryDriver(driver);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/deliveryDrivers/{id}")
    public ResponseEntity<Void> deleteDeliveryDriver(@PathVariable Long id) {
        adminService.deleteDeliveryDriverById(id);
        return ResponseEntity.noContent().build();
    }

}
