package com.example.MedicExpress.Service;

import com.example.MedicExpress.Model.*;
import com.example.MedicExpress.Repository.*;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private DeliveryDriverRepository deliveryDriverRepository;
    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<PrescriptionEntity> getPrescriptionsForPatient(Long patientId) {
        return prescriptionRepository.findByPatient_Id(patientId);
    }

    public List<OrderEntity> getOrdersForPatient(Long patientId) {
        return orderRepository.findByPatientId(patientId);
    }

    public Optional<PatientEntity> findById(Long id) {
        return patientRepository.findById(id);
    }

}
