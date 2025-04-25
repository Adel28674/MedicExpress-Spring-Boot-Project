package com.example.MedicExpress.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
//@Table(name = "users")
@Entity
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

//    @Column(name = "id")
    private String sex;

//    @Column(name = "id")
    private Date dateBirth;

//    @Column(name = "id")
    private String bankCard;

    //    @Column(name = "id")
    private String vitalCard;

    //    @Column(name = "id")
    private List<PrescriptionEntity> prescriptionEntity;


}
