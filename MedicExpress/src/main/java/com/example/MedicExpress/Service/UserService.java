package com.example.MedicExpress.Service;

import com.example.MedicExpress.Exception.UserAlreadyExistException;
import com.example.MedicExpress.Exception.UserDoesNotExistException;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

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



}
