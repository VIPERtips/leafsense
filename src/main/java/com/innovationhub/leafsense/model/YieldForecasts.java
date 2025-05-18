package com.innovationhub.leafsense.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class YieldForecasts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int forecastId;

	public YieldForecasts() {
		// TODO Auto-generated constructor stub
	}

	@ManyToOne
	@JoinColumn(name = "farm_id", referencedColumnName = "farmId", nullable = false)
	private Farm farm;
	private String predictedYield;
	private String confidenceLevel;
	private LocalDate forecastDate;
	private String modelVersion;

	public int getForecastId() {
		return forecastId;
	}

	public void setForecastId(int forecastId) {
		this.forecastId = forecastId;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public String getPredictedYield() {
		return predictedYield;
	}

	public void setPredictedYield(String predictedYield) {
		this.predictedYield = predictedYield;
	}

	public String getConfidenceLevel() {
		return confidenceLevel;
	}

	public void setConfidenceLevel(String confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}

	public LocalDate getForecastDate() {
		return forecastDate;
	}

	public void setForecastDate(LocalDate forecastDate) {
		this.forecastDate = forecastDate;
	}

	public String getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(String modelVersion) {
		this.modelVersion = modelVersion;
	}

}
