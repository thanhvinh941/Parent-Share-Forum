package com.se1.userservice;

import java.util.List;

import lombok.Data;

@Data
public class ApiResponseEntityList<T> {

	List<T> data;
	Integer status;
	List<String> errorList;
}
