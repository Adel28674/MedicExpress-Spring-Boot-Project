package com.example.MedicExpress.SerializationClass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

    private String email;
    private String password;
    private String role;
    private String kbisNumber;
}