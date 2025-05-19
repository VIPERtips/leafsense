package com.innovationhub.leafsense.controller;

import com.innovationhub.leafsense.dto.YieldForecastDto;
import com.innovationhub.leafsense.model.YieldForecasts;
import com.innovationhub.leafsense.response.ApiResponse;
import com.innovationhub.leafsense.service.YieldForecastsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/yieldforecast")
public class YieldForecastsController {

    @Autowired private YieldForecastsService yieldForecastsService;

    @Operation(
        summary = "Generate a yield forecast",
        description = "Generates and stores a yield forecast result using input from the prediction model."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<YieldForecasts>> generateForeCast(
            @RequestBody YieldForecastDto forecast,
            @RequestParam int farmId) {
        try {
            YieldForecasts forecasts = yieldForecastsService.generateForecasts(forecast, farmId);
            return ResponseEntity.ok(new ApiResponse<>("Forecast generated successfully", true, forecasts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(
        summary = "Get all forecasts for a farm",
        description = "Returns a paginated list of all yield forecasts saved for a specific farm."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<Page<YieldForecasts>>> getAllYieldForecats(
            @RequestParam int farmId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<YieldForecasts> forecasts = yieldForecastsService.getAllYieldForecasts(page, size, farmId);
            return ResponseEntity.ok(new ApiResponse<>("Forecasts retrieved successfully", true, forecasts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(
        summary = "Get a forecast by ID",
        description = "Fetches the details of a specific yield forecast based on its ID."
    )
    @GetMapping("/{forecastId}")
    public ResponseEntity<ApiResponse<YieldForecasts>> getForeCastById(@PathVariable int forecastId) {
        try {
            YieldForecasts forecasts = yieldForecastsService.getForecastsById(forecastId);
            return ResponseEntity.ok(new ApiResponse<>("Forecast retrieved successfully", true, forecasts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(
        summary = "Update an existing forecast",
        description = "Updates an existing forecast record based on ID and associated farm."
    )
    @PutMapping("/{forecastId}")
    public ResponseEntity<ApiResponse<YieldForecasts>> updateForecast(
            @RequestBody YieldForecastDto yieldForecastDto,
            @PathVariable int forecastId,
            @RequestParam int farmId) {
        try {
            YieldForecasts forecasts = yieldForecastsService.updateForecast(yieldForecastDto, forecastId, farmId);
            return ResponseEntity.ok(new ApiResponse<>("Forecast updated successfully", true, forecasts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }

    @Operation(
        summary = "Delete a yield forecast",
        description = "Deletes a forecast from the system by its unique ID."
    )
    @DeleteMapping("/{forecastId}")
    public ResponseEntity<ApiResponse<YieldForecasts>> deleteForecast(@PathVariable int forecastId) {
        try {
            yieldForecastsService.deleteYieldForecastById(forecastId);
            return ResponseEntity.ok(new ApiResponse<>("Forecast deleted successfully", true, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage(), false, null));
        }
    }
}
