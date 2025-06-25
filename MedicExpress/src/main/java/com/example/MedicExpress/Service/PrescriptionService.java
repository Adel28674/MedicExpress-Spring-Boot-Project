package com.example.MedicExpress.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MedicExpress.Model.MedicamentEntity;
import com.example.MedicExpress.Model.PrescriptionEntity;
import com.example.MedicExpress.Model.Role;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Repository.DoctorRepository;
import com.example.MedicExpress.Repository.PatientRepository;
import com.example.MedicExpress.Repository.PrescriptionRepository;
import com.example.MedicExpress.Repository.UserRepository;
import com.example.MedicExpress.SerializationClass.CreatePrescriptionRequest;
import com.example.MedicExpress.SerializationClass.PatientPrescriptionResponse;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    public PrescriptionEntity create(PrescriptionEntity prescriptionEntity) {
        if (prescriptionEntity.getMedicaments() == null || prescriptionEntity.getMedicaments().isEmpty()) {
            throw new IllegalArgumentException("Une ordonnance doit contenir au moins un médicament.");
        }

        // 🔍 Vérification de l'existence du docteur
        Long doctorId = prescriptionEntity.getDoctor().getId();
        if (!doctorRepository.existsById(doctorId)) {
            throw new IllegalArgumentException("Le médecin avec l'id " + doctorId + " n'existe pas.");
        }

        // ✅ Correction ici : on récupère l'ID depuis l'objet PatientEntity
        Long patientId = prescriptionEntity.getPatient().getId();
        if (!patientRepository.existsById(patientId)) {
            throw new IllegalArgumentException("Le patient avec l'id " + patientId + " n'existe pas.");
        }

        // 💊 Lier chaque médicament à la prescription avant la sauvegarde
        for (MedicamentEntity medicament : prescriptionEntity.getMedicaments()) {
            medicament.setPrescription(prescriptionEntity);
        }

        return prescriptionRepository.save(prescriptionEntity);
    }

    public PrescriptionEntity createFromRequest(CreatePrescriptionRequest request) {
        // Validation
        if (request.getMedications() == null || request.getMedications().isEmpty()) {
            throw new IllegalArgumentException("Une ordonnance doit contenir au moins un médicament.");
        }

        // Find user with DOCTOR role by email
        Optional<UserEntity> doctorUserOpt = userRepository.findByEmailAndRole(request.getDoctorId(), Role.DOCTOR);
        if (doctorUserOpt.isEmpty()) {
            throw new IllegalArgumentException("Le médecin avec l'email " + request.getDoctorId() + " n'existe pas.");
        }
        UserEntity doctorUser = doctorUserOpt.get();

        // Find PATIENT user by ID
        Optional<UserEntity> patientUserOpt = userRepository.findById(request.getPatientId());
        if (patientUserOpt.isEmpty()) {
            throw new IllegalArgumentException("Le patient avec l'id " + request.getPatientId() + " n'existe pas.");
        }
        
        UserEntity patientUser = patientUserOpt.get();
        if (patientUser.getRole() != Role.PATIENT) {
            throw new IllegalArgumentException("L'utilisateur avec l'id " + request.getPatientId() + " n'est pas un patient.");
        }

        // Create prescription entity with direct user references
        PrescriptionEntity prescription = new PrescriptionEntity();
        prescription.setDoctor(doctorUser);
        prescription.setPatient(patientUser);

        // Create medications
        List<MedicamentEntity> medicaments = new ArrayList<>();
        for (CreatePrescriptionRequest.MedicationRequest medRequest : request.getMedications()) {
            MedicamentEntity medicament = new MedicamentEntity();
            medicament.setNom(medRequest.getName());
            medicament.setPrescription(prescription);
            medicaments.add(medicament);
        }
        prescription.setMedicaments(medicaments);

        return prescriptionRepository.save(prescription);
    }

    private PatientPrescriptionResponse.DoctorInfo extractDoctorInfo(PrescriptionEntity prescriptionEntity) {
        Long doctorId = prescriptionEntity.getDoctor().getId();
        Optional<UserEntity> doctorUserOpt = userRepository.findById(doctorId);
        
        if (doctorUserOpt.isPresent()) {
            UserEntity doctorUser = doctorUserOpt.get();
            PatientPrescriptionResponse.DoctorInfo doctorInfo = new PatientPrescriptionResponse.DoctorInfo();
            doctorInfo.setId(doctorUser.getId());
            doctorInfo.setName(doctorUser.getName());
            doctorInfo.setFirstName(doctorUser.getFirstName());
            doctorInfo.setEmail(doctorUser.getEmail());
            // Note: RPPS is not available in UserEntity, only in DoctorEntity
            // If RPPS is needed, the database schema needs to be updated
            return doctorInfo;
        }
        
        return null;
    }

}