package com.innovationhub.leafsense.dto;

import java.time.LocalDate;

import com.innovationhub.leafsense.enums.EquipmentStatus;
import com.innovationhub.leafsense.enums.EquipmentType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class EquipmentInventoryDto {
	private String name;
	private EquipmentType type;
	private EquipmentStatus status;
	private LocalDate lastMaintananceDate;
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
