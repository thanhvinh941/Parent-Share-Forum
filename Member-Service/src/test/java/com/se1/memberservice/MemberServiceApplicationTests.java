package com.se1.memberservice;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.se1.memberservice.domain.db.read.RMemberMapper;
import com.se1.memberservice.domain.dto.MemberDto;
import com.se1.memberservice.domain.service.MemberService;

@SpringBootTest
class MemberServiceApplicationTests {

	@Autowired
	RMemberMapper mapper;
	
	@Autowired
	MemberService memberService;

	SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd"); 
	
	@Test
	void getMemberWithConditonSpecifiedPattern() throws ParseException {
		
		Map<String,Object> map = new HashMap();
		map.put("display_name", "de");
		map.put("memberfname", List.of("Barney","Jody","Faunie"));
		map.put("create_dt", List.of(new Timestamp(ft.parse("2022-12-01").getTime()),new Timestamp(ft.parse("2023-01-01").getTime())));
		List<MemberDto> responses2 = memberService.getMemberWithConditonSpecifiedPattern(map);

	}
	
	@Test
	void getMemberWithConditonSpecifiedField() throws ParseException {
		
		Map<String,Object> map = new HashMap();
		map.put("display_name", "Barney Richard");
		map.put("login_id", "brichard0");
		map.put("dbo_dt", new Timestamp(ft.parse("1981-12-18").getTime()));
		map.put("valid_flg", new Byte("1"));
		List<MemberDto> responses1 = memberService.getMemberWithConditonSpecifiedField(map);

	}
}
