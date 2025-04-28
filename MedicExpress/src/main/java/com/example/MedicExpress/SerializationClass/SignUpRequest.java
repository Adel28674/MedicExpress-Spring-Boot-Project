package com.example.MedicExpress.SerializationClass;

import com.example.MedicExpress.Model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String email;
    private String password;
    private String name;
    private String firstName;
    private Role role;
}
