package com.innovationhub.leafsense.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.innovationhub.leafsense.dto.UserDto;
import com.innovationhub.leafsense.enums.Role;
import com.innovationhub.leafsense.model.User;
import com.innovationhub.leafsense.repository.UserRepository;



@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
  

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); 
        return String.valueOf(otp);
    }

    public void generateResetToken(User user) {
        String token = UUID.randomUUID().toString(); 
        user.setResetToken(token);
        user.setTokenExpiration(LocalDateTime.now().plusMinutes(30)); 
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public User findByResetToken(String token) {
        return userRepository.findByResetToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null); 
        user.setTokenExpiration(null);
        userRepository.save(user);
    }
    
    public List<UserDto> getUsersByRole(Role role) {
        List<User> users = userRepository.findByRole(role);
        return users.stream()
                .map(user -> new UserDto(
                        user.getUserId(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }

    public UserDto getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        return new UserDto(
                user.getUserId(), 
                user.getEmail()
        );
    }

    public void deleteUserById(int id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
    
   


    public User getUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found for username " + email));
    }
}
