package com.se1.authservice.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
	private String accessToken;
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)	
	private Date expiryDate;
	
	private String email;
	
}
