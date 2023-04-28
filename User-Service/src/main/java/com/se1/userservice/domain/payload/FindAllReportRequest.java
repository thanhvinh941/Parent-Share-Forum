package com.se1.userservice.domain.payload;

import lombok.Data;

@Data
public class FindAllReportRequest {
	private String name;
	private String email;
}
