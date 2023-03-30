package com.se1.postservice.domain.payload;

import java.util.List;

import lombok.Data;

@Data
public class RegistResponse {
	private List<String> message;
	private Boolean isRegist;
}
