package com.example.MedicExpress.Model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter

@Entity
//@Table(name = "users")
public class PrescriptionEntity {

    @Id
//    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id")
    private String code;

    @ManyToOne
//  @Column(name = "id")
    private DoctorEntity doctorEntity;

    @ElementCollection
//    @Column(name = "id")
    private List<String> medicaments;

//    @Column(name = "id")
    private PatientEntity patient;

    public PrescriptionEntity(String code, DoctorEntity doctorEntity, List<String> medicaments, PatientEntity patient){

        this.code = code;
        this.doctorEntity = doctorEntity;
        this.medicaments = medicaments;
        this.patient = patient;

    }

}
