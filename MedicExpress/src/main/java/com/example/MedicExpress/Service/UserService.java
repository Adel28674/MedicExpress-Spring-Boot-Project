package com.example.MedicExpress.Service;

import com.example.MedicExpress.Exception.UserAlreadyExistException;
import com.example.MedicExpress.Exception.UserDoesNotExistException;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Repository.UserRepository;
import com.example.MedicExpress.SerializationClass.ModifyPasswordRequest;
import com.example.MedicExpress.SerializationClass.UserUpdateRequest;
import com.example.MedicExpress.Utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

    @Autowired
    private final ModelMapper modelMapper;

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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

    public void updateUser(UserUpdateRequest userUpdateRequest) {
        UserEntity user = userRepository.findByEmail(userUpdateRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        modelMapper.map(userUpdateRequest, user);

        userRepository.save(user);
    }


    public void updatePassword(ModifyPasswordRequest modifyPasswordRequest){
        UserEntity user = userRepository.findByEmail(modifyPasswordRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
