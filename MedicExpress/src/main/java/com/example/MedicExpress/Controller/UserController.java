package com.example.MedicExpress.Controller;

import com.example.MedicExpress.Exception.UserAlreadyExistException;
import com.example.MedicExpress.Model.PatientEntity;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.SerializationClass.AuthRequest;
import com.example.MedicExpress.SerializationClass.UserUpdateRequest;
import com.example.MedicExpress.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("Welcome");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/addUsers")
    public ResponseEntity<String> addUsers(@RequestBody List<UserEntity> usersEntity){
        usersEntity.forEach(userEntity -> {
            userEntity.setPassword(userService.cryptPassword(userEntity.getPassword()));
        });
        userService.addUsers(usersEntity);
        return ResponseEntity.ok("the users have been added");
    }

    @PostMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateRequest userUpdateRequest){
        userService.updateUser(userUpdateRequest);
        return ResponseEntity.ok(userUpdateRequest.getEmail() + "'s informations have been updated");
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return ResponseEntity.ok(userId + " has been deleted");
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestBody UserEntity userEntity){
        userService.deleteUser(userEntity);
        return ResponseEntity.ok(userEntity.getEmail() + " has been deleted");
    }

    @DeleteMapping("/deleteUsers")
    public ResponseEntity<String> deleteUsers(@RequestBody List<Long> userIds){
        userService.deleteUsers(userIds);
        return ResponseEntity.ok("");
    }


    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleterAllUser() {
        userService.deleteAll();
        return ResponseEntity.ok("");
    }


}
