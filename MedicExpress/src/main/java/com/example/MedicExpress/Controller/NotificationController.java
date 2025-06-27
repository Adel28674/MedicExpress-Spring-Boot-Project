package com.example.MedicExpress.Controller;

import com.example.MedicExpress.Model.NotificationDTO;
import com.example.MedicExpress.Service.NotificationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*") // À ajuster selon votre front
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{driverId}")
    public List<NotificationDTO> getNotifications(@PathVariable Long driverId) {
        return notificationService.getNotificationsForDriver(driverId);
    }
}

