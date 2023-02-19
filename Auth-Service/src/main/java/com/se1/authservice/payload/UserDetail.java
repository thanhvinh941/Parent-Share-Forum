package com.se1.authservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetail {
	
	private Long id;
	private String name;
	private String imageUrl;
	private String role;
}
