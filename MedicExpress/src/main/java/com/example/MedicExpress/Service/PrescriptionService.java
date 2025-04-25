package com.example.MedicExpress.Service;


import com.example.MedicExpress.Model.DoctorEntity;
import com.example.MedicExpress.Model.PatientEntity;
import com.example.MedicExpress.Model.PrescriptionEntity;
import com.example.MedicExpress.Repository.DoctorRepository;
import com.example.MedicExpress.Repository.PatientRepository;
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

    @Autowired
    private PatientRepository patientRepository;

    public String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }

    public PrescriptionEntity createCode(Long medecinId, List<String> medicaments, Long patientId ) {
        DoctorEntity doctorEntity = doctorRepository.findById(medecinId).orElseThrow();
        PatientEntity patient = patientRepository.findById(patientId).orElseThrow();
        String code = generateRandomCode(30);
        PrescriptionEntity prescriptionEntity = new PrescriptionEntity(code, doctorEntity, medicaments, patient);
        return prescriptionRepository.save(prescriptionEntity);
    }

}