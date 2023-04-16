package com.se1.postservice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.service.PostService;

@SpringBootTest
public class PostServiceTest {

	@Autowired
	PostService postService;
	
	@Autowired
	ApiResponseEntity apiResponseEntity;
	
	@Test
	void testPost() {
		Map<String, Object> map = new HashMap<>();
		map.put("hashTag", "baosuc");
		postService.findAllPostByCondition(map, apiResponseEntity, 0);
	}
}
