package com.example.MedicExpress.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String username){
        super( username + " already exist.");
    }

}