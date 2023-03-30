package com.se1.userservice.domain.payload;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class FindRequest {
	    private Long id;
	    private String name;
	    private String email;
	    private LocalDate birthday;
	    private Boolean emailVerified;
	    private String role;
	    private String provider;
	    private String topicId;
		private Byte status; // 1: online, 2 offline
	    private String phoneNumber;
	    private String identifyNo;
	    private Boolean isExpert;
	    private Boolean delFlg;
	    private Date createAt;
	    private Date updateAt;

}
