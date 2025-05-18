package com.innovationhub.leafsense.dto;

import java.time.LocalDate;

public class YieldForecastDto {
	private String predictedYield;
	private String confidenceLevel;
	private LocalDate forecastDate;
	private String modelVersion;
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
