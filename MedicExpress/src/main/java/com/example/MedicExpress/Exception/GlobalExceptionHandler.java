package com.example.MedicExpress.Exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<String> handleRuntimeException(UserDoesNotExistException ex) {
        return ResponseEntity.status(404).body("Global handler: " + ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleRuntimeException(UserAlreadyExistException ex) {
        return ResponseEntity.status(500).body("Global handler: " + ex.getMessage());
    }
}