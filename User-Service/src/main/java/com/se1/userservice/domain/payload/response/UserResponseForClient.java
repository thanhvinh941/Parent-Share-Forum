package com.se1.userservice.domain.payload.response;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserResponseForClient {
	private Long id;
	private String name;
	private String email;
	private String imageUrl;
	private Boolean isExpert;
	private String topicId;
	private ExpertInfo expertInfo;
	private ContactInfo contactInfo;
	private int status;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastTime;

	@Data
	public static class ContactInfo {
		private String topicContactId;
		private Integer status;
	}
	
	@Data
	public static class ExpertInfo {
		private String jobTitle;
		private String specialist;
		private String workPlace;
		private Double rating;
	}
	
}
