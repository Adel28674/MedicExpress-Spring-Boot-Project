package com.example.MedicExpress.Service;


import com.example.MedicExpress.Model.Doctor;
import com.example.MedicExpress.Model.Prescription;
import com.example.MedicExpress.Repository.DoctorRepository;
import com.example.MedicExpress.Repository.PrescriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }

    public Prescription createCode(Long medecinId, List<String> medicaments) {
        Doctor doctor = doctorRepository.findById(medecinId).orElseThrow();
        String code = generateRandomCode(30);
        Prescription prescription = new Prescription(code, doctor, medicaments);
        return prescriptionRepository.save(prescription);
    }

}