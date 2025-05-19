package com.innovationhub.leafsense.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.innovationhub.leafsense.enums.Role;
import com.innovationhub.leafsense.enums.UserStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	@Column(unique = true,nullable = false)
	@NotNull(message = "Please enter a email")
	private String email;
	
	@NotNull(message = "Please enter a password")
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	private String resetToken;
	private LocalDateTime tokenExpiration;
	private LocalDateTime otpExpiration;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;
	
	
	public  LocalDateTime getOtpExpiration() {
		return otpExpiration;
	}

	public  void setOtpExpiration(LocalDateTime otpExpiration) {
		this.otpExpiration = otpExpiration;
	}

	private String OTP;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	
	@Enumerated(EnumType.STRING)
	 private UserStatus status = UserStatus.PENDING;
	
	public User() {
		
	}

	public User(String email, String password, Role role) {
		
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public LocalDateTime getTokenExpiration() {
		return tokenExpiration;
	}

	public void setTokenExpiration(LocalDateTime tokenExpiration) {
		this.tokenExpiration = tokenExpiration;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}
	
	

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role));
	}
	public boolean isResetTokenValid() {
		return tokenExpiration != null && LocalDateTime.now().isBefore(tokenExpiration);
	}

	@Override
	public String getUsername() {
		
		return email;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updateedAt) {
		this.updatedAt = updateedAt;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	
}
