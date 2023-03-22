package com.se1.userservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.se1.userservice.model.Rating;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long>{

	List<Rating> findByUserRatedId(Long id);

}
