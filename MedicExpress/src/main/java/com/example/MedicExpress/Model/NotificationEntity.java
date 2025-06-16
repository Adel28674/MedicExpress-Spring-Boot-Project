package com.example.MedicExpress.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "notifications")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private boolean read = false;

    @Column(name = "created_at")
    private Date createdAt = new Date(System.currentTimeMillis());

    @ManyToOne
    @JoinColumn(name = "delivery_driver_id")
    private DeliveryDriverEntity deliveryDriver;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}