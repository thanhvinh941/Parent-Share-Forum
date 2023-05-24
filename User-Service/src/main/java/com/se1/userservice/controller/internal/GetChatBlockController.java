package com.se1.userservice.controller.internal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se1.userservice.domain.model.ApiRequestEntity;
import com.se1.userservice.domain.payload.ApiResponseEntity;

@RestController
@RequestMapping("/contact/internal")
public class GetChatBlockController {

	@PostMapping("/chat-block")
	public ResponseEntity<ApiResponseEntity> getChatBlock(@RequestBody ApiRequestEntity request){
		return null;
	}
}
