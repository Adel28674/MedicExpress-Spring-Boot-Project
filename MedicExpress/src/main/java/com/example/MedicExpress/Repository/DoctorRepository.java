
package com.example.MedicExpress.Repository;

import com.example.MedicExpress.Model.DoctorEntity;
import com.example.MedicExpress.Model.PatientEntity;
import com.example.MedicExpress.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long>{
    @Query("SELECT DISTINCT u FROM UserEntity u JOIN PrescriptionEntity p ON u.id = p.patient.id WHERE p.doctorEntity.id = :doctorId")
    List<UserEntity> findDistinctPatientsByDoctorId(@Param("doctorId") Long doctorId);
}
