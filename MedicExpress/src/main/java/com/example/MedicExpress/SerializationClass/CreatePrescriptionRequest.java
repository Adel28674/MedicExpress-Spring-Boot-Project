package com.example.MedicExpress.SerializationClass;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreatePrescriptionRequest {
    private Long patientId;
    private String doctorId; // Doctor email
    private String date;
    private List<MedicationRequest> medications;
    private List<String> medicationNames;
    private String medicationsString;
    private String instructions;
    private String status;

    @Getter
    @Setter
    public static class MedicationRequest {
        private String name;
    }
} 