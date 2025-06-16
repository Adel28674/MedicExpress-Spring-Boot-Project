package com.example.MedicExpress.SerializationClass;

import com.example.MedicExpress.Model.OrderStatus;
import java.time.LocalDate;
import java.util.Date;

public record OrderResponse(
        Long id,
        Date date,  // Préférez LocalDate à java.sql.Date
        OrderStatus status,
        String code,
        String qrcode,
        PrescriptionInfo prescription,  // Utilisez un DTO spécifique plutôt que l'entité
        DeliveryDriverInfo deliveryDriver,
        PatientInfo patient,
        PharmacyInfo pharmacy
) {
    // Records sont immuables par défaut (pas besoin de getters/setters)

    // DTOs imbriqués pour éviter la sérialisation circulaire
    public record PrescriptionInfo(Long id, String doctorName) {}
    public record DeliveryDriverInfo(Long id, String driverName) {}
    public record PatientInfo(Long id, String patientName) {}
    public record PharmacyInfo(Long id, String pharmacyName) {}
}