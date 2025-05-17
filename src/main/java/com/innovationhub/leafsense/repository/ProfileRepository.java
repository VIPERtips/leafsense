package com.innovationhub.leafsense.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovationhub.leafsense.model.Profile;
import com.innovationhub.leafsense.model.User;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

	Optional<Profile> findByUser(User user);

	Profile getProfileByUser_UserId(int userId);

	

}
