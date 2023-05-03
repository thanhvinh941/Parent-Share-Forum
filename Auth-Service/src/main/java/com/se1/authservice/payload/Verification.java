package com.se1.authservice.payload;

import java.util.Date;

import lombok.Data;

@Data
public class Verification {
	private Long id;
	private Long userId;
	private String token;
	private Date expirationTime;
	private Byte validFlg;
}
