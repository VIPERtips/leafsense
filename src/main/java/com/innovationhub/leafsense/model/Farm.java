package com.innovationhub.leafsense.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Farm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int farmId;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "profile_id", referencedColumnName = "profileId", nullable = false)
	private Profile profile;
	
	private String name;
	private String location;
	private String size;
	private String cropType;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	@OneToMany(mappedBy = "farm",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<EquipmentInventory> equipmentInventories;
	
	
	public Farm() {
		// TODO Auto-generated constructor stub
	}

	public int getFarmId() {
		return farmId;
	}

	public void setFarmId(int farmId) {
		this.farmId = farmId;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getCropType() {
		return cropType;
	}

	public void setCropType(String cropType) {
		this.cropType = cropType;
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

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<EquipmentInventory> getEquipmentInventories() {
		return equipmentInventories;
	}

	public void setEquipmentInventories(List<EquipmentInventory> equipmentInventories) {
		this.equipmentInventories = equipmentInventories;
	}
	
}
