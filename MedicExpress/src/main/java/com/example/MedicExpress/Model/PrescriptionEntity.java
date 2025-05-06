package com.example.MedicExpress.Model;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;


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

    @ManyToMany
    @JoinTable(
            name = "prescription_medicament",
            joinColumns = @JoinColumn(name = "prescription_id"),
            inverseJoinColumns = @JoinColumn(name = "medicament_id")
    )
    private Set<MedicamentEntity> medicaments;

    @Column(name = "patient")
    private int patient;

}