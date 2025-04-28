package com.example.MedicExpress.Controller;
import com.example.MedicExpress.Service.DeliveryDriverService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/deliveryDriver")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DeliveryDriverController {

    @Autowired
    private DeliveryDriverService deliveryDriverService;


}
