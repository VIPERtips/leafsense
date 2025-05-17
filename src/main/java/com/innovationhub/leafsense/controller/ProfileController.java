package com.innovationhub.leafsense.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innovationhub.leafsense.dto.ProfileDto;
import com.innovationhub.leafsense.model.Profile;
import com.innovationhub.leafsense.model.User;
import com.innovationhub.leafsense.response.ApiResponse;
import com.innovationhub.leafsense.service.JwtService;
import com.innovationhub.leafsense.service.ProfileService;
import com.innovationhub.leafsense.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	

    // Helper method to extract the token from the HTTP request
    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return (token != null && token.startsWith("Bearer ")) ? token.substring(7) : null;
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Profile>> createProfile(@RequestBody ProfileDto profile,HttpServletRequest req){
    	try {
    		String username = jwtService.extractUsername(extractToken(req));
       	 User user = userService.getUserByUsername(username);
       	 Profile createdProfile = profileService.createProfile(profile, user.getUserId());
       	 return ResponseEntity.ok(new ApiResponse<>("Profile created successfully",true,createdProfile));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body((new ApiResponse<>(e.getMessage(),false,null)));
		}
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<Profile>> getUserProfile(HttpServletRequest req){
    	try {
    		String username = jwtService.extractUsername(extractToken(req));
        	User user = userService.getUserByUsername(username);
        	Profile getProfile = profileService.getProfileByUser(user);
        	 return ResponseEntity.ok(new ApiResponse<>("Profile retrieved successfully",true,getProfile));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body((new ApiResponse<>(e.getMessage(),false,null)));
		}
    }
    
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Map<String, Page<Profile>>>> getAllProfiles(@RequestParam(defaultValue =  "0") int page,
    		@RequestParam(defaultValue =  "10") int size){
    	try {
    		 Map<String, Page<Profile>> groupedProfiles = profileService.getProfilesGroupedByRole(page, size);
             return ResponseEntity.ok(new ApiResponse<>(
                 groupedProfiles.isEmpty() ? "No profiles found" : "Profiles retrieved successfully",
                 true,
                 groupedProfiles
             ));
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                      .body(new ApiResponse<>("An error occurred while fetching profiles", false, null));
		}
    }
    
    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteProfile(HttpServletRequest req){
    	try {
			String username = jwtService.extractUsername(extractToken(req));
			User user = userService.getUserByUsername(username);
			profileService.deleteProfile(user);
			return ResponseEntity.ok(new ApiResponse<>("Profile deleted successfully",true, null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((new ApiResponse<>(e.getMessage(),false,null)));
		}
    }
    
    @PutMapping
    public ResponseEntity<ApiResponse<Profile>> updateProfile(@RequestBody ProfileDto profile,HttpServletRequest req){
    	try {
    		String username = jwtService.extractUsername(extractToken(req));
       	 User user = userService.getUserByUsername(username);
       	 Profile createdProfile = profileService.updateProfileByUser(profile, user);
       	 return ResponseEntity.ok(new ApiResponse<>("Profile updated successfully",true,createdProfile));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((new ApiResponse<>(e.getMessage(),false,null)));
		}
    }
}
