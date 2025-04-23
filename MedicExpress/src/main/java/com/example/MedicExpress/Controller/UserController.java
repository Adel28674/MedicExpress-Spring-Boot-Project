package com.example.MedicExpress.Controller;

import com.example.MedicExpress.Exception.UserAlreadyExistException;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.SerializationClass.AuthRequest;
import com.example.MedicExpress.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserEntity userEntity){
        userEntity.setPassword(userService.cryptPassword(userEntity.getPassword()));
        try{
            userService.register(userEntity);
        }catch (RuntimeException e){
            throw new UserAlreadyExistException(userEntity.getEmail());
        }
        return ResponseEntity.ok("the user " + userEntity.getEmail() + " have been added");
    }

    @PostMapping("/addUsers")
    public ResponseEntity<String> addUsers(@RequestBody List<UserEntity> usersEntity){
        usersEntity.forEach(userEntity -> {
            userEntity.setPassword(userService.cryptPassword(userEntity.getPassword()));
        });
        userService.addUsers(usersEntity);
        return ResponseEntity.ok("the users have been added");
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

    @PostMapping("/authentification")
    public ResponseEntity<String> authentification(@RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(userService.authentificate(authRequest.getEmail(), authRequest.getPassword()));
    }

//    @PostMapping("/getUsernameFromToken/{token}")
//    public ResponseEntity<String> getUsernameFromToken(@PathVariable String token){
//        return ResponseEntity.ok(userService.getUsernameFromToken(token));
//    }

}
