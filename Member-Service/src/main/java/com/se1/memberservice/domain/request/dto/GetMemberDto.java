package com.se1.memberservice.domain.request.dto;

import java.util.Map;

import lombok.Data;

@Data
public class GetMemberDto {

	Map<String, String> condition;
}
