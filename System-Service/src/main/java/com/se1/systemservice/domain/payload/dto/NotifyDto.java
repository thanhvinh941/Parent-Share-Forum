package com.se1.systemservice.domain.payload.dto;

import java.util.Date;
import lombok.Data;

@Data
public class NotifyDto {
	private Long id;
	private User user;
	private String param;
	private String type;
	private String value;
	private int status;
	private Date createAt;
	private Byte delFlg;
	
	@Data
	public static class User {
		private Long id;
		private String email;
		private String name;
		private String imageUrl;
		private String role;
		private Boolean isExpert;
		private Double rating;
		private int status;
		private String topicId;
	}
}
