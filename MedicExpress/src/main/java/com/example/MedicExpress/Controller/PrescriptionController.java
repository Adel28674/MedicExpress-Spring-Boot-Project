package com.example.MedicExpress.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MedicExpress.Model.PrescriptionEntity;
import com.example.MedicExpress.SerializationClass.CreatePrescriptionRequest;
import com.example.MedicExpress.SerializationClass.PrescriptionResponse;
import com.example.MedicExpress.Service.PrescriptionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/prescription")
@RequiredArgsConstructor
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/addPrescription")
    public ResponseEntity<PrescriptionResponse> addPrescription(@RequestBody CreatePrescriptionRequest request) {
        // 🔍 DEBUG LOGGING
        System.out.println("🔍 RECEIVED REQUEST: " + request);
        System.out.println("🔍 Patient ID: " + request.getPatientId());
        System.out.println("🔍 Doctor ID: " + request.getDoctorId());
        System.out.println("🔍 Date: " + request.getDate());
        System.out.println("🔍 Medications: " + request.getMedications());
        System.out.println("🔍 Medications String: " + request.getMedicationsString());
        System.out.println("🔍 Instructions: " + request.getInstructions());
        System.out.println("🔍 Status: " + request.getStatus());
        
        try {
            PrescriptionEntity prescription = prescriptionService.createFromRequest(request);
            PrescriptionResponse response = new PrescriptionResponse(
                true,
                "Ordonnance créée avec succès",
                prescription.getId(),
                request.getPatientId()
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            System.out.println("🚨 ERROR: " + e.getMessage());
            PrescriptionResponse errorResponse = new PrescriptionResponse(
                false,
                e.getMessage(),
                null,
                null
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

}