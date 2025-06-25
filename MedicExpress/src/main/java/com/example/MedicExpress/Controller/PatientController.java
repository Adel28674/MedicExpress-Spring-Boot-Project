package com.example.MedicExpress.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MedicExpress.Model.CreateOrderRequest;
import com.example.MedicExpress.Model.OrderEntity;
import com.example.MedicExpress.Model.PrescriptionEntity;
import com.example.MedicExpress.SerializationClass.PatientPrescriptionResponse;
import com.example.MedicExpress.Service.PatientService;

import lombok.RequiredArgsConstructor;


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

    @GetMapping("/prescriptions/enhanced")
    public ResponseEntity<List<PatientPrescriptionResponse>> getEnhancedPrescriptions(@RequestParam Long patientId) {
        return ResponseEntity.ok(patientService.getEnhancedPrescriptionsForPatient(patientId));
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
