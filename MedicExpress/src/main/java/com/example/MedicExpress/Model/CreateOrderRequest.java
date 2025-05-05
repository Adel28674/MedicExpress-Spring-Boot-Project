package com.example.MedicExpress.Model;

public class CreateOrderRequest {

    private String status;

    private Long deliveryDriverId;
    private Long pharmacyId;
    private Long patientId;
    private Long prescriptionId;



    public String getStatus() {
        return status;
    }

    public Long getDeliveryDriverId() {
        return deliveryDriverId;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public void setDeliveryDriverId(Long deliveryDriverId) {
        this.deliveryDriverId = deliveryDriverId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
}
