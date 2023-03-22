package com.se1.userservice.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Verification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer verifiId;
	
	private Integer userId;
	
	private String token;
	
	private Date expirationTime;
	
	private Byte validFlg;
	
	private Byte delFlg;
	
}
