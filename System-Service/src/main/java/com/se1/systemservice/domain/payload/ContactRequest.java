package com.se1.systemservice.domain.payload;

import lombok.Data;

@Data
public class ContactRequest {
	private Long id;
	private Long userReciverId;
	private Long userSenderId;
}
