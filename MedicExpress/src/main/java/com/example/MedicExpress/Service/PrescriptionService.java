package com.example.MedicExpress.Service;


import com.example.MedicExpress.Model.DoctorEntity;
import com.example.MedicExpress.Model.MedicamentEntity;
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

    public PrescriptionEntity create(PrescriptionEntity prescriptionEntity) {
        if (prescriptionEntity.getMedicaments() == null || prescriptionEntity.getMedicaments().isEmpty()) {
            throw new IllegalArgumentException("Une ordonnance doit contenir au moins un médicament.");
        }

        // Vérification de l'existence du docteur
        Long doctorId = prescriptionEntity.getDoctorEntity().getId();
        if (!doctorRepository.existsById(doctorId)) {
            throw new IllegalArgumentException("Le médecin avec l'id " + doctorId + " n'existe pas.");
        }

        // Vérification de l'existence du patient
        PatientEntity patient = prescriptionEntity.getPatient();
        if (!patientRepository.existsById(patient.getId())) {
            throw new IllegalArgumentException("Le patient avec l'id " + patient.getId() + " n'existe pas.");
        }


        // Lier chaque médicament à la prescription avant la sauvegarde
        for (MedicamentEntity medicament : prescriptionEntity.getMedicaments()) {
            medicament.setPrescription(prescriptionEntity);
        }

        return prescriptionRepository.save(prescriptionEntity);
    }
}