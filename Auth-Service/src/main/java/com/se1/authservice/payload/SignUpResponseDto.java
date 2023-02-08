package com.se1.authservice.payload;

import java.util.List;

import lombok.Data;

@Data
public class SignUpResponseDto {

	private List<String> message;
	private Boolean signUp;
}
