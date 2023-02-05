package com.se1.userservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.se1.userservice.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	Boolean existsByEmail(String email);

}
