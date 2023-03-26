package com.se1.userservice.domain.payload;

import lombok.Data;

@Data
public class UserContactDto {

	private long id;
	private String name;
	private String imageUrl;
	private int userStatus;
}
