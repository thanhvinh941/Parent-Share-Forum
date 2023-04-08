package com.se1.postservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.domain.db.read.RPostMapper;

@SpringBootTest
public class PostMapperTest {

	@Autowired
	RPostMapper postMapper;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	void test1() throws JsonProcessingException {
		 Object object = postMapper.findAllByIdWithCondition("1, 2", 0);
		 objectMapper.writeValueAsString(object);
	}
}
