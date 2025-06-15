package com.example.MedicExpress.Controller;


import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.SerializationClass.AuthRequest;
import com.example.MedicExpress.SerializationClass.SignUpRequest;
import com.example.MedicExpress.Service.AuthService;
import com.example.MedicExpress.Utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails user = authService.loadUserByUsername(request.getEmail());
        String jwtToken = jwtUtils.generateToken(user);

        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest request){
        UserEntity savedUser = authService.saveUser(request);
        return ResponseEntity.ok("User registered successfully with email: " + savedUser.getEmail());
    }

}
