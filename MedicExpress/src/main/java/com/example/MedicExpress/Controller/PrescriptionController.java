package com.example.MedicExpress.Controller;

import com.example.MedicExpress.Model.PrescriptionEntity;
import com.example.MedicExpress.Service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prescription")
@RequiredArgsConstructor
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/addPrescription")
    public ResponseEntity<?> addPrescription(@RequestBody PrescriptionEntity prescriptionEntity) {
        try {
            prescriptionService.create(prescriptionEntity);
            return ResponseEntity.ok("Ordonnance créée pour le patient ID : " + prescriptionEntity.getPatient());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

}