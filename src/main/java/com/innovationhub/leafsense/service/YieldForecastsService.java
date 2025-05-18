package com.innovationhub.leafsense.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.innovationhub.leafsense.dto.YieldForecastDto;
import com.innovationhub.leafsense.model.Farm;
import com.innovationhub.leafsense.model.YieldForecasts;
import com.innovationhub.leafsense.repository.YieldForecastsRepository;

@Service
public class YieldForecastsService {

	@Autowired
	private YieldForecastsRepository yieldForecastsRepository;
	
	@Autowired
	private FarmService farmService;
	
	public YieldForecasts generateForecasts(YieldForecastDto req, int farmId) {
		Farm farm = farmService.getFarmById(farmId);
		//data should come from our model
		YieldForecasts forecasts = new YieldForecasts();
		forecasts.setPredictedYield(req.getPredictedYield());
		forecasts.setConfidenceLevel(req.getConfidenceLevel());
		forecasts.setFarm(farm);
		forecasts.setModelVersion(req.getModelVersion());
		forecasts.setForecastDate(req.getForecastDate());
		return yieldForecastsRepository.save(forecasts);
	}
	
	public YieldForecasts updateForecast(YieldForecastDto req, int farmId, int yieldForecatId) {
		Farm farm = farmService.getFarmById(farmId);
		//data should come from our model
		YieldForecasts forecasts = getForecastsById(yieldForecatId);
		forecasts.setPredictedYield(req.getPredictedYield());
		forecasts.setConfidenceLevel(req.getConfidenceLevel());
		forecasts.setFarm(farm);
		forecasts.setModelVersion(req.getModelVersion());
		forecasts.setForecastDate(req.getForecastDate());
		return yieldForecastsRepository.save(forecasts);
	}
	
	public YieldForecasts getForecastsById(int yieldForecastId) {
		YieldForecasts yieldForecasts = yieldForecastsRepository.findById(yieldForecastId)
				.orElseThrow(()-> new RuntimeException("No forecast found by id: "+yieldForecastId));
		return yieldForecasts;
	}
	
	public Page<YieldForecasts> getAllYieldForecasts(int page, int size,int farmId){
		Farm farm = farmService.getFarmById(farmId);
		 Sort sort = Sort.by(Sort.Direction.DESC,"forecastDate");
		 Pageable pageable = PageRequest.of(page, size, sort);
		 
		return yieldForecastsRepository.findByFarm(farm,pageable);
	}
	
	public  void deleteYieldForecastById(int id) {
		 yieldForecastsRepository.deleteById(id);
	}
}
