package com.se1.memberservice.domain.response.dto;


import lombok.Data;

@Data
public class MemberResponse {

	private String memberFName;
	private String memberLName;
	private String memberEmail;
	private String memberPhoneNumber;
	private String authProvider;
	private String displayName;
	private String avatarUrl;
	private String backgroudUrl;
	private String loginId;
	private String password;
	private String dboDt;
	private String lastLoginTime;
	private Byte statusFlg;
	private Byte validFlg;
	private Byte delFlg;
	private String createDt;
	private String updateDt;
}
