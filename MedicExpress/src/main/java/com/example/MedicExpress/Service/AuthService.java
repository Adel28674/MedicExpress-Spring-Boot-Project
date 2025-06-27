package com.example.MedicExpress.Service;

import com.example.MedicExpress.Model.*;
import com.example.MedicExpress.Repository.*;
import com.example.MedicExpress.SerializationClass.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final DeliveryDriverRepository deliveryDriverRepository;

    @Autowired
    private final AdminRepository adminRepository;

    @Autowired
    private final DoctorRepository doctorRepository;

    @Autowired
    private final PatientRepository patientRepository;

    @Autowired
    private final PharmacyRepository pharmacyRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder ;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }

    public UserEntity saveUser(SignUpRequest signupRequest) {

        UserEntity user = new UserEntity();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(signupRequest.getRole());
        user.setName(signupRequest.getName());
        user.setFirstName(signupRequest.getFirstName());

        UserEntity savedUser = userRepository.save(user);

        switch (savedUser.getRole()) {
            case PATIENT -> {
                PatientEntity patient = new PatientEntity();
                patient.setUser(savedUser);
                patient.setSex("Null");
                patient.setAddress("Null");
                patientRepository.save(patient);
            }
            case DOCTOR -> {
                DoctorEntity doctor = new DoctorEntity();
                doctor.setUser(savedUser);
                doctor.setRpps(signupRequest.getRpps());
                doctorRepository.save(doctor);


            }
            case DELIVERY_DRIVER -> {
                DeliveryDriverEntity driver = new DeliveryDriverEntity();
                driver.setUser(savedUser);
                deliveryDriverRepository.save(driver);

            }
            case PHARMACIST -> {
                PharmacyEntity pharmacy = new PharmacyEntity();
                pharmacy.setUser(savedUser);
                pharmacyRepository.save(pharmacy);
            }
            case ADMIN -> {
                // déjà sauvegardé, rien de plus à faire
            }
            default -> throw new RuntimeException("Invalid role: " + savedUser.getRole());
        }

        return savedUser;
    }



}
