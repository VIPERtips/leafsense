package com.innovationhub.leafsense.controller;

import com.innovationhub.leafsense.dto.FarmDto;
import com.innovationhub.leafsense.model.Farm;
import com.innovationhub.leafsense.model.Profile;
import com.innovationhub.leafsense.model.User;
import com.innovationhub.leafsense.response.ApiResponse;
import com.innovationhub.leafsense.service.FarmService;
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

@RestController
@RequestMapping("/api/farm")
public class FarmController {

    @Autowired private FarmService farmService;
    @Autowired private JwtService jwtService;
    @Autowired private UserService userService;
    @Autowired private ProfileService profileService;

    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return (token != null && token.startsWith("Bearer ")) ? token.substring(7) : null;
    }

    @Operation(
        summary = "Register a new farm",
        description = "Creates a farm and assigns it to the currently authenticated user."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<Farm>> registerFarm(@RequestBody FarmDto farmDto, HttpServletRequest req) {
        try {
            String username = jwtService.extractUsername(extractToken(req));
            User user = userService.getUserByUsername(username);
            Profile profile = profileService.getProfileByUser(user);
            Farm newFarm = farmService.registerFarm(farmDto, profile.getProfileId());
            return ResponseEntity.ok(new ApiResponse<>("Farm registered successfully", true, newFarm));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(
        summary = "Get all farms owned by user",
        description = "Returns a paginated list of farms created by the authenticated user."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<Page<Farm>>> getFarms(
            HttpServletRequest req,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            String username = jwtService.extractUsername(extractToken(req));
            User user = userService.getUserByUsername(username);
            Profile profile = profileService.getProfileByUser(user);
            Page<Farm> farms = farmService.getUserFarms(profile.getProfileId(), page, size);
            return ResponseEntity.ok(new ApiResponse<>("Farms retrieved successfully", true, farms));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(
        summary = "Update farm details",
        description = "Updates the information of a specific farm owned by the authenticated user."
    )
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Farm>> updateFarm(
            @RequestBody FarmDto farmDto,
            HttpServletRequest req,
            @PathVariable int id) {
        try {
            String username = jwtService.extractUsername(extractToken(req));
            User user = userService.getUserByUsername(username);
            Profile profile = profileService.getProfileByUser(user);
            Farm updatedFarm = farmService.updateFarm(farmDto, id, profile.getProfileId());
            return ResponseEntity.ok(new ApiResponse<>("Farm updated successfully", true, updatedFarm));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(
        summary = "Delete a farm",
        description = "Removes a farm from the system based on its ID. Only the owner can perform this action."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Farm>> deleteFarm(HttpServletRequest req, @PathVariable int id) {
        try {
            farmService.deleteFarm(id);
            return ResponseEntity.ok(new ApiResponse<>("Farm deleted successfully", true, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }
}
