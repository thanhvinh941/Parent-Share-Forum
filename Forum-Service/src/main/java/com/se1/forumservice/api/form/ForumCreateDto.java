package com.se1.forumservice.api.form;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForumCreateDto {

	@NotEmpty
	@NotBlank
	private String forumName;
	
	private Integer forumParent;
	
}
