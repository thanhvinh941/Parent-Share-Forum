package com.se1.userservice.payload;


import com.se1.userservice.model.AuthProvider;

import lombok.Data;

@Data
public class UserResponseDto {
	private Long id;
    private String name;
    private String email;
    private String imageUrl;
    private Boolean emailVerified;
    private String password;
    private AuthProvider provider;
    private String providerId;
    private String role;
    private int status;
    private Boolean isExpert;
    private String topicId;
	private Double rating;
}
