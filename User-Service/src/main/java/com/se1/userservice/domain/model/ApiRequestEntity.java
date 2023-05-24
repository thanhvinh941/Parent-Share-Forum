package com.se1.userservice.domain.model;

import lombok.Data;

@Data
public class ApiRequestEntity {
	private String conditionStr;
	private String order;
	private Integer offet;
	private Integer limit;
}
