package com.se1.chatservice.domain.db.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ChatDto {

	private Long id;
	private Long userId;
	private String content;
	private int status;
	private String topicId;
	private Long chatParent;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createAt;
	private Long userDeleteId;
	private Integer isFile;
	private List<React> reactList;

	@Data
	public static class React {
		private Long userId;
		private String reactCode;
	}
}
