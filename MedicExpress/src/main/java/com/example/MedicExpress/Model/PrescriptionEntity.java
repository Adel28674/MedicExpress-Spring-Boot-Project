package com.example.MedicExpress.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor")
    private DoctorEntity doctorEntity;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference  // <-- ajoute cette annotation ici
    private List<MedicamentEntity> medicaments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "patient")
    private PatientEntity patient;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MedicamentEntity> medicaments = new ArrayList<>();

}
