package com.example.MedicExpress.Repository;

import com.example.MedicExpress.Model.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByDeliveryDriverId(Long deliveryDriverId);
    List<NotificationEntity> findByDeliveryDriverIdOrderByCreatedAtDesc(Long deliveryDriverId);
    List<NotificationEntity> findByDeliveryDriver_IdOrderByCreatedAtDesc(Long driverId);

}
