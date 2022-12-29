package com.se1.memberservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.se1.memberservice.domain.entity.Member;
import com.se1.memberservice.domain.request.dto.GetMemberDto;
import com.se1.memberservice.domain.response.dto.MemberResponse;
import com.se1.memberservice.domain.service.MemberService;
import com.se1.memberservice.model.db.read.RMemberMapper;

@SpringBootTest
class MemberServiceApplicationTests {

	@Autowired
	RMemberMapper mapper;
	
	@Autowired
	MemberService memberService;
	
	@Test
	void contextLoads() {
		List<Member> list = mapper.selectWhere(null);
		System.out.println(list);
	}

	@Test
	void testSelectMember() {
		GetMemberDto dto = new GetMemberDto();
		
		Map<String,String> map = new HashMap();
		map.put("test1", "test1");
		dto.setCondition(map);
		List<MemberResponse> responses = memberService.getMemberWithConditon(dto);
	}
}
