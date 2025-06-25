package com.example.MedicExpress.SerializationClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionResponse {
    private boolean success;
    private String message;
    private Long prescriptionId;
    private Long patientId;
} 