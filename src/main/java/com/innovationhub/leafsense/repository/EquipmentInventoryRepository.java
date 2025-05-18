package com.innovationhub.leafsense.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.innovationhub.leafsense.model.EquipmentInventory;
import com.innovationhub.leafsense.model.Farm;

public interface EquipmentInventoryRepository extends JpaRepository<EquipmentInventory, Integer> {

	Page<EquipmentInventory> findByFarm(Farm farm, Pageable pageable);

}
