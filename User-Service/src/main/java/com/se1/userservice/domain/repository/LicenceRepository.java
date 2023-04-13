package com.se1.userservice.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.se1.userservice.domain.model.Licence;

@Repository
public interface LicenceRepository extends CrudRepository<Licence, Long> {

}
