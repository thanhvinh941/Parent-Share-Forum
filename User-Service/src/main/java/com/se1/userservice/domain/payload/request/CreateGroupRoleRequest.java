package com.se1.userservice.domain.payload.request;

import java.util.List;

import lombok.Data;

@Data
public class CreateGroupRoleRequest {
	String name;
	private List<String> roles;
}
