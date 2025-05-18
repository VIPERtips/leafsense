package com.innovationhub.leafsense.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.innovationhub.leafsense.model.Farm;
import com.innovationhub.leafsense.model.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {

	Page<Sensor> findByFarm(Farm farm, Pageable pageable);

}
