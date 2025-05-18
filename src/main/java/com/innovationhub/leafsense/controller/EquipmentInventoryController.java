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

import com.innovationhub.leafsense.dto.EquipmentInventoryDto;
import com.innovationhub.leafsense.model.EquipmentInventory;
import com.innovationhub.leafsense.response.ApiResponse;
import com.innovationhub.leafsense.service.EquipmentInventoryService;

@RestController
@RequestMapping("/api/equipment-inventory")
public class EquipmentInventoryController {
	
	@Autowired
	private EquipmentInventoryService equipmentInventoryService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<EquipmentInventory>> registerEquipmentInventory(@RequestBody EquipmentInventoryDto equipmentInventoryDto, int farmId){
		try {
			EquipmentInventory inventory = equipmentInventoryService.createEquipmentInventory(equipmentInventoryDto, farmId);
			return ResponseEntity.ok(new ApiResponse<>("Equipment registered successfully",true, inventory));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((new ApiResponse<>(e.getMessage(), false, null)));
		}
	}
	
	@GetMapping("/{equipmentId}")
	public ResponseEntity<ApiResponse<EquipmentInventory>> getEquipementById( @PathVariable int equipmentId){
		try {
			EquipmentInventory inventory = equipmentInventoryService.getEquipmentInventoryById(equipmentId);
			return ResponseEntity.ok(new ApiResponse<>("Equipment retrieved successfully",true, inventory));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body((new ApiResponse<>(e.getMessage(), false, null)));
		}
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<Page<EquipmentInventory>>> getAllEquipmentPerFarm(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,@RequestParam int farmId){
		try {
			Page<EquipmentInventory> inventory = equipmentInventoryService.getAllInventoryPerFarm(page, size, farmId);
			return ResponseEntity.ok(new ApiResponse<>("Equipment(s) retrieved successfully",true, inventory));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body((new ApiResponse<>(e.getMessage(), false, null)));
		}
	}
	
	@PutMapping("/{equipmentId}")
	public ResponseEntity<ApiResponse<EquipmentInventory>> updateEquipmentInventory(@RequestBody EquipmentInventoryDto equipmentInventoryDto, int farmId,int equipmentId){
		try {
			EquipmentInventory inventory = equipmentInventoryService.updateEquipmentInventory(equipmentInventoryDto, farmId, equipmentId);
			return ResponseEntity.ok(new ApiResponse<>("Equipment updated successfully",true, inventory));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((new ApiResponse<>(e.getMessage(), false, null)));
		}
	}
	
	@DeleteMapping("/{equipmentId}")
	public ResponseEntity<ApiResponse<EquipmentInventory>> deleteEquipemnt( @PathVariable int equipmentId){
		try {
			equipmentInventoryService.deleteEquipmentInventoryById(equipmentId);
			return ResponseEntity.ok(new ApiResponse<>("Equipment deleted successfully",true, null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((new ApiResponse<>(e.getMessage(), false, null)));
		}
	}
	
	
}
