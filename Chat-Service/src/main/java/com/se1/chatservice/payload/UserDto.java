package com.se1.chatservice.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserDto {
	private Long id;
    private String name;
    private String email;
    private String imageUrl;
    private int status;
    private Boolean isExpert;
    private String topicId;
	private Double rating;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastTime;
}
