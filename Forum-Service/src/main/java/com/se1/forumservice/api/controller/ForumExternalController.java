package com.se1.forumservice.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se1.forumservice.domain.entity.Forum;
import com.se1.forumservice.domain.service.ForumService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/forum/external")
@RequiredArgsConstructor
public class ForumExternalController {

	private final ForumService forumService;
	
	@GetMapping("/getAllForumParent")
	public ResponseEntity<?> getAllForumParent(){
		
		List<Forum> forums = forumService.getAllForumParent();
		return ResponseEntity.ok(forums);
	}
}
