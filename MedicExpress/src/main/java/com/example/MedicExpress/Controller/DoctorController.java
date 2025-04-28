
package com.example.MedicExpress.Controller;

import com.example.MedicExpress.Model.DoctorEntity;
import com.example.MedicExpress.Service.DoctorService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/doctor")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/allDoctor")
    public List<DoctorEntity> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public DoctorEntity getDoctor(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }



}
