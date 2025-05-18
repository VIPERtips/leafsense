package com.innovationhub.leafsense.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.innovationhub.leafsense.dto.SensorDto;
import com.innovationhub.leafsense.model.Farm;
import com.innovationhub.leafsense.model.Sensor;
import com.innovationhub.leafsense.repository.SensorRepository;

@Service
public class SensorService {
	@Autowired
	private SensorRepository sensorRepository;
	@Autowired
	private FarmService farmService;
	
	public Sensor registerSensor(SensorDto req, int farmId) {
		Farm farm = farmService.getFarmById(farmId);
		Sensor sensor = new Sensor();
		sensor.setFarm(farm);
		sensor.setInstalledAt(req.getInstalledAt());
		sensor.setLocation(req.getLocation());
		sensor.setSensorType(req.getSensorType());
		sensor.setStatus(req.getStatus());
		return sensorRepository.save(sensor);
	}
	
	public Sensor getSensorById(int sensorId) {
		Sensor sensor = sensorRepository.findById(sensorId)
				.orElseThrow(()->
				new RuntimeException("No sensor found by ID: "+sensorId));
		return sensor;
	}
	
	public Sensor updateSensor(SensorDto req, int farmId,int sensorId) {
		Farm farm = farmService.getFarmById(farmId);
		Sensor sensor = getSensorById(sensorId);
		sensor.setFarm(farm);
		sensor.setInstalledAt(req.getInstalledAt());
		sensor.setLocation(req.getLocation());
		sensor.setSensorType(req.getSensorType());
		sensor.setStatus(req.getStatus());
		return sensorRepository.save(sensor);
	}
	
	public Page<Sensor> getAllSensorsPerFarm(int page, int size, int farmId){
		Farm farm = farmService.getFarmById(farmId);
		 Sort sort = Sort.by(Sort.Direction.DESC,"inspectedAt");
		 Pageable pageable = PageRequest.of(page, size, sort);
		 return sensorRepository.findByFarm(farm,pageable);
	}
	
	public void deleteSensorBy(int sensorId) {
		Sensor sensor = sensorRepository.findById(sensorId)
				.orElseThrow(()->
				new RuntimeException("No sensor found by ID: "+sensorId));
		 sensorRepository.delete(sensor);;
	}
}
