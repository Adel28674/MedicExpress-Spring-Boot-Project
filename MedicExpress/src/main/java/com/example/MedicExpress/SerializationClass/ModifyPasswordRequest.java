package com.example.MedicExpress.SerializationClass;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ModifyPasswordRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
}