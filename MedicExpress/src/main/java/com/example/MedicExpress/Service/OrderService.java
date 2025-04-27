package com.example.MedicExpress.Service;

import com.example.MedicExpress.Repository.OrderRepository;
import com.example.MedicExpress.Repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
}
