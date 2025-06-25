package com.example.MedicExpress.SerializationClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientSearchResponse {
    private Long userId;
    private Long patientId; // Same as userId due to 1:1 relationship
    private String name;
    private String firstName;
    private String email;
    private String fullName; // Computed field for display
    private boolean hasCompleteName; // Indicator if name/firstName are available
} 