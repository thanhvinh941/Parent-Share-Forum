package com.se1.memberservice.domain.response.dto;

import java.util.List;

import lombok.Data;

@Data
public class ApiResponseEntity<T> {

	T data;
	Integer status;
	List<Object> errorList;
}
