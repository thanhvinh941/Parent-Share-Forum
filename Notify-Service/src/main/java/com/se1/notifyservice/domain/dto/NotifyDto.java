package com.se1.notifyservice.domain.dto;

import lombok.Data;

@Data
public class NotifyDto {
	 private Long userId;
	 private String param;
	 private String type;
	 private String value;
}
