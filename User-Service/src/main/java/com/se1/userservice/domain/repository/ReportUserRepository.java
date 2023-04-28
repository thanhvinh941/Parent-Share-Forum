package com.se1.userservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.se1.userservice.domain.model.ReportUser;

@Repository
public interface ReportUserRepository extends JpaRepository<ReportUser, Long>{
	
}
