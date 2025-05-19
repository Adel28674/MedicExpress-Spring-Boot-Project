package com.example.MedicExpress.Repository;

import com.example.MedicExpress.Model.DeliveryDriverEntity;
import com.example.MedicExpress.Model.DoctorEntity;
import com.example.MedicExpress.Model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

}