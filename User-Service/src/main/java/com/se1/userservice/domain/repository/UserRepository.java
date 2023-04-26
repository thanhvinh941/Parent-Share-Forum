package com.se1.userservice.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.se1.userservice.domain.model.User;
import com.se1.userservice.domain.model.UserRole;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.email = ?1")
	User findByEmail(String email);

	Boolean existsByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.id = ?1 AND u.role = 'expert'")
	User findExpertById(Long expertid);

	List<User> findAllByRole(UserRole expert);

	@Query("SELECT u FROM User u WHERE u.name like %:name% OR u.email like %:name% AND u.role != 'admin' AND u.id != :userId")
	List<User> findByName(@Param("name") String name,@Param("userId") Long userId);

}
