package com.example.MedicExpress.Controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Repository.UserRepository;
import com.example.MedicExpress.SerializationClass.AuthRequest;
import com.example.MedicExpress.SerializationClass.SignUpRequest;
import com.example.MedicExpress.Service.AuthService;
import com.example.MedicExpress.Utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        System.out.println("🔍 LOGIN DEBUG: Received login request for email: " + request.getEmail());
        
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            System.out.println("🔍 LOGIN DEBUG: Authentication successful for: " + request.getEmail());

            UserDetails user = authService.loadUserByUsername(request.getEmail());
            System.out.println("🔍 LOGIN DEBUG: User details loaded, authorities: " + user.getAuthorities());
            
            String jwtToken = jwtUtils.generateToken(user);
            System.out.println("🔍 LOGIN DEBUG: JWT token generated: " + jwtToken.substring(0, Math.min(50, jwtToken.length())) + "...");

            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            System.out.println("🔍 LOGIN DEBUG: Authentication failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest request){
        UserEntity savedUser = authService.saveUser(request);
        return ResponseEntity.ok("User registered successfully with email: " + savedUser.getEmail());
    }

    @PostMapping("/reset-password-temp")
    public ResponseEntity<String> resetPasswordTemp(@RequestParam String email, @RequestParam String newPassword) {
        System.out.println("🔍 TEMP PASSWORD RESET: " + email + " -> " + newPassword);
        try {
            // Update password in database
            Optional<UserEntity> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                UserEntity userEntity = userOpt.get();
                userEntity.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(userEntity);
                return ResponseEntity.ok("Password reset successful for " + email);
            }
            return ResponseEntity.badRequest().body("User not found");
        } catch (Exception e) {
            System.out.println("🔍 PASSWORD RESET ERROR: " + e.getMessage());
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}
