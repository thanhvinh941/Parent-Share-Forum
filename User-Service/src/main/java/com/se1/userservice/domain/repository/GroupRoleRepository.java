package com.se1.userservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.se1.userservice.domain.model.GroupRole;

@Repository
public interface GroupRoleRepository extends JpaRepository<GroupRole, Long>{

}
