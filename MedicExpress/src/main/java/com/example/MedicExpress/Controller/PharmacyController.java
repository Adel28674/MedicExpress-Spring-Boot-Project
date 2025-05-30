package com.example.MedicExpress.Controller;

import com.example.MedicExpress.Model.DoctorEntity;
import com.example.MedicExpress.Service.DeliveryDriverService;
import com.example.MedicExpress.Service.DoctorService;

import com.example.MedicExpress.Service.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/pharmacy")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

}
