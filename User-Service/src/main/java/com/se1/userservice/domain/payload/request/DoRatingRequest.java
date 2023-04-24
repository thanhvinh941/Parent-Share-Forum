package com.se1.userservice.domain.payload.request;

import lombok.Data;

@Data
public class DoRatingRequest {
	private Long expertId;
	private Double rate;
}
