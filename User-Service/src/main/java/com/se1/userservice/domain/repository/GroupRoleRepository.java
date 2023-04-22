package com.se1.userservice.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.se1.userservice.domain.model.GroupRole;

@Repository
public interface GroupRoleRepository extends JpaRepository<GroupRole, Long>{

	List<GroupRole> findByName(String name);

	@Query("SELECT g FROM GroupRole g WHERE g.delFlg = 0")
	List<GroupRole> findAll();
	
}
