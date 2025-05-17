package com.innovationhub.leafsense.service;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.innovationhub.leafsense.dto.ProfileDto;
import com.innovationhub.leafsense.model.Profile;
import com.innovationhub.leafsense.model.User;
import com.innovationhub.leafsense.repository.ProfileRepository;
import com.innovationhub.leafsense.repository.UserRepository;

@Service
public class ProfileService {
	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private UserRepository userRepository;

	public Profile createProfile(ProfileDto req, int id) {
		if (req.getFullname() == null || req.getFullname().isBlank()) {
			throw new RuntimeException("Full name is required");
		}

		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
		Profile profile = new Profile();
		profile.setFullname(req.getFullname());
		profile.setPhone(req.getPhone());
		profile.setCreatedAt(LocalDate.now());
		profile.setUser(user);

		return profileRepository.save(profile);

	}

	public Profile getProfileByUser(User user) {
		return profileRepository.findByUser(user)
				.orElseThrow(() -> new RuntimeException("No profile found for this user"));
	}

	

	public Map<String, Page<Profile>> getProfilesGroupedByRole(int page, int size) {
		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Profile> profiles = profileRepository.findAll(pageable);

		if (profiles.isEmpty()) {
			return Map.of();
		}

		return profiles.get().collect(Collectors.groupingBy(profile -> profile.getUser().getRole().name(), Collectors
				.collectingAndThen(Collectors.toList(), list -> new PageImpl<>(list, pageable, list.size()))));
	}

	public Page<Profile> getAllProfiles(int page, int size) {
		try {
			Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
			Pageable pageable = PageRequest.of(page, size, sort);
			return profileRepository.findAll(pageable);
		} catch (Exception e) {
			System.out.println("Error fetching profiles: " + e.getMessage());
			throw new RuntimeException("Error fetching profiles", e);
		}
	}

	public void deleteProfile(User user) {
		try {
			Profile profile = profileRepository.getProfileByUser_UserId(user.getUserId());

			System.out.println("trying to delete profile of " + profile.getProfileId());

			profileRepository.delete(profile);

			if (profileRepository.findById(profile.getProfileId()).isPresent()) {
				System.out.println("Profile still exists");
			} else {
				System.out.println("deleted bro");
			}
		} catch (Exception e) {
			System.out.println("Error deleting profile");
			throw new RuntimeException("Error deleting profile");
		}
	}

	public Profile updateProfileByUser(ProfileDto req, User user) {
		Profile profile = getProfileByUser(user);
		if (req.getFullname() != null && !req.getFullname().isBlank()) {
			profile.setFullname(req.getFullname());
		}
		if (req.getPhone() != null && !req.getPhone().isBlank()) {
			profile.setPhone(req.getPhone());
		}

		return profileRepository.save(profile);
	}

}
