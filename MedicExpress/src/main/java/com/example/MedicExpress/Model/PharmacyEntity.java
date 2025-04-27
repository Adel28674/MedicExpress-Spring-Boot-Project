package com.example.MedicExpress.Model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter

@Entity
//@Table(name = "users")
public class PharmacyEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    public PharmacyEntity(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
