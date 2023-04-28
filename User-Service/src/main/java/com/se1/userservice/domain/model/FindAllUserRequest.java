package com.se1.userservice.domain.model;

import java.util.List;

import lombok.Data;

@Data
public class FindAllUserRequest {

	private String name;
	private String email;
	private List<String> provider;
	private List<String> role;
	private Boolean isReport;
}
