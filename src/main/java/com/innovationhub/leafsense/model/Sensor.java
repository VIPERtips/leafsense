package com.innovationhub.leafsense.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innovationhub.leafsense.enums.SensorStatus;
import com.innovationhub.leafsense.enums.SensorType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Sensor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sensorId;
	@ManyToOne
	@JoinColumn(name = "farm_id",referencedColumnName = "farmId",nullable = false)
	@JsonIgnore
	private Farm farm;
	@Enumerated(EnumType.STRING)
	private SensorType sensorType;
	private String location;  //optional
	@Enumerated(EnumType.STRING)
	private SensorStatus status;
	
	private LocalDate installedAt;
	
	@OneToMany(mappedBy = "sensor",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<SensorReadings> sensorReadings;
	
	public Sensor() {
		// TODO Auto-generated constructor stub
	}

	public int getSensorId() {
		return sensorId;
	}

	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

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

	public List<SensorReadings> getSensorReadings() {
		return sensorReadings;
	}

	public void setSensorReadings(List<SensorReadings> sensorReadings) {
		this.sensorReadings = sensorReadings;
	}
	
}
