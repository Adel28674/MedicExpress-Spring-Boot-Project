package com.example.MedicExpress.Model;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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

    @ManyToOne
    @JoinColumn(name = "doctor") // colonne doctor en prescription = clé étrangère vers doctor.id
    private DoctorEntity doctorEntity;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicamentEntity> medicaments = new ArrayList<>();


    @Column(name = "patient")
    private Long patient;

}