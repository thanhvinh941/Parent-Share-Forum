package com.se1.memberservice.domain.service;

import java.util.List;

import com.se1.memberservice.domain.request.dto.GetMemberDto;
import com.se1.memberservice.domain.request.dto.RegisMemberRequestDto;
import com.se1.memberservice.domain.response.dto.MemberResponse;

import jakarta.validation.Valid;

public interface MemberService {

	Boolean regisMember(@Valid RegisMemberRequestDto request);

	List<MemberResponse> getMemberWithConditon(@Valid GetMemberDto request);

}
