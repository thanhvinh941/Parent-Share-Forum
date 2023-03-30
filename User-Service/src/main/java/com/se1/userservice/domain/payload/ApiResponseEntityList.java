package com.se1.userservice.domain.payload;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ApiResponseEntityList {

	List<Object> data;
	Integer status;
	List<String> errorList;
}
