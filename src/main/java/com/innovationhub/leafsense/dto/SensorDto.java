package com.innovationhub.leafsense.dto;

import java.time.LocalDate;

import com.innovationhub.leafsense.enums.SensorStatus;
import com.innovationhub.leafsense.enums.SensorType;

public class SensorDto {
	private SensorType sensorType;
	private String location;  //optional
	private SensorStatus status;
	
	private LocalDate installedAt;

	public SensorType getSensorType() {
		return sensorType;
	}

	public void setSensorType(SensorType sensorType) {
		this.sensorType = sensorType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public SensorStatus getStatus() {
		return status;
	}

	public void setStatus(SensorStatus status) {
		this.status = status;
	}

	public LocalDate getInstalledAt() {
		return installedAt;
	}

	public void setInstalledAt(LocalDate installedAt) {
		this.installedAt = installedAt;
	}
	
}
