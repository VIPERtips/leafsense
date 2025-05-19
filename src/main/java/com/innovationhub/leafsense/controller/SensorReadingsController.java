package com.innovationhub.leafsense.controller;

import com.innovationhub.leafsense.dto.SensorReadingDto;
import com.innovationhub.leafsense.model.SensorReadings;
import com.innovationhub.leafsense.response.ApiResponse;
import com.innovationhub.leafsense.service.SensorReadingsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensor-readings")
public class SensorReadingsController {

    @Autowired
    private SensorReadingsService sensorReadingsService;

    @Operation(summary = "Create sensor reading", description = "Adds a reading to a sensor.")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<SensorReadings>> createReading(@RequestBody SensorReadingDto req, @RequestParam int sensorId) {
        try {
            SensorReadings readings = sensorReadingsService.createReading(req, sensorId);
            return ResponseEntity.ok(new ApiResponse<>("Reading created successfully", true, readings));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(summary = "Get sensor reading by ID", description = "Fetches a sensor reading by its ID.")
    @GetMapping("/{readingId}")
    public ResponseEntity<ApiResponse<SensorReadings>> getSensorReadingById(@PathVariable int readingId) {
        try {
            SensorReadings reading = sensorReadingsService.getSensorReadingsById(readingId);
            return ResponseEntity.ok(new ApiResponse<>("Reading found", true, reading));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(summary = "Get all sensor readings", description = "Fetches paginated list of all readings for a specific sensor.")
    @GetMapping("/sensor")
    public ResponseEntity<ApiResponse<Page<SensorReadings>>> getAllReadingsPerSensor(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam int sensorId) {
        try {
            Page<SensorReadings> readings = sensorReadingsService.getAllSensorsReadingsPerSensor(page, size, sensorId);
            return ResponseEntity.ok(new ApiResponse<>("Readings fetched successfully", true, readings));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(summary = "Delete sensor reading", description = "Deletes a specific reading by ID.")
    @DeleteMapping("/{readingId}")
    public ResponseEntity<ApiResponse<String>> deleteSensorReading(@PathVariable int readingId) {
        try {
            sensorReadingsService.deleteSensorReadingBy(readingId);
            return ResponseEntity.ok(new ApiResponse<>("Sensor reading deleted successfully", true, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }
}
