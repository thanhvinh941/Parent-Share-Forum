package com.se1.postservice.domain.payload;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ApiResponseEntity {

	Object data;
	Integer status;
	List<String> errorList;
}
