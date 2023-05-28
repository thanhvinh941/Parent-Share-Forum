package com.se1.userservice.controller.external;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.payload.request.DoRatingRequest;
import com.se1.userservice.domain.service.RatingService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/rating/external")
@RestController
@RequiredArgsConstructor
public class RatingExternalController {

	private final ObjectMapper objectMapper;
	private final RatingService ratingService;
	
	@PostMapping("/doRating")
	public ResponseEntity<?> doRating(@RequestBody DoRatingRequest request,
			@RequestHeader("user_detail") String userDetailHeader)
			throws JsonMappingException, JsonProcessingException {
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);

		try {
			Object response = ratingService.processDoRating(request, userDetail.getId());
			apiResponseEntity.setData(response);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		} catch (Exception e) {
			apiResponseEntity.setData(false);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}

		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/getUesrRating")
	public ResponseEntity<?> getUesrRating(@RequestParam("expertId") Long expertid,
			@RequestHeader("user_detail") String userDetailHeader)
			throws JsonMappingException, JsonProcessingException {
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);

		try {
			Object response = ratingService.processGetUesrRating(expertid, userDetail.getId());
			apiResponseEntity.setData(response);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		} catch (Exception e) {
			apiResponseEntity.setData(false);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}

		return ResponseEntity.ok(apiResponseEntity);
	}
}
