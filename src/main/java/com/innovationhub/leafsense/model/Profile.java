package com.innovationhub.leafsense.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int profileId;
	
	private String fullname;
	private String phone;
	private LocalDate createdAt;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
	private User user;
	
	@OneToMany(mappedBy = "profile",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Farm> farms;
	
	public Profile() {
		
	}
	





	public int getProfileId() {
		return profileId;
	}




	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}




	public String getFullname() {
		return fullname;
	}




	public void setFullname(String fullname) {
		this.fullname = fullname;
	}




	public String getPhone() {
		return phone;
	}




	public void setPhone(String phone) {
		this.phone = phone;
	}




	public User getUser() {
		return user;
	}




	public void setUser(User user) {
		this.user = user;
	}






	public LocalDate getCreatedAt() {
		return createdAt;
	}






	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}






	public List<Farm> getFarms() {
		return farms;
	}






	public void setFarms(List<Farm> farms) {
		this.farms = farms;
	}





}
