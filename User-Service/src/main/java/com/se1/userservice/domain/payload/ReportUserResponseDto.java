package com.se1.userservice.domain.payload;

import java.util.List;

import lombok.Data;

@Data
public class ReportUserResponseDto {
	private Long userId;
	private Long report;
	private String name;
    private String email;
    private String imageUrl;
    private List<String> reasons;
}
