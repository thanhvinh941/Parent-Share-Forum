package com.se1.memberservice.domain.dto;

import java.util.List;

import lombok.Data;

@Data
public class InsertConditionDto<T> {
	List<T> records;
}
