package com.example.MedicExpress.Model;

public enum OrderStatus {
    PENDING_DRIVER_RESPONSE,  // En attente de validation par le livreur
    WAITING_FOR_DRIVER,       // Livreur a accepté, commande prête à être prise en charge
    IN_DELIVERY,              // Commande en cours de livraison
    DELIVERED,                // Commande livrée au patient
    CANCELLED                 // Commande annulée
}

