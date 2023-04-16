package com.se1.notifyservice.domain.payload;

import lombok.Data;

@Data
public class UserDetail {
	private Long id;
	private String email;
	private String name;
	private String imageUrl;
}
