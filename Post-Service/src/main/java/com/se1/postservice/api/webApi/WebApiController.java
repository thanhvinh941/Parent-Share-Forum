package com.se1.postservice.api.webApi;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.se1.postservice.api.form.ApiResponseEntity;
import com.se1.postservice.api.form.PostRequestDto;
import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/web")
@RequiredArgsConstructor
public class WebApiController {
	
	private final PostService postService;
	
	@PostMapping("/save")
	public ResponseEntity<?> createPost(@Valid @RequestBody PostRequestDto request) throws URISyntaxException{
		
		Post post = new Post();
		post.setPostId(request.getPostId());
		post.setMemberId(request.getMemberId());
		post.setPostTitle(request.getTitle());
		post.setPostMetaTitle(request.getMetaTitle());
		post.setPostSummary(request.getSummary());
		post.setPostSlug(UUID.randomUUID().toString());
		post.setContext(request.getContext());
		post.setCreatedAt(new Timestamp(new Date().getTime()));
		post.setUpdateAt(new Timestamp(new Date().getTime()));
		post.setValidFlag(new Byte("1"));
		
		List<Post> list = postService.saveAll(List.of(post));

		ApiResponseEntity<List<Post>> apiResponseEntity = new ApiResponseEntity<>();
		apiResponseEntity.setData(list);
		apiResponseEntity.setStatus(1);
		apiResponseEntity.setErrorList(List.of());
	
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	
	@GetMapping("/delete")
	public ResponseEntity<?> deletePost(@RequestParam("post_id") Integer postId){
		Boolean isDelete = postService.uDelete(postId);
		
		ApiResponseEntity<Boolean> apiResponseEntity = new ApiResponseEntity<>();
		apiResponseEntity.setData(isDelete);
		apiResponseEntity.setStatus(1);
		apiResponseEntity.setErrorList(List.of());
		
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@GetMapping("/publish")
	public ResponseEntity<?> publishPost(@RequestParam("post_id") Integer postId, @RequestParam("published_flg") Byte publishedFlg){
		Boolean isPublish = postService.uPublish(postId, publishedFlg);
		
		ApiResponseEntity<Boolean> apiResponseEntity = new ApiResponseEntity<>();
		apiResponseEntity.setData(isPublish);
		apiResponseEntity.setStatus(1);
		apiResponseEntity.setErrorList(List.of());
		
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/like")
	public ResponseEntity<?> likePost(){
		return null;
	}
}
