package com.example.MedicExpress.Model;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "prescriptions")
public class PrescriptionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor") // colonne doctor en prescription = clé étrangère vers doctor.id
    private DoctorEntity doctorEntity;

    @Column(name = "medicament")
    private String medicaments;

    @Column(name = "patient")
    private int patient;

}