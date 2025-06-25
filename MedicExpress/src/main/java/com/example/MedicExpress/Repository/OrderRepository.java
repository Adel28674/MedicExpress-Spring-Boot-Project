package com.example.MedicExpress.Repository;

import com.example.MedicExpress.Model.OrderEntity;
import com.example.MedicExpress.Model.OrderStatus;
import com.example.MedicExpress.Model.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByPatient(Long patientId);

    Optional<OrderEntity> findFirstByPrescriptionIdAndStatusNotIn(Long prescriptionId, List<OrderStatus> excludedStatuses);

    List<OrderEntity> findByStatusAndPatient(String status, Long patient);

    List<OrderEntity> findByPrescriptionId(Long prescriptionId);

    @Query("SELECT DISTINCT o.prescription FROM OrderEntity o WHERE o.pharmacy.id = :pharmacyId")
    List<PrescriptionEntity> findPrescriptionsByPharmacyId(Long pharmacyId);

    // 🔍 Donne toutes les commandes pour une pharmacie
    List<OrderEntity> findByPharmacyId(Long pharmacyId);
}
