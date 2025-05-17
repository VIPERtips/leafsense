package com.innovationhub.leafsense.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.innovationhub.leafsense.dto.LoginDto;
import com.innovationhub.leafsense.dto.UserDto;
import com.innovationhub.leafsense.enums.Role;
import com.innovationhub.leafsense.enums.UserStatus;
import com.innovationhub.leafsense.model.User;
import com.innovationhub.leafsense.repository.UserRepository;
import com.innovationhub.leafsense.response.AuthenticationResponse;




@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
   
    @Autowired
    private EmailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public AuthenticationResponse registerUser(UserDto req, Role role) {
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        if (req.getEmail().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Oops, email taken. Enter a unique email.");
        }

        User user = new User();
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setEmail(req.getEmail());
        user.setRole(role);
        
        String otp = userService.generateOTP();
        user.setOTP(otp);
        user.setOtpExpiration(LocalDateTime.now().plusHours(2));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(null);

        userRepository.save(user);

        emailSender.sendRegistrationEmail(req.getEmail(), otp);

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthenticationResponse(token, user.getRole().name(), refreshToken);
    }
    
    public void confirmOTP(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getOTP() == null || !user.getOTP().toString().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }
        if(LocalDateTime.now().isAfter(user.getOtpExpiration()) ) {
        	throw new RuntimeException("OTP expired please request new");
        }
        user.setStatus(UserStatus.APPROVED);
        user.setUpdatedAt(LocalDateTime.now());
        user.setOtpExpiration(null);
        user.setOTP(null); 
        userRepository.save(user);
    }
    
    public void requestOTP(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (user.getStatus() == UserStatus.APPROVED) {
            throw new RuntimeException("OTP request is not allowed. Your account is already approved.");
        }

        String otp = userService.generateOTP();
        user.setOTP(otp);
        user.setOtpExpiration(LocalDateTime.now().plusHours(2));
        userRepository.save(user);

        emailSender.sendRegistrationEmail(user.getEmail(), otp);
        emailSender.sendSystemAlertToAdmin(email);
    }

    public AuthenticationResponse authenticate(LoginDto req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(user.getStatus() != UserStatus.APPROVED) {
        	throw new RuntimeException("Please verify your account before you can log in. Check your email for OTP and follow instructions or request new OTP");
        }

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthenticationResponse(token, user.getRole().name(), user.getStatus().name(),user.getUserId() ,refreshToken);
    }

    public AuthenticationResponse refreshToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!jwtService.isValid(refreshToken, user)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String newAccessToken = jwtService.generateToken(user);
        return new AuthenticationResponse(newAccessToken, user.getRole().name(),user.getStatus().name(),user.getUserId(), refreshToken);
    }
}
