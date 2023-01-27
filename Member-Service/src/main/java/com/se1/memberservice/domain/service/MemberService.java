package com.se1.memberservice.domain.service;

import java.util.List;
import java.util.Map;

import com.se1.memberservice.domain.dto.InsertConditionDto;
import com.se1.memberservice.domain.dto.MemberDto;
import com.se1.memberservice.domain.entity.Member;

public interface MemberService {

	List<Member> regisMember(InsertConditionDto<Member> request);

	List<MemberDto> getMemberWithConditonSpecifiedField(Map<String, Object> request);

	List<MemberDto> getMemberWithConditonSpecifiedPattern(Map<String, Object> request);

}
