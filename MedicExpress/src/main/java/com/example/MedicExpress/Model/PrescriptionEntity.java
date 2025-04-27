package com.example.MedicExpress.Model;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "prescriptions")
public class PrescriptionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "doctor") // colonne doctor en prescription = clé étrangère vers doctor.id
    private DoctorEntity doctorEntity;

    @Column(name = "medicament")
    private String medicaments;

    @Column(name = "patient")
    private int patient;

}