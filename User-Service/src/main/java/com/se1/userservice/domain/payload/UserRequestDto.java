package com.se1.userservice.domain.payload;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserRequestDto {

	private Long id;
	
    private String name;

    private String email;

    private String imageUrl;

    private String birthday;
    
    private Boolean emailVerified = false;

    private String password;

    private String provider;

    private String providerId;
    
    private String role;

}
