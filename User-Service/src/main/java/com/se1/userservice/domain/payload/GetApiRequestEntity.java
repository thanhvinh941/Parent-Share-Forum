package com.se1.userservice.domain.payload;

import lombok.Data;

@Data
public class GetApiRequestEntity {
	private String conditionStr;
	private String order;
	private Integer offset;
	private Integer limit;
}
