package com.example.MedicExpress.Service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MedicExpress.Model.DoctorEntity;
import com.example.MedicExpress.Model.Role;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Repository.DoctorRepository;
import com.example.MedicExpress.Repository.UserRepository;
import com.example.MedicExpress.SerializationClass.PatientSearchResponse;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    public List<DoctorEntity> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public DoctorEntity getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    // Search patients by name (nom et prénom) - LEGACY VERSION
    public List<UserEntity> searchPatients(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            // Return all patients if no search term provided
            return userRepository.findByRole(Role.PATIENT);
        }
        return userRepository.findPatientsByNameContaining(searchTerm.trim(), Role.PATIENT);
    }

    // Get all patients - LEGACY VERSION
    public List<UserEntity> getAllPatients() {
        return userRepository.findByRole(Role.PATIENT);
    }

    // ENHANCED: Search patients with secure response - NO PASSWORDS
    public List<PatientSearchResponse> searchPatientsSecure(String searchTerm) {
        List<UserEntity> users;
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            users = userRepository.findByRole(Role.PATIENT);
        } else {
            users = userRepository.findPatientsByNameContaining(searchTerm.trim(), Role.PATIENT);
        }
        return convertToPatientSearchResponses(users);
    }

    // ENHANCED: Get all patients with secure response
    public List<PatientSearchResponse> getAllPatientsSecure() {
        List<UserEntity> users = userRepository.findByRole(Role.PATIENT);
        return convertToPatientSearchResponses(users);
    }

    private List<PatientSearchResponse> convertToPatientSearchResponses(List<UserEntity> users) {
        List<PatientSearchResponse> responses = new ArrayList<>();
        for (UserEntity user : users) {
            PatientSearchResponse response = new PatientSearchResponse();
            response.setUserId(user.getId());
            response.setPatientId(user.getId()); // Same as userId due to 1:1 relationship
            response.setName(user.getName());
            response.setFirstName(user.getFirstName());
            response.setEmail(user.getEmail());
            
            // Compute full name for display
            String fullName = "";
            boolean hasCompleteName = false;
            
            if (user.getFirstName() != null && user.getName() != null) {
                fullName = user.getFirstName() + " " + user.getName();
                hasCompleteName = true;
            } else if (user.getFirstName() != null) {
                fullName = user.getFirstName();
                hasCompleteName = false;
            } else if (user.getName() != null) {
                fullName = user.getName();
                hasCompleteName = false;
            } else {
                fullName = user.getEmail(); // Fallback to email if no name
                hasCompleteName = false;
            }
            
            response.setFullName(fullName);
            response.setHasCompleteName(hasCompleteName);
            responses.add(response);
        }
        return responses;
    }
}