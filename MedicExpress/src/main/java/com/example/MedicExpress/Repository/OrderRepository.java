package com.example.MedicExpress.Repository;

import com.example.MedicExpress.Model.OrderEntity;
import com.example.MedicExpress.Model.OrderStatus;
import com.example.MedicExpress.Model.PrescriptionEntity;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByPatientId(Long patientId);
    Optional<OrderEntity> findFirstByPrescriptionIdAndStatusNotIn(Long prescriptionId, List<OrderStatus> excludedStatuses);

    List<OrderEntity> findByPrescriptionId(Long prescriptionId);
}