package com.se1.userservice.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.se1.userservice.domain.model.Verification;

@Repository
public interface VerifycationRepository extends CrudRepository<Verification, Long>{

}
