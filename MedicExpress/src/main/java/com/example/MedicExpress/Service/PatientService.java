package com.example.MedicExpress.Service;

import com.example.MedicExpress.Repository.OrderRepository;
import com.example.MedicExpress.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

}
