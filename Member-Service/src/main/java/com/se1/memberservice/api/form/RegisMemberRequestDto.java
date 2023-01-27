package com.se1.memberservice.api.form;

import java.util.Date;

import com.se1.memberservice.domain.entity.AuthProvider;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisMemberRequestDto {

	@NotBlank
	@NotNull
	private String memberFName;
	
	@NotBlank
	@NotNull
	private String memberLName;
	
	@Email
	private String memberEmail;
	
	private String memberPhoneNumber;
	
	private AuthProvider provider;
	
	@NotBlank
	@NotEmpty
	private String displayName;
	
	@NotBlank
	@NotNull
	private String loginId;
	
	@Min(6)
	@Max(24)
	private String password;
	
	@NotBlank
	@NotEmpty
	private Date dboDt;
	
}
