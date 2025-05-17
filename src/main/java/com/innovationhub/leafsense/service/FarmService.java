package com.innovationhub.leafsense.service;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.innovationhub.leafsense.dto.FarmDto;
import com.innovationhub.leafsense.model.Farm;
import com.innovationhub.leafsense.model.Profile;
import com.innovationhub.leafsense.repository.FarmRepository;
import com.innovationhub.leafsense.repository.ProfileRepository;

@Service
public class FarmService {
	@Autowired
	private FarmRepository farmRepository;

	@Autowired
	private ProfileRepository profileRepository;

	public Farm registerFarm(FarmDto req, int profileId) {
		if (req.getName().isEmpty()) {
			throw new RuntimeException("Farm name is required");
		}

		if (req.getLocation().isEmpty()) {
			throw new RuntimeException("Farm location is required");
		}

		Profile profile = profileRepository.findById(profileId)
				.orElseThrow(() -> new RuntimeException("No profile found by id: " + profileId));
		Farm farm = new Farm();
		farm.setName(req.getName());
		farm.setLocation(req.getLocation());
		farm.setSize(req.getSize());
		farm.setCropType(req.getCropType());
		farm.setCreatedAt(LocalDateTime.now());
		farm.setProfile(profile);
		return farmRepository.save(farm);
	}

	public Farm updateFarm(FarmDto req, int farmId, int profileId) {
		Farm farm = getFarmById(farmId);
		
		Profile profile = profileRepository.findById(profileId)
				.orElseThrow(() -> new RuntimeException("No profile found by Id: " + profileId));
		if (farm.getProfile().getProfileId() != profileId) {
			throw new RuntimeException("You are not authorized to update this farm.");
		}
		
		farm.setName(req.getName());
		farm.setLocation(req.getLocation());
		farm.setSize(req.getSize());
		farm.setCropType(req.getCropType());
		farm.setUpdatedAt(LocalDateTime.now());
		farm.setProfile(profile);
		return farmRepository.save(farm);
	}
	public Farm getFarmById(int farmId) {
		Farm farm = farmRepository.findById(farmId)
				.orElseThrow(() -> new RuntimeException("No Farm found by Id: " + farmId));
		return farm;
	}
	
	public Page<Farm> getUserFarms(int profileId, int page, int size){
		Profile profile = profileRepository.findById(profileId)
				.orElseThrow(() -> new RuntimeException("No profile found by Id: " + profileId));
		Sort sort = Sort.by(Sort.Direction.DESC,"createdAt");
		Pageable pageable = PageRequest.of(page, size,sort);
		
		return farmRepository.findByProfile(profile,pageable);
	}
	
	public void deleteFarm(int farmId) {
		Farm farm = getFarmById(farmId);
		farmRepository.delete(farm);
	}
}
