package com.example.MedicExpress.Repository;

import com.example.MedicExpress.Model.OrderEntity;
import com.example.MedicExpress.Model.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
    List<OrderEntity> findByPatientId(Long patientId);

}
