package com.example.MedicExpress.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "medicament")
public class MedicamentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "prescription_id", nullable = false)
    @JsonBackReference  // <-- ajoute cette annotation ici
    private PrescriptionEntity prescription;
}
