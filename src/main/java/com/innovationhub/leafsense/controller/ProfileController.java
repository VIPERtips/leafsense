package com.innovationhub.leafsense.controller;

import com.innovationhub.leafsense.dto.ProfileDto;
import com.innovationhub.leafsense.model.Profile;
import com.innovationhub.leafsense.model.User;
import com.innovationhub.leafsense.response.ApiResponse;
import com.innovationhub.leafsense.service.JwtService;
import com.innovationhub.leafsense.service.ProfileService;
import com.innovationhub.leafsense.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired private ProfileService profileService;
    @Autowired private UserService userService;
    @Autowired private JwtService jwtService;

    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return (token != null && token.startsWith("Bearer ")) ? token.substring(7) : null;
    }

    @Operation(
        summary = "Create a profile",
        description = "Creates a user profile and links it to the currently authenticated user."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<Profile>> createProfile(
            @RequestBody ProfileDto profile,
            HttpServletRequest req) {
        try {
            String username = jwtService.extractUsername(extractToken(req));
            User user = userService.getUserByUsername(username);
            Profile createdProfile = profileService.createProfile(profile, user.getUserId());
            return ResponseEntity.ok(new ApiResponse<>("Profile created successfully", true, createdProfile));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(
        summary = "Get current user profile",
        description = "Fetches the profile associated with the currently logged-in user."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<Profile>> getUserProfile(HttpServletRequest req) {
        try {
            String username = jwtService.extractUsername(extractToken(req));
            User user = userService.getUserByUsername(username);
            Profile profile = profileService.getProfileByUser(user);
            return ResponseEntity.ok(new ApiResponse<>("Profile retrieved successfully", true, profile));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(
        summary = "Get all profiles grouped by role",
        description = "Returns paginated user profiles grouped by role (e.g., FARMER, ADMIN, etc.)."
    )
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Map<String, Page<Profile>>>> getAllProfiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Page<Profile>> groupedProfiles = profileService.getProfilesGroupedByRole(page, size);
            String msg = groupedProfiles.isEmpty() ? "No profiles found" : "Profiles retrieved successfully";
            return ResponseEntity.ok(new ApiResponse<>(msg, true, groupedProfiles));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error fetching profiles", false, null));
        }
    }

    @Operation(
        summary = "Delete current user profile",
        description = "Deletes the authenticated user's profile and its associated data."
    )
    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteProfile(HttpServletRequest req) {
        try {
            String username = jwtService.extractUsername(extractToken(req));
            User user = userService.getUserByUsername(username);
            profileService.deleteProfile(user);
            return ResponseEntity.ok(new ApiResponse<>("Profile deleted successfully", true, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(
        summary = "Update current user profile",
        description = "Updates the authenticated user's profile information."
    )
    @PutMapping
    public ResponseEntity<ApiResponse<Profile>> updateProfile(
            @RequestBody ProfileDto profile,
            HttpServletRequest req) {
        try {
            String username = jwtService.extractUsername(extractToken(req));
            User user = userService.getUserByUsername(username);
            Profile updatedProfile = profileService.updateProfileByUser(profile, user);
            return ResponseEntity.ok(new ApiResponse<>("Profile updated successfully", true, updatedProfile));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }
}
