package com.example.MedicExpress.Controller;

import com.example.MedicExpress.Model.DoctorEntity;
import com.example.MedicExpress.Service.DoctorService;

import com.example.MedicExpress.Service.OrderService;
import com.example.MedicExpress.Service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;
}
