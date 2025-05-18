package com.innovationhub.leafsense.dto;

public class DiseaseInspectionDto {
	private String imageUrl;
	private boolean diseaseDetected;
	private String diseaseName;
	private String confidenceScore;
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public boolean isDiseaseDetected() {
		return diseaseDetected;
	}
	public void setDiseaseDetected(boolean diseaseDetected) {
		this.diseaseDetected = diseaseDetected;
	}
	public String getDiseaseName() {
		return diseaseName;
	}
	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}
	public String getConfidenceScore() {
		return confidenceScore;
	}
	public void setConfidenceScore(String confidenceScore) {
		this.confidenceScore = confidenceScore;
	}
	
	
}
