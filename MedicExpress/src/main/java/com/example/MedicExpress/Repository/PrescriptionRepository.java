
package com.example.MedicExpress.Repository;

import com.example.MedicExpress.Model.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Long> {
    Optional<PrescriptionEntity> findByCode(String code);
}