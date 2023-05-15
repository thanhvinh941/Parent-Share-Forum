package com.se1.postservice.domain.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class SubscribeDto {
	private Long id;
	private SubscribeUser userExpertId;
	private Date createAt;
	
	@Data
	public static class SubscribeUser{
		private Long id;
		private String email;
		private String name;
		private String imageUrl;
		private boolean isExpert;
		private Double rating;
		private Byte status;
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
		private Date lastTime = new Date();
	}
}
