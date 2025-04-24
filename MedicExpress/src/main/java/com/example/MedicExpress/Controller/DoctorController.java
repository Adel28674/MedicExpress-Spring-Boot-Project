package com.example.MedicExpress.Controller;

import com.example.MedicExpress.Model.Doctor;
import com.example.MedicExpress.Service.DoctorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/medecins")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public List<Doctor> getAllMedecins() {
        return doctorService.getAllMedecins();
    }

    @GetMapping("/{id}")
    public Doctor getMedecin(@PathVariable Long id) {
        return doctorService.getMedecinById(id);
    }



}
