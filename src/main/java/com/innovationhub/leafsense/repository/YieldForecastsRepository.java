package com.innovationhub.leafsense.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.innovationhub.leafsense.model.Farm;
import com.innovationhub.leafsense.model.YieldForecasts;

public interface YieldForecastsRepository extends JpaRepository<YieldForecasts, Integer> {

	Page<YieldForecasts> findByFarm(Farm farm, Pageable pageable);

}
