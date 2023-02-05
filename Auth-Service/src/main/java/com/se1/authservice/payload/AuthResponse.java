package com.se1.authservice.payload;

import lombok.Data;

@Data
public class AuthResponse {
	private String accessToken;
    //TODO: remove filed tokenType, add filed: expiryDate
    private String tokenType = "Bearer";
    public AuthResponse(String token) {
    	this.accessToken = token;
    }
}
