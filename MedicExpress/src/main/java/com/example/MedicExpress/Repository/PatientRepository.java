package com.example.MedicExpress.Repository;

import com.example.MedicExpress.Model.DeliveryDriverEntity;
import com.example.MedicExpress.Model.DoctorEntity;
import com.example.MedicExpress.Model.OrderEntity;
import com.example.MedicExpress.Model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    Optional<PatientEntity> findById(Long id);
}

