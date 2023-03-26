package com.se1.postservice.domain.db.dto;

import java.util.Date;
import lombok.Data;

@Data
public class PostDto {
	private Long id;
	private Long userId;
	private String title;
	private String metaTitle;
	private String slug;
	private String summary;
	private Boolean validFlag;
	private String context;
	private String hashTag;
	private Integer topicTagId;
	private Date createAt;
	private Date updateAt;
}
