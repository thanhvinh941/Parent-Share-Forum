package com.se1.memberservice.domain.entity;

import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberId;
	
	private String memberFName;
	
	private String memberLName;
	
	private String memberEmail;
	
	private String memberPhoneNumber;
	
	private String displayName;
	
	private String loginId;
	
	private String password;
	
	private String avatarUrl;
	
	private String backgroudUrl;
	
	private Date dboDt;
	
	private Timestamp lastLoginTime;
	
	private Byte statusFlg;
	
	private Byte validFlg;
	
	private Byte delFlg;
	
	private Timestamp createDt;
	
	private Timestamp updateDt;
}
