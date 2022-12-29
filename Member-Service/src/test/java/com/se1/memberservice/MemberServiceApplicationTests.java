package com.se1.memberservice;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.se1.memberservice.domain.entity.Member;
import com.se1.memberservice.model.db.read.RMemberMapper;

@SpringBootTest
class MemberServiceApplicationTests {

	@Autowired
	RMemberMapper mapper;
	
	@Test
	void contextLoads() {
		List<Member> list = mapper.selectWhere(null);
		System.out.println(list);
	}

}
