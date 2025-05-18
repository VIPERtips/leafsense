package com.innovationhub.leafsense.model;

import java.time.LocalDate;

import com.innovationhub.leafsense.enums.EquipmentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innovationhub.leafsense.enums.EquipmentStatus;

import jakarta.persistence.*;

@Entity
public class EquipmentInventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int equipmentId;
	@ManyToOne
	@JoinColumn(name = "farm_id",referencedColumnName = "farmId",nullable = false)
	@JsonIgnore
	private Farm farm;
	private String name;
	@Enumerated(EnumType.STRING)
	private EquipmentType type;
	@Enumerated(EnumType.STRING)
	private EquipmentStatus status;
	private LocalDate lastMaintananceDate;
	
	public EquipmentInventory() {
		// TODO Auto-generated constructor stub
	}

	public int getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EquipmentType getType() {
		return type;
	}

	public void setType(EquipmentType type) {
		this.type = type;
	}

	public EquipmentStatus getStatus() {
		return status;
	}

	public void setStatus(EquipmentStatus status) {
		this.status = status;
	}

	public LocalDate getLastMaintananceDate() {
		return lastMaintananceDate;
	}

	public void setLastMaintananceDate(LocalDate lastMaintananceDate) {
		this.lastMaintananceDate = lastMaintananceDate;
	}
	
	
}
