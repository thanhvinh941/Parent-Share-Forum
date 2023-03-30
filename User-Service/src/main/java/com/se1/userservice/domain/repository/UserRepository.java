package com.se1.userservice.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.se1.userservice.domain.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.email = ?1")
	User findByEmail(String email);

	Boolean existsByEmail(String email);

}
