package com.innovationhub.leafsense.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.innovationhub.leafsense.model.Farm;
import com.innovationhub.leafsense.model.Profile;

public interface FarmRepository extends JpaRepository<Farm, Integer> {

	Page<Farm> findByProfile(Profile profile, Pageable pageable);

	

}
