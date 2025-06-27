package com.example.MedicExpress.Service;

import com.example.MedicExpress.Model.NotificationDTO;
import com.example.MedicExpress.Repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationDTO> getNotificationsForDriver(Long driverId) {
        return notificationRepository
                .findByDeliveryDriverIdOrderByCreatedAtDesc(driverId)
                .stream()
                .map(NotificationDTO::new)
                .collect(Collectors.toList());
    }
}
