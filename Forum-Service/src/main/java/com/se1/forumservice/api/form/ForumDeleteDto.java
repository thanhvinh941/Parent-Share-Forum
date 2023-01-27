package com.se1.forumservice.api.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForumDeleteDto {

	@NotNull
	private Integer forumId;
}
