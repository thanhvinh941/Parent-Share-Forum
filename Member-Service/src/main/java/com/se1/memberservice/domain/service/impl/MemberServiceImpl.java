package com.se1.memberservice.domain.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.se1.memberservice.domain.entity.Member;
import com.se1.memberservice.domain.repository.MemberRepository;
import com.se1.memberservice.domain.request.dto.GetMemberDto;
import com.se1.memberservice.domain.request.dto.RegisMemberRequestDto;
import com.se1.memberservice.domain.response.dto.MemberResponse;
import com.se1.memberservice.domain.service.MemberService;
import com.se1.memberservice.model.db.read.RMemberMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	
	private final MemberRepository memberRepository;
	
	private final RMemberMapper mapper;

	
	@Override
	public Boolean regisMember(@Valid RegisMemberRequestDto request) {
		
		Member member = new Member();
		member.setMemberFName(request.getMemberFName());
		member.setMemberLName(request.getMemberLName());
		member.setMemberEmail(request.getMemberEmail());
		member.setMemberPhoneNumber(request.getMemberPhoneNumber());
		member.setProvider(request.getProvider());
		member.setDisplayName(request.getDisplayName());
		member.setLoginId(request.getLoginId());
		member.setPassword(request.getPassword());
		member.setDboDt(request.getDboDt());
		member.setCreateDt(Timestamp.valueOf(LocalDateTime.now()));
		member.setUpdateDt(Timestamp.valueOf(LocalDateTime.now()));
		member.setStatusFlg(new Byte("0"));
		member.setValidFlg(new Byte("1"));
		member.setDelFlg(new Byte("0"));

		Member memberSave = memberRepository.save(member);
		
		if(memberSave != null) {
			return true;
		}
		return false;
	}


	@Override
	public List<MemberResponse> getMemberWithConditon(@Valid GetMemberDto request) {
		
		String whereStr = generatorWhereCondition(request.getCondition());
		
		List<Member> members = mapper.selectWhere(whereStr);
		
		return null;
	}


	private String generatorWhereCondition(Map<String, String> condition) {
		StringBuilder stringBuilder =  new StringBuilder();
		stringBuilder.append("where ");
		condition.entrySet().stream().forEach(x -> stringBuilder.append(x.getKey() + " = " + x.getValue()));
		return stringBuilder.toString();
	}

}
