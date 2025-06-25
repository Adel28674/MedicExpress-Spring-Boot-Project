package com.example.MedicExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MedicExpress.Model.DoctorEntity;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long>{
}
