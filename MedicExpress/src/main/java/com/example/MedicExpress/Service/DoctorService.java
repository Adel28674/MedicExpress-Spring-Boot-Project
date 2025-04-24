package com.example.MedicExpress.Service;
import com.example.MedicExpress.Model.Doctor;


import com.example.MedicExpress.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> getAllMedecins() {
        return doctorRepository.findAll();
    }

    public Doctor getMedecinById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

}
