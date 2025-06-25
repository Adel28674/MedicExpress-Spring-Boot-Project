
package com.example.MedicExpress.Repository;

import com.example.MedicExpress.Model.DoctorEntity;
import com.example.MedicExpress.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long>{
    // Repository
}
