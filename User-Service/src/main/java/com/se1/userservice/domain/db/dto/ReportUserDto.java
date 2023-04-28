package com.se1.userservice.domain.db.dto;

import lombok.Data;

@Data
public class ReportUserDto {

	private Long userId;
	private Long report;
	private String name;
    private String email;
    private String imageUrl;
}
