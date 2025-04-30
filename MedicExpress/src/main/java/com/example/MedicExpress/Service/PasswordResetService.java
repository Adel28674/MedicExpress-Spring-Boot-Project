package com.example.MedicExpress.Service;

import com.example.MedicExpress.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;
}
