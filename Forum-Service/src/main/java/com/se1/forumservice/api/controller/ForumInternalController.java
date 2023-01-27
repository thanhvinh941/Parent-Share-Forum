package com.se1.forumservice.api.controller;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se1.forumservice.api.form.ForumCreateDto;
import com.se1.forumservice.api.form.ForumDeleteDto;
import com.se1.forumservice.api.form.ForumUpdateDto;
import com.se1.forumservice.domain.entity.Forum;
import com.se1.forumservice.domain.service.ForumService;
import com.se1.forumservice.util.Util;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class ForumInternalController {

	private final ForumService forumService;
	
	@PostMapping("/createForum")
	public ResponseEntity<?> createForum(@Valid @RequestBody ForumCreateDto request){
		
		Forum forumRequest = new Forum();
		forumRequest.setForumName(request.getForumName());
		forumRequest.setForumSlug(Util.deAccent(request.getForumName()).replace(" ", "-"));
		forumRequest.setForumParent(request.getForumParent());
		forumRequest.setValidFlg(new Byte("1"));
		forumRequest.setCreateAt(new Date());
		forumRequest.setUpdateAt(new Date());
		
		Forum forumSave = forumService.createdForum(forumRequest);
		
		return ResponseEntity.ok(forumSave);
	}
	
	@PostMapping("/updateForum")
	public ResponseEntity<?> updateForum(@Valid @RequestBody ForumUpdateDto request){
		
		Forum forumRequest = new Forum();
		forumRequest.setForumId(request.getForumId());
		forumRequest.setForumName(request.getForumName());
		forumRequest.setForumSlug(Util.deAccent(request.getForumName()).replace(" ", "-"));
		forumRequest.setForumParent(request.getForumParent());
		
		Forum forumUpdate = forumService.updateForum(forumRequest);

		return ResponseEntity.ok(forumUpdate);
	}
	
	@PostMapping("/uDeleteForum")
	public ResponseEntity<?> uDeleteForum(@Valid @RequestBody ForumDeleteDto request){
		
		Boolean isDelete = forumService.uDeleteForum(request.getForumId());
		
		return ResponseEntity.ok(isDelete);
	}
}
