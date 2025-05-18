package com.innovationhub.leafsense.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.innovationhub.leafsense.dto.DiseaseInspectionDto;
import com.innovationhub.leafsense.model.DiseaseInspection;
import com.innovationhub.leafsense.model.Farm;
import com.innovationhub.leafsense.repository.DiseaseInspectionRepository;



@Service
public class DiseaseInspectionService {
	@Autowired
	private DiseaseInspectionRepository diseaseInspectionRepository;

	@Autowired
	private FarmService farmService;
	
	public DiseaseInspection createInspection(DiseaseInspectionDto req, int farmId) {
		Farm farm = farmService.getFarmById(farmId);
		DiseaseInspection diseaseInspection = new DiseaseInspection();
		diseaseInspection.setFarm(farm);
		diseaseInspection.setImageUrl(req.getImageUrl());
		if(req.isDiseaseDetected()) {
			diseaseInspection.setDiseaseName(req.getDiseaseName());
			diseaseInspection.setConfidenceScore(req.getConfidenceScore());
			diseaseInspection.setDiseaseDetected(true);
		} else {
			//implement the other logic here
		}
		diseaseInspection.setInspectedAt(LocalDate.now());
		return diseaseInspectionRepository.save(diseaseInspection);
	}
	
	public DiseaseInspection getInspectionById(int id) {
		DiseaseInspection diseaseInspection = diseaseInspectionRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("No inspection found for id: "+id));
		return diseaseInspectionRepository.save(diseaseInspection);
	}
	
	public Page<DiseaseInspection> getAllInspectionsPerFarm(int page, int size, int farmId){
		Farm farm = farmService.getFarmById(farmId);
		 Sort sort = Sort.by(Sort.Direction.DESC,"inspectedAt");
		 Pageable pageable = PageRequest.of(page, size, sort);
		 return diseaseInspectionRepository.findByFarm(farm,pageable);
	}
	
	public void deleteInspection(int id) {
		DiseaseInspection inspection = getInspectionById(id);
		if(inspection == null) {
			throw new RuntimeException("Inpection not found");
		}
		diseaseInspectionRepository.delete(inspection);
	}
	
}
