package com.example.MedicExpress.Service;


import com.example.MedicExpress.Model.DoctorEntity;
import com.example.MedicExpress.Model.PrescriptionEntity;
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

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 30;
    private final SecureRandom random = new SecureRandom();

    public PrescriptionEntity create(PrescriptionEntity prescriptionEntity) {
        // Générer un code unique avant de sauvegarder
        String uniqueCode = generateUniqueCode();
        prescriptionEntity.setCode(uniqueCode);

        return prescriptionRepository.save(prescriptionEntity);
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = generateRandomCode();
        } while (prescriptionRepository.findByCode(code).isPresent());
        return code;
    }

    private String generateRandomCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}