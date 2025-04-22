package com.example.MedicExpress.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserDoesNotExistException extends RuntimeException{
    public UserDoesNotExistException(long userId){
        super("User " + userId + " does not exist.");
    }
}