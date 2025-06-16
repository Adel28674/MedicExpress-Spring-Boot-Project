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

        switch (user.getRole()) {
            case PATIENT -> {
                PatientEntity patient = new PatientEntity();
                patient.setId(user.getId());
                patientRepository.save(patient);
            }
            case DOCTOR -> {
                DoctorEntity doctorEntity = new DoctorEntity();
                doctorEntity.setId(user.getId());
                doctorEntity.setRpps(signupRequest.getRpps());
                doctorRepository.save(doctorEntity);

            }
            case DELIVERY_DRIVER -> {
                DeliveryDriverEntity deliveryDriverEntity = new DeliveryDriverEntity();
                deliveryDriverEntity.setId(user.getId());
                deliveryDriverRepository.save(deliveryDriverEntity);
            }
            case PHARMACIST -> {
                PharmacyEntity pharmacyEntity = new PharmacyEntity();
                pharmacyEntity.setId(user.getId());
                pharmacyRepository.save(pharmacyEntity);
            }
            case ADMIN -> {
                adminRepository.save(user);
            }
            default -> {
            throw new RuntimeException("");}
        }
        return userRepository.save(user);
    }


}
