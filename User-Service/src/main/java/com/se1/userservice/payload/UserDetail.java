package com.se1.userservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {
	
	private Long id;
	private String email;
	private String name;
	private String imageUrl;
	private String role;
	private boolean isExpert;
	private Double rating;
	private Byte status;
}
