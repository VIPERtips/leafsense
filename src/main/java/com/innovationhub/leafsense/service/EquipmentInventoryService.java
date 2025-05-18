package com.innovationhub.leafsense.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.innovationhub.leafsense.dto.EquipmentInventoryDto;
import com.innovationhub.leafsense.model.EquipmentInventory;
import com.innovationhub.leafsense.model.Farm;
import com.innovationhub.leafsense.repository.EquipmentInventoryRepository;

@Service
public class EquipmentInventoryService {
	
	@Autowired
	private EquipmentInventoryRepository equipmentInventoryRepository;
	
	@Autowired
	private FarmService farmService;
	
	public EquipmentInventory createEquipmentInventory(EquipmentInventoryDto req, int farmId) {
		Farm farm = farmService.getFarmById(farmId);
		EquipmentInventory inventory = new EquipmentInventory();
		inventory.setFarm(farm);
		inventory.setName(req.getName());
		inventory.setType(req.getType());
		inventory.setStatus(req.getStatus());
		inventory.setLastMaintananceDate(req.getLastMaintananceDate());
		return equipmentInventoryRepository.save(inventory);
	}
	
	public EquipmentInventory getEquipmentInventoryById(int equipmentId) {
		return equipmentInventoryRepository.findById(equipmentId)
				.orElseThrow(()-> new RuntimeException("Equipment not found by ID "+equipmentId));
	}
	
	public Page<EquipmentInventory> getAllInventoryPerFarm(int page, int size, int farmId){
		Farm farm = farmService.getFarmById(farmId);
		Sort sort = Sort.by(Sort.Direction.DESC,"lastMaintananceDate");
		Pageable pageable = PageRequest.of(page, size,sort);
		return equipmentInventoryRepository.findByFarm(farm,pageable);
	}
	
	public EquipmentInventory updateEquipmentInventory(EquipmentInventoryDto req, int farmId,int equipmentId) {
		Farm farm = farmService.getFarmById(farmId);
		EquipmentInventory inventory = getEquipmentInventoryById(equipmentId);
		inventory.setFarm(farm);
		inventory.setName(req.getName());
		inventory.setType(req.getType());
		inventory.setStatus(req.getStatus());
		inventory.setLastMaintananceDate(req.getLastMaintananceDate());
		return equipmentInventoryRepository.save(inventory);
	}
	
	public void deleteEquipmentInventoryById(int equipmentId) {
		EquipmentInventory inventory = getEquipmentInventoryById(equipmentId);
		 equipmentInventoryRepository.delete(inventory);
	}
	
}
