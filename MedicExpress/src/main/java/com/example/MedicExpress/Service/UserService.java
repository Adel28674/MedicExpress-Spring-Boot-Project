package com.example.MedicExpress.Service;

import com.example.MedicExpress.Exception.UserDoesNotExistException;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserEntity getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new UserDoesNotExistException(userId));
    }

}
