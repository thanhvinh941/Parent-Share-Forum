package com.se1.userservice.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.se1.userservice.domain.model.Rating;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long>{

	List<Rating> findByUserRatedId(Long id);

	Rating findByUserRatedIdAndUserRatingId(Long expertid, Long id);

}
