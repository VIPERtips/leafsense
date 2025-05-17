package com.innovationhub.leafsense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innovationhub.leafsense.dto.FarmDto;
import com.innovationhub.leafsense.model.Farm;
import com.innovationhub.leafsense.model.Profile;
import com.innovationhub.leafsense.model.User;
import com.innovationhub.leafsense.response.ApiResponse;
import com.innovationhub.leafsense.service.FarmService;
import com.innovationhub.leafsense.service.JwtService;
import com.innovationhub.leafsense.service.ProfileService;
import com.innovationhub.leafsense.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/farm")
public class FarmController {

	@Autowired
	private FarmService farmService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;

	// Helper method to extract the token from the HTTP request
	private String extractToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		return (token != null && token.startsWith("Bearer ")) ? token.substring(7) : null;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Farm>> registerFarm(@RequestBody FarmDto farmDto, HttpServletRequest req) {
		try {
			String username = jwtService.extractUsername(extractToken(req));
			User user = userService.getUserByUsername(username);
			Profile profile = profileService.getProfileByUser(user);
			Farm newFarm = farmService.registerFarm(farmDto, profile.getProfileId());
			return ResponseEntity.ok(new ApiResponse<>("Farm registered successfully", true, newFarm));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body((new ApiResponse<>(e.getMessage(), false, null)));
		}
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<Farm>>> getFarms(HttpServletRequest req,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		try {
			String username = jwtService.extractUsername(extractToken(req));
			User user = userService.getUserByUsername(username);
			Profile profile = profileService.getProfileByUser(user);
			Page<Farm> farms = farmService.getUserFarms(profile.getProfileId(),page, size );
			return ResponseEntity.ok(new ApiResponse<>("Farm(s) retrived successfully", true, farms));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((new ApiResponse<>(e.getMessage(), false, null)));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Farm>> updateFarm(@RequestBody FarmDto farmDto,HttpServletRequest req,int id) {
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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Farm>> deleteFarm(HttpServletRequest req,int id) {
		try {
			farmService.deleteFarm(id);
			 return ResponseEntity.ok(new ApiResponse<>("Farm deleted successfully", true, null));
		} catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), false, null));
	}
	}
}