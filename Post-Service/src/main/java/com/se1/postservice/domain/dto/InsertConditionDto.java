package com.se1.postservice.domain.dto;

import java.util.List;

import lombok.Data;

@Data
public class InsertConditionDto<T> {
	List<T> records;
}
