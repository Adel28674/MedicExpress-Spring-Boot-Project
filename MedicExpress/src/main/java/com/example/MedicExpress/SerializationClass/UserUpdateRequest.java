package com.example.MedicExpress.SerializationClass;

import com.example.MedicExpress.Model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String email;
    private String name;
    private String firstName;
}

