package com.se1.memberservice.domain.request.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MemberRegisRequestDto {

	private String memberFName;
	
	private String memberLName;
	
	private String memberEmail;
	
	private String memberPhoneNumber;
	
	private String displayName;
	
	private String loginId;
	
	private String password;
	
	private Date dboDt;
	
}
