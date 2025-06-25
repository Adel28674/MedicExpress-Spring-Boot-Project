package com.example.MedicExpress.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MedicExpress.Model.DoctorEntity;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.SerializationClass.PatientSearchResponse;
import com.example.MedicExpress.Service.DoctorService;

import lombok.RequiredArgsConstructor;


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

    // LEGACY: Search patients by name (nom et prénom) - INCLUDES PASSWORDS
    @GetMapping("/patients/search")
    public List<UserEntity> searchPatients(@RequestParam(value = "q", required = false) String searchTerm) {
        return doctorService.searchPatients(searchTerm);
    }

    // LEGACY: Get all patients - INCLUDES PASSWORDS
    @GetMapping("/patients")
    public List<UserEntity> getAllPatients() {
        return doctorService.getAllPatients();
    }

    // ENHANCED: Secure search patients - NO PASSWORDS
    @GetMapping("/patients/search/secure")
    public List<PatientSearchResponse> searchPatientsSecure(@RequestParam(value = "q", required = false) String searchTerm) {
        return doctorService.searchPatientsSecure(searchTerm);
    }

    // ENHANCED: Secure get all patients - NO PASSWORDS
    @GetMapping("/patients/secure")
    public List<PatientSearchResponse> getAllPatientsSecure() {
        return doctorService.getAllPatientsSecure();
    }
}
