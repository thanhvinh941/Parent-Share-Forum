package com.se1.memberservice.domain.request.dto;

import lombok.Data;

@Data
public class MemberLoginRequestDto {

	private String loginId;
	
	private String password;
}
