package com.innovationhub.leafsense.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.innovationhub.leafsense.dto.SensorDto;
import com.innovationhub.leafsense.dto.SensorReadingDto;
import com.innovationhub.leafsense.model.Farm;
import com.innovationhub.leafsense.model.Sensor;
import com.innovationhub.leafsense.model.SensorReadings;
import com.innovationhub.leafsense.repository.SensorReadingRepository;

@Service
public class SensorReadingsService {
	@Autowired
	private SensorReadingRepository sensorReadingRepository;
	@Autowired
	private SensorService sensorService;
	
	public SensorReadings createReading(SensorReadingDto req, int sensorId) {
		Sensor sensor = sensorService.getSensorById(sensorId);
		SensorReadings readings = new SensorReadings();
		readings.setSensor(sensor);
		readings.setValue(req.getValue());
		readings.setUnit(req.getUnit());
		
		return sensorReadingRepository.save(readings);
	}
	
	public SensorReadings getSensorReadingsById(int readingId) {
		SensorReadings readings = sensorReadingRepository.findById(readingId)
				.orElseThrow(()->
				new RuntimeException("No Sensor readings found by ID: "+readingId));
		return readings;
	}
	

	
	public Page<SensorReadings> getAllSensorsReadingsPerSensor(int page, int size, int sensorId){
		Sensor sensor = sensorService.getSensorById(sensorId);
		 Sort sort = Sort.by(Sort.Direction.DESC,"timestamp");
		 Pageable pageable = PageRequest.of(page, size, sort);
		 return sensorReadingRepository.findBySensor(sensor,pageable);
	}
	
	public void deleteSensorReadingBy(int readingId) {
		SensorReadings reading = getSensorReadingsById(readingId);
		 sensorReadingRepository.delete(reading);;
	}
}
