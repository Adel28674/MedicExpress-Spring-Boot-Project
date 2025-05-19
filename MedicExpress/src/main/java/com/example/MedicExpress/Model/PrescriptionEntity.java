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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor")
    private DoctorEntity doctorEntity;

    @Column(name = "medicament")
    private String medicaments;

    @ManyToOne
    @JoinColumn(name = "patient")
    private UserEntity patient;
}
