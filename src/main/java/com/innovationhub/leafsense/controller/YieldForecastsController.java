package com.innovationhub.leafsense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innovationhub.leafsense.dto.YieldForecastDto;
import com.innovationhub.leafsense.model.Farm;
import com.innovationhub.leafsense.model.Profile;
import com.innovationhub.leafsense.model.User;
import com.innovationhub.leafsense.model.YieldForecasts;
import com.innovationhub.leafsense.response.ApiResponse;
import com.innovationhub.leafsense.service.YieldForecastsService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/yieldforecast")
public class YieldForecastsController {

	@Autowired
	private YieldForecastsService yieldForecastsService;

	
	 @Operation(summary = "generates a forecast", description = "data should come from model")
	@PostMapping
	public ResponseEntity<ApiResponse<YieldForecasts>> generateForeCast(@RequestBody YieldForecastDto forecast, @RequestParam int farmId){
		try {
			YieldForecasts forecasts = yieldForecastsService.generateForecasts(forecast, farmId);
			return ResponseEntity.ok(new ApiResponse<>("Forecast generated successfully",true,forecasts));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(e.getMessage(), false, null));
		}
		
	}
	 @Operation(summary = "gets all forecast", description = " data on db")
	@GetMapping
	public ResponseEntity<ApiResponse<Page<YieldForecasts>>> getAllYieldForecats(@RequestParam int farmId,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
		try {
			Page<YieldForecasts> forecasts = yieldForecastsService.getAllYieldForecasts(page, size, farmId);
					return ResponseEntity.ok(new ApiResponse<>("Forecasts retrieved successfully",true,forecasts));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(e.getMessage(), false, null));
		}
		
	}
	 @Operation(summary = "gets a forecast", description = " data on db")
	@GetMapping("/{forecastId}")
	public ResponseEntity<ApiResponse<YieldForecasts>> getForeCastById(@PathVariable int forecastId){
		try {
			YieldForecasts forecasts = yieldForecastsService.getForecastsById(forecastId);
					return ResponseEntity.ok(new ApiResponse<>("Forecast retrieved successfully",true,forecasts));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage(), false, null));
		}
		
	}
	 @Operation(summary = "update a forecast", description = " data on db")
	@PutMapping("/{forecastId}")
	public ResponseEntity<ApiResponse<YieldForecasts>> updateForecast( @RequestBody YieldForecastDto yieldForecastDto,@PathVariable int forecastId,@RequestParam int farmId){
		try {
			YieldForecasts forecasts = yieldForecastsService.updateForecast(yieldForecastDto, forecastId, farmId);
					return ResponseEntity.ok(new ApiResponse<>("Forecast updated successfully",true,forecasts));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(e.getMessage(), false, null));
		}
		
	}
	 @Operation(summary = "delete a forecast", description = " data on db")
	@DeleteMapping("/{forecastId}")
	public ResponseEntity<ApiResponse<YieldForecasts>> deleteForecast(@PathVariable int forecastId){
		try {
			yieldForecastsService.deleteYieldForecastById(forecastId);
					return ResponseEntity.ok(new ApiResponse<>("Forecast deleted successfully",true,null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(e.getMessage(), false, null));
		}
		
	}


}
