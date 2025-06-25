package com.example.MedicExpress.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
@Table(name = "patients")
public class PatientEntity {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserEntity user;

    @Column(name = "sex")
    private String sex;

    @Column(name = "address")
    private String address;

}
