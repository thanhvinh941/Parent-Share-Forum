package com.se1.postservice.api.internalApi;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.se1.postservice.api.form.ApiResponseEntity;
import com.se1.postservice.domain.dto.InsertConditionDto;
import com.se1.postservice.domain.dto.SelectConditionDto;
import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.service.PostService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalApiController {
	
	private final PostService postService;
	
	@PostMapping("/post/insert")
	public ApiResponseEntity<List<Post>> insertpost(@RequestBody List<Post> request){
		
		List<Post> list = postService.saveAll(request);

		ApiResponseEntity<List<Post>> apiResponseEntity = new ApiResponseEntity<>();
		apiResponseEntity.setData(list);
		apiResponseEntity.setStatus(1);
		apiResponseEntity.setErrorList(List.of());
		
		return apiResponseEntity;
	}
	
	@PostMapping("/post/select")
	public ResponseEntity<?> selectpost(@RequestBody SelectConditionDto request){
		return null;
	}
	
	@PostMapping("/post/update")
	public ResponseEntity<?> updatepost(){
		return null;
	}
	
	@PostMapping("/post/delete")
	public ResponseEntity<?> deletepost(){
		return null;
	}
}
