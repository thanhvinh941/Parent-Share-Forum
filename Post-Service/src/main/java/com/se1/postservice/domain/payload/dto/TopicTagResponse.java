package com.se1.postservice.domain.payload.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TopicTagResponse {
	private Integer id;
	private String tagName;
	private String color;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createAt;
	private String userCreate;
}
