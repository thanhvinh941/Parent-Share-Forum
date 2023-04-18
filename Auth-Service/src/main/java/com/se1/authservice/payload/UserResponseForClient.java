package com.se1.authservice.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserResponseForClient {
	private Long id;
    private String name;
    private String email;
    private String imageUrl;
    private String role;
    private int status;
    private Boolean isExpert;
	private Double rating;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastTime;
}
