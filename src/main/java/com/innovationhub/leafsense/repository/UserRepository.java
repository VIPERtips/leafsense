package com.innovationhub.leafsense.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innovationhub.leafsense.enums.Role;
import com.innovationhub.leafsense.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String username);

	boolean existsByEmail(String email);

	User findByResetToken(String token);

	List<User> findByRole(Role role);

}
