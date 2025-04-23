package com.example.MedicExpress.Service;

import com.example.MedicExpress.Exception.UserAlreadyExistException;
import com.example.MedicExpress.Exception.UserDoesNotExistException;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder bc ;


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
//            return jwtUtil.generateToken(userOptional.get());
            return null;
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public String cryptPassword(String password){
        return bc.encode(password);
    }

}
