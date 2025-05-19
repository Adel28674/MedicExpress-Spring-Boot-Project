package com.example.MedicExpress.Model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {

    private String status;

    private Long deliveryDriverId;
    private Long pharmacyId;
    private Long patientId;
    private Long prescriptionId;

}
