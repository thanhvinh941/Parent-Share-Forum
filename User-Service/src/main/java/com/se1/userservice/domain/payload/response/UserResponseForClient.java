package com.se1.userservice.domain.payload.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserResponseForClient {
	private Long id;
    private String name;
    private String email;
    private String imageUrl;
    private int status;
    private Boolean isExpert;
	private Double rating;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastTime;
}
