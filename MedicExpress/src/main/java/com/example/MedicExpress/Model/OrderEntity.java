package com.example.MedicExpress.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Setter
@Getter
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private String status;

    @Column(name = "code")
    private String code;

    @Column(name = "qrcode", columnDefinition = "TEXT")
    private String qrcode;

    @ManyToOne
    @JoinColumn(name = "prescription", referencedColumnName = "id")
    private PrescriptionEntity prescription;

    @ManyToOne
    @JoinColumn(name = "deliverydriver", referencedColumnName = "id")
    private DeliveryDriverEntity deliveryDriver;

    @ManyToOne
    @JoinColumn(name = "patient", referencedColumnName = "id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "pharmacy", referencedColumnName = "id")
    private PharmacyEntity pharmacy;

}
