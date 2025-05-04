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

    public PrescriptionEntity create(PrescriptionEntity prescriptionEntity) {
        return prescriptionRepository.save(prescriptionEntity);
    }


}