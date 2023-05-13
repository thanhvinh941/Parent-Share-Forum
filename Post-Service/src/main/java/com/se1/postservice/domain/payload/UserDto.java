package com.se1.postservice.domain.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserDto {

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
		private Boolean isRate;
		private Boolean isSub;
		private Integer ratingCount;
	}
}
