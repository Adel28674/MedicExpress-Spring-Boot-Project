package com.example.MedicExpress.Service;

import com.example.MedicExpress.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryDriverService {

    @Autowired
    private OrderRepository orderRepository;
}
