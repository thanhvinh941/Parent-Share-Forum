package com.se1.authservice.payload;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
	private String name;
    private String email;
    private String imageUrl;
    private Boolean emailVerified;
    private String role;
}
