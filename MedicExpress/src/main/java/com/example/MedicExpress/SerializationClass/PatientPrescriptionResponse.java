package com.example.MedicExpress.SerializationClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientPrescriptionResponse {
    private Long id;
    private String status;
    private String date;
    private DoctorInfo doctor;
    private List<MedicationInfo> medications;
    private boolean hasOrder;
    private Long orderId;
    private String orderStatus;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DoctorInfo {
        private Long id;
        private String name;
        private String firstName;
        private String email;
        private String rpps;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicationInfo {
        private Long id;
        private String name;
    }
} 