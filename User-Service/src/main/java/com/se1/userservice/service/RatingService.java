package com.se1.userservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se1.userservice.model.Rating;
import com.se1.userservice.repository.RatingRepository;

@Service
public class RatingService {

	@Autowired
	private RatingRepository ratingRepository;
	
	double getRatingByUserId(long userId) {
		List<Rating> expertRatings = ratingRepository.findByUserRatedId(userId);
		if(expertRatings != null && expertRatings.size() > 0) {
			List<Integer> ratings = expertRatings.stream().map(ex -> ex.getRating()).collect(Collectors.toList());
			return ratings.stream().mapToDouble(d -> d).average().getAsDouble();
		} return 0;
	}
}
