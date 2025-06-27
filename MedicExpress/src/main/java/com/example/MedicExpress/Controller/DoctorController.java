
package com.example.MedicExpress.Controller;

import com.example.MedicExpress.Model.DoctorEntity;
import com.example.MedicExpress.Model.PatientEntity;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Repository.DoctorRepository;
import com.example.MedicExpress.Service.DoctorService;

import com.example.MedicExpress.Service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/doctor")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/allDoctor")
    public List<DoctorEntity> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public DoctorEntity getDoctor(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    private final DoctorRepository doctorRepository;

    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/{doctorId}/patients")
    public ResponseEntity<List<UserEntity>> getPatientsByDoctor(@PathVariable Long doctorId) {
        List<UserEntity> patients = doctorRepository.findDistinctPatientsByDoctorId(doctorId);
        if (patients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(patients);
    }
}
