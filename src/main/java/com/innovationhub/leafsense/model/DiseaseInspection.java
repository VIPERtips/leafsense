package com.innovationhub.leafsense.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class DiseaseInspection {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int inspectionId;
	@ManyToOne
	@JoinColumn(name = "farm_id", referencedColumnName = "farmId", nullable = false)
	private Farm farm;
	private String imageUrl;
	private boolean diseaseDetected;
	private String diseaseName;
	private String confidenceScore;
	private LocalDate inspectedAt = LocalDate.now();
	
	public DiseaseInspection() {
		// TODO Auto-generated constructor stub
	}

	public int getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(int inspectionId) {
		this.inspectionId = inspectionId;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

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

	public LocalDate getInspectedAt() {
		return inspectedAt;
	}

	public void setInspectedAt(LocalDate inspectedAt) {
		this.inspectedAt = inspectedAt;
	}
	
}
