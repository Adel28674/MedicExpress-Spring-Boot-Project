package com.example.MedicExpress.Model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequest {
    private String email;
    private String password;
    private Role role;

    private String name;
    private String firstName;

    private String rpps;
    private String kbisNumber;

}

