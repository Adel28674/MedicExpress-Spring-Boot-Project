package com.example.MedicExpress.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotificationDTO {
    private Long id;
    private String message;
    private boolean read;
    private Date createdAt;
    private Long deliveryDriverId;
    private Long orderId;
    private String orderCode;

    // Constructeur
    public NotificationDTO(NotificationEntity entity) {
        this.id = entity.getId();
        this.message = entity.getMessage();
        this.read = entity.isRead();
        this.createdAt = entity.getCreatedAt();
        this.deliveryDriverId = entity.getDeliveryDriver() != null ? entity.getDeliveryDriver().getId() : null;
        this.orderCode = entity.getOrder() != null ? entity.getOrder().getCode() : null;
        this.orderId = entity.getOrderIdRaw();

    }

}
