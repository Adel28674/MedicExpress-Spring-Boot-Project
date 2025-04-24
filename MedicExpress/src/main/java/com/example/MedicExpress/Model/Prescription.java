package com.example.MedicExpress.Model;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter

@Entity
//@Table(name = "users")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id")
    private String code;

    @ManyToOne
    private Doctor doctor;

    @ElementCollection
    private List<String> medicaments;

    public Prescription( String code, Doctor doctor, List<String> medicaments){

        this.code = code;
        this.doctor = doctor ;
        this.medicaments = medicaments;

    }

}
