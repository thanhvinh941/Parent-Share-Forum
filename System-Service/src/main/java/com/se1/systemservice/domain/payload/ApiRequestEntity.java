package com.se1.systemservice.domain.payload;

import lombok.Data;

@Data
public class ApiRequestEntity {
	private String conditionStr;
	private String order;
	private Integer offet;
	private Integer limit;
}
