package com.innovationhub.leafsense.controller;

import com.innovationhub.leafsense.dto.DiseaseInspectionDto;
import com.innovationhub.leafsense.model.DiseaseInspection;
import com.innovationhub.leafsense.response.ApiResponse;
import com.innovationhub.leafsense.service.DiseaseInspectionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inspection")
public class DiseaseInspectionController {

    @Autowired
    private DiseaseInspectionService diseaseInspectionService;

    @Operation(
        summary = "Create a crop disease inspection",
        description = "Stores the results of a crop disease analysis from the ML model for a specific farm. Requires inspection details and the farm ID."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<DiseaseInspection>> makeInspection(
            @RequestBody DiseaseInspectionDto inspectionDto,
            @RequestParam int farmId) {
        try {
            DiseaseInspection inspection = diseaseInspectionService.createInspection(inspectionDto, farmId);
            return ResponseEntity.ok(new ApiResponse<>("Inspection created successfully", true, inspection));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(
        summary = "Get a specific inspection by ID",
        description = "Retrieves the inspection record associated with the given inspection ID. Useful for detailed view."
    )
    @GetMapping("/{inspectionId}")
    public ResponseEntity<ApiResponse<DiseaseInspection>> getInspectionById(@PathVariable int inspectionId) {
        try {
            DiseaseInspection inspection = diseaseInspectionService.getInspectionById(inspectionId);
            return ResponseEntity.ok(new ApiResponse<>("Inspection retrieved successfully", true, inspection));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(
        summary = "Get all inspections for a farm",
        description = "Returns a paginated list of all disease inspections recorded for a specific farm using the farm ID."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<Page<DiseaseInspection>>> getAllInpections(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam int farmId) {
        try {
            Page<DiseaseInspection> inspection = diseaseInspectionService.getAllInspectionsPerFarm(page, size, farmId);
            return ResponseEntity.ok(new ApiResponse<>("Inspections retrieved successfully", true, inspection));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(
        summary = "Delete an inspection by ID",
        description = "Deletes a disease inspection record from the system based on its unique ID."
    )
    @DeleteMapping("/{inspectionId}")
    public ResponseEntity<ApiResponse<DiseaseInspection>> deleteInspection(@PathVariable int inspectionId) {
        try {
            diseaseInspectionService.deleteInspection(inspectionId);
            return ResponseEntity.ok(new ApiResponse<>("Inspection deleted successfully", true, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }
}
