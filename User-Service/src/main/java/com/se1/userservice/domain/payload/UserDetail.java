package com.se1.userservice.domain.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.se1.userservice.domain.payload.response.UserResponseForClient.ExpertInfo;

import lombok.Data;

@Data
public class UserDetail {
	private Long id;
	private String email;
	private String name;
	private String imageUrl;
	private String role;
	private Boolean isExpert;
	private ExpertInfo expertInfo;
	private String topicId;
	private int status;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastTime;
}
