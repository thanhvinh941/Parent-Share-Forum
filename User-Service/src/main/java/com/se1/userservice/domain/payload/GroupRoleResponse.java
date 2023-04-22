package com.se1.userservice.domain.payload;

import java.util.Date;
import java.util.Map;

import lombok.Data;

@Data
public class GroupRoleResponse {
	private Long id;
	private String name;
	private Map<Integer, String> roles;
	private Boolean delFlg;
	private Date createAt;
	private Date updateAt;
}
