package com.innovationhub.leafsense.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.innovationhub.leafsense.model.Sensor;
import com.innovationhub.leafsense.model.SensorReadings;

public interface SensorReadingRepository extends JpaRepository<SensorReadings, Integer> {

	Page<SensorReadings> findBySensor(Sensor sensor, Pageable pageable);

}
