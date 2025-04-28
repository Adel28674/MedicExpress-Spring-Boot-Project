package com.example.MedicExpress.Exception;

public class PrescriptionAlreadyExistException extends RuntimeException {
    public PrescriptionAlreadyExistException(String code) {
        super("Prescription with code " + code + " already exists.");
    }
}
