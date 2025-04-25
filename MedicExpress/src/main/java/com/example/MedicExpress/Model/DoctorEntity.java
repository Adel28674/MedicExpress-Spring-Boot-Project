package com.example.MedicExpress.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//@Table(name = "users")
@Entity
public class DoctorEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

    @Column(name = "rpps")
    private String rpps;


    public DoctorEntity(String rpps) {
        this.rpps = rpps;
    }

}
