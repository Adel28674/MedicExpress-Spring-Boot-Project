package com.example.MedicExpress.Repository;


import com.example.MedicExpress.Model.DeliveryDriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryDriverRepository extends JpaRepository<DeliveryDriverEntity, Long>{}
