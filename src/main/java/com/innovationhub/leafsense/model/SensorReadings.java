package com.innovationhub.leafsense.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SensorReadings {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int readingId;
	@ManyToOne
	@JoinColumn(name = "sensor_id",referencedColumnName = "sensorId",nullable = false)
	@JsonIgnore
	private Sensor sensor;
	@Column(columnDefinition = "DECIMAL")
	private String value;
	private String unit;
	private LocalDateTime timestamp = LocalDateTime.now();
	
	public SensorReadings() {
		// TODO Auto-generated constructor stub
	}

	public int getReadingId() {
		return readingId;
	}

	public void setReadingId(int readingId) {
		this.readingId = readingId;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	
	
}
