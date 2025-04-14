//package com.example.MedicExpress.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    @Autowired
//    private final UserRepository userRepository;
//
//    @Autowired
//    private final BCryptPasswordEncoder bc ;
//
//    @Autowired
//    private final JwtUtil jwtUtil;
//
//
//    public UserEntity getUserById(Long userId){
//        return userRepository.findById(userId).orElseThrow(()->new UserDoesNotExistException(userId));
//    }
//
//    public List<UserEntity> getAllUsers(){
//        return userRepository.findAll();
//    }
//
//    public  void addUsers(List<UserEntity> listUsers){
//        userRepository.saveAll(listUsers);
//    }
//
//    public void deleteUserById(Long userId){
//        userRepository.deleteById(userId);
//    }
//
//    public void deleteUser(UserEntity userEntity){
//        userRepository.delete(userEntity);
//    }
//
//    public void deleteUsers(List<Long> listUsers){
//        userRepository.deleteAllById(listUsers);
//    }
//
//    public void deleteAll(){
//        userRepository.deleteAll();
//    }
//
//    public String cryptPassword(String password){
//        return bc.encode(password);
//    }
//
//    public String authentificate(String username, String password) {
//        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
//
//        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
//            return jwtUtil.generateToken(userOptional.get());
//        } else {
//            throw new RuntimeException("Invalid credentials");
//        }
//    }
//
//    public UserEntity register(UserEntity user) {
//        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
//            throw new UserAlreadyExistException(user.getUsername());
//        }
//        return userRepository.save(user);
//    }
//
//    public String getUsernameFromToken(String token) {
//        return jwtUtil.extractUsername(token);
//    }
//}