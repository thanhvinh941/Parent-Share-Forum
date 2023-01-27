package com.se1.memberservice.domain.dto;


import lombok.Data;

@Data
public class MemberDto {
	private Integer memberId;
	private String memberFName;
	private String memberLName;
	private String memberEmail;
	private String memberPhoneNumber;
	private String provider;
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
