package com.se1.authservice.payload;

import java.util.List;

import lombok.Data;

@Data
public class ApiResponseEntity<T> {

	T data;
	Integer status;
	List<String> errorList;
}