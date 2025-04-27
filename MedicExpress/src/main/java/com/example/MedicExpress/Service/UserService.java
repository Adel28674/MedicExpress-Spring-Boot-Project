package com.example.MedicExpress.Service;

import com.example.MedicExpress.Exception.UserAlreadyExistException;
import com.example.MedicExpress.Exception.UserDoesNotExistException;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Repository.UserRepository;
import com.example.MedicExpress.Utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder bc ;

    @Autowired
    private final JwtUtils jwtUtil;

    public String cryptPassword(String password){
        return bc.encode(password);
    }

    public UserEntity getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new UserDoesNotExistException(userId));
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public  void addUsers(List<UserEntity> listUsers){
        userRepository.saveAll(listUsers);
    }

    public UserEntity register(UserEntity user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistException(user.getEmail());
        }
        return userRepository.save(user);
    }

    public void deleteUserById(Long userId){
        userRepository.deleteById(userId);
    }

    public void deleteUser(UserEntity userEntity){
        userRepository.delete(userEntity);
    }

    public void deleteUsers(List<Long> listUsers){
        userRepository.deleteAllById(listUsers);
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }

    public String authentificate(String username, String password) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(username);

        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            return jwtUtil.generateToken(userOptional.get().getEmail());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
