package com.innovationhub.leafsense.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.innovationhub.leafsense.model.DiseaseInspection;
import com.innovationhub.leafsense.model.Farm;

public interface DiseaseInspectionRepository extends JpaRepository<DiseaseInspection, Integer> {

	Page<DiseaseInspection> findByFarm(Farm farm, Pageable pageable);

}
