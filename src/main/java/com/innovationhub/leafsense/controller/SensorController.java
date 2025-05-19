package com.innovationhub.leafsense.controller;

import com.innovationhub.leafsense.dto.SensorDto;
import com.innovationhub.leafsense.model.Sensor;
import com.innovationhub.leafsense.response.ApiResponse;
import com.innovationhub.leafsense.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @Operation(summary = "Register sensor", description = "Registers a new sensor to a specific farm.")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Sensor>> registerSensor(@RequestBody SensorDto req, @RequestParam int farmId) {
        try {
            Sensor sensor = sensorService.registerSensor(req, farmId);
            return ResponseEntity.ok(new ApiResponse<>("Sensor registered successfully", true, sensor));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(summary = "Update sensor", description = "Updates an existing sensor by sensor ID and farm ID.")
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Sensor>> updateSensor(@RequestBody SensorDto req, @RequestParam int farmId, @RequestParam int sensorId) {
        try {
            Sensor sensor = sensorService.updateSensor(req, farmId, sensorId);
            return ResponseEntity.ok(new ApiResponse<>("Sensor updated successfully", true, sensor));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(summary = "Get sensor by ID", description = "Fetches a sensor by its ID.")
    @GetMapping("/{sensorId}")
    public ResponseEntity<ApiResponse<Sensor>> getSensorById(@PathVariable int sensorId) {
        try {
            Sensor sensor = sensorService.getSensorById(sensorId);
            return ResponseEntity.ok(new ApiResponse<>("Sensor found", true, sensor));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(summary = "Get all sensors by farm", description = "Fetches paginated list of all sensors under a farm.")
    @GetMapping("/farm")
    public ResponseEntity<ApiResponse<Page<Sensor>>> getAllSensorsPerFarm(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam int farmId) {
        try {
            Page<Sensor> sensors = sensorService.getAllSensorsPerFarm(page, size, farmId);
            return ResponseEntity.ok(new ApiResponse<>("Sensors fetched successfully", true, sensors));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(summary = "Delete sensor", description = "Deletes a sensor by its ID.")
    @DeleteMapping("/{sensorId}")
    public ResponseEntity<ApiResponse<String>> deleteSensor(@PathVariable int sensorId) {
        try {
            sensorService.deleteSensorBy(sensorId);
            return ResponseEntity.ok(new ApiResponse<>("Sensor deleted successfully", true, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }
}
