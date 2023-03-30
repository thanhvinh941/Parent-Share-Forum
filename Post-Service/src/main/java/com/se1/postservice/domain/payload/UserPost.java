package com.se1.postservice.domain.payload;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserPost {
	private Long id;
	private String name;
	private String email;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDate birthday;
	private Boolean emailVerified;
	private String role;
	private String provider;
	private Byte status; // 1: online, 2 offline
	private String phoneNumber;
	private String identifyNo;
	private Boolean isExpert;
	private Boolean delFlg;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createAt;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateAt;
}
