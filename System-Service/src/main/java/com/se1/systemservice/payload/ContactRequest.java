package com.se1.systemservice.payload;

import lombok.Data;

@Data
public class ContactRequest {
	private Long id;
	private Long userReciverId;
	private Long userSenderId;
}
