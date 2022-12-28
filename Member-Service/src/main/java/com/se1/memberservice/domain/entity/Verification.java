package com.se1.memberservice.domain.entity;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Verification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer verifiId;
	
	private Integer memberId;
	
	private String token;
	
	private Date expirationTime;
	
	private Byte validFlg;
	
	private Byte delFlg;
	
}
