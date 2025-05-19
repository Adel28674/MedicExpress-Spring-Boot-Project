package com.example.MedicExpress.Controller;

import com.example.MedicExpress.Model.CreateOrderRequest;
import com.example.MedicExpress.Model.DoctorEntity;
import com.example.MedicExpress.Model.OrderEntity;
import com.example.MedicExpress.Model.PrescriptionEntity;
import com.example.MedicExpress.Service.DeliveryDriverService;
import com.example.MedicExpress.Service.DoctorService;

import com.example.MedicExpress.Service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/prescriptions")
    public ResponseEntity<List<PrescriptionEntity>> getPrescriptions(@RequestParam Long patientId) {
        return ResponseEntity.ok(patientService.getPrescriptionsForPatient(patientId));
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderEntity> placeOrder(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(patientService.createOrder(request));
    }



    @GetMapping("/orders")
    public ResponseEntity<List<OrderEntity>> getMyOrders(@RequestParam Long patientId) {
        return ResponseEntity.ok(patientService.getOrdersForPatient(patientId));
    }

    // ---- Évaluation du livreur ----

//    @PostMapping("/evaluate")
//    public ResponseEntity<String> evaluateDelivery(@RequestBody EvaluationRequest evaluation) {
//        patientService.evaluateDelivery(evaluation);
//        return ResponseEntity.ok("Évaluation enregistrée");
//    }

    // ---- Notifications / Historique ----

//    @GetMapping("/history")
//    public ResponseEntity<List<OrderEntity>> getTreatmentHistory(@RequestParam Long patientId) {
//        return ResponseEntity.ok(patientService.getTreatmentHistory(patientId));
//    }

}
