package com.innovationhub.leafsense.controller;

import com.innovationhub.leafsense.dto.LoginDto;
import com.innovationhub.leafsense.dto.UserDto;
import com.innovationhub.leafsense.enums.Role;
import com.innovationhub.leafsense.model.User;
import com.innovationhub.leafsense.response.ApiResponse;
import com.innovationhub.leafsense.response.AuthenticationResponse;
import com.innovationhub.leafsense.service.AuthenticationService;
import com.innovationhub.leafsense.service.EmailSender;
import com.innovationhub.leafsense.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailSender emailSender;

    @Operation(summary = "User registration", description = "Registers a user with default role FARMER")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Map<String, Object>>> registerUser(
            @RequestBody UserDto request,
            @RequestParam(defaultValue = "FARMER") String role) {
        try {
            Role userRole = Role.valueOf(role.toUpperCase());
            AuthenticationResponse authResponse = authenticationService.registerUser(request, userRole);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("user", Map.of("email", request.getEmail(), "role", userRole.name()));
            responseData.put("token", authResponse.getToken());

            return ResponseEntity.ok(new ApiResponse<>("Registration successful. Check your email for OTP.", true, responseData));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage(), false, null));
        }
    }

    @Operation(summary = "Confirm OTP", description = "Confirms OTP to complete user registration.")
    @PostMapping("/confirm-otp")
    public ResponseEntity<ApiResponse<String>> confirmOTP(@RequestParam String email, @RequestParam String otp) {
        try {
            authenticationService.confirmOTP(email, otp);
            return ResponseEntity.ok(new ApiResponse<>("OTP confirmed successfully", true, null));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage(), false, null));
        }
    }

    @Operation(summary = "User login", description = "Authenticates user and returns token")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@RequestBody LoginDto loginDto) {
        try {
            AuthenticationResponse authResponse = authenticationService.authenticate(loginDto);
            return ResponseEntity.ok(new ApiResponse<>("Login successful", true, authResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("Login failed: " + e.getMessage(), false, null));
        }
    }

    @Operation(summary = "Refresh token", description = "Refreshes JWT token")
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> refreshToken(@RequestParam String refreshToken) {
        AuthenticationResponse authResponse = authenticationService.refreshToken(refreshToken);
        return ResponseEntity.ok(new ApiResponse<>("Token refreshed successfully", true, authResponse));
    }

    @Operation(summary = "Forgot password", description = "Sends reset password link to email")
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with this email.");
        }

        userService.generateResetToken(user);
        emailSender.sendPasswordResetEmail(user.getEmail(), user.getUsername(), user.getResetToken());

        return ResponseEntity.ok("Password reset link sent to your email.");
    }

    @Operation(summary = "Request OTP", description = "Resends a new OTP to email")
    @PostMapping("/request-otp")
    public ResponseEntity<String> requestOtp(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with this email.");
        }

        authenticationService.requestOTP(email);
        return ResponseEntity.ok("OTP request successful. Check your email.");
    }

    @Operation(summary = "Reset password", description = "Resets password using reset token")
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        User user = userService.findByResetToken(token);
        if (user == null || !user.isResetTokenValid()) {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }

        userService.updatePassword(user, newPassword);
        return ResponseEntity.ok("Password reset successful. You can now log in.");
    }
}
