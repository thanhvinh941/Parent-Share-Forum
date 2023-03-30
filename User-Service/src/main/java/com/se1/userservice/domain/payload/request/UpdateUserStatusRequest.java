package com.se1.userservice.domain.payload.request;

import lombok.Data;

@Data
public class UpdateUserStatusRequest {
	private Long id;
	private int status;
}
