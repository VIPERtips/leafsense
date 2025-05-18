package com.innovationhub.leafsense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innovationhub.leafsense.dto.DiseaseInspectionDto;
import com.innovationhub.leafsense.model.DiseaseInspection;
import com.innovationhub.leafsense.response.ApiResponse;
import com.innovationhub.leafsense.service.DiseaseInspectionService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/inspection")
public class DiseaseInspectionController {
	@Autowired
	private DiseaseInspectionService diseaseInspectionService;
	
	@Operation(summary = "create an inspection", description = "data should come from our model")
	@PostMapping
	public ResponseEntity<ApiResponse<DiseaseInspection>> makeInspection(@RequestBody DiseaseInspectionDto inspectionDto, @RequestParam int farmId){
		try {
			DiseaseInspection inspection = diseaseInspectionService.createInspection(inspectionDto, farmId);
			return ResponseEntity.ok(new ApiResponse<>("Inspection  created successfully", true, inspection));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(e.getMessage(), false, null));
		}
	}
	
	@Operation(summary = "get an inspection", description = " get by id")
	@GetMapping("/{inspectionId}")
	public ResponseEntity<ApiResponse<DiseaseInspection>> getInspectionById(@PathVariable int inspectionId){
		try {
			DiseaseInspection inspection = diseaseInspectionService.getInspectionById(inspectionId);
			return ResponseEntity.ok(new ApiResponse<>("Inspection retrieved successfully", true, inspection));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage(), false, null));
		}
	}
	@Operation(summary = "get all inspections", description = " get per farm")
	@GetMapping
	public ResponseEntity<ApiResponse<Page<DiseaseInspection>>> getAllInpections(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,@RequestParam int farmId){
		try {
			Page<DiseaseInspection> inspection = diseaseInspectionService.getAllInspectionsPerFarm(page, size, farmId);
			return ResponseEntity.ok(new ApiResponse<>("Inspection(s) retrieved successfully", true, inspection));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage(), false, null));
		}
	}
	@Operation(summary = "delete an inspection", description = " delete by id")
	@DeleteMapping("/{farmId}")
	public ResponseEntity<ApiResponse<DiseaseInspection>> deleteInspection(@PathVariable int farmId){
		try {
			diseaseInspectionService.deleteInspection(farmId);
			return ResponseEntity.ok(new ApiResponse<>("Inspection deleted successfully", true, null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(e.getMessage(), false, null));
		}
	}
}
