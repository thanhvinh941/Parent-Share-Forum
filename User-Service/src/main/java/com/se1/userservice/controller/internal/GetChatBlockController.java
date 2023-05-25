package com.se1.userservice.controller.internal;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se1.userservice.domain.model.ApiRequestEntity;
import com.se1.userservice.domain.model.ChatBlock;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.service.ChatBlockService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact/internal")
public class GetChatBlockController {

	private final ChatBlockService chatBlockService;

	@PostMapping("/chat-block")
	public ResponseEntity<ApiResponseEntity> getChatBlock(@RequestBody ApiRequestEntity request){
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		try {			
			ChatBlock chatBlock = chatBlockService.getChatBlock(request);
			return okResponse(chatBlock, apiResponseEntity);
		} catch (Exception e) {
			return badResponse(e.getMessage() ,apiResponseEntity);
		}
	}
	
	public ResponseEntity<ApiResponseEntity> okResponse(Object data, ApiResponseEntity apiResponseEntity) {
		apiResponseEntity.setData(data);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	public ResponseEntity<ApiResponseEntity> badResponse(String error, ApiResponseEntity apiResponseEntity) {
		apiResponseEntity.setData(null);
		apiResponseEntity.setErrorList(List.of(error));
		apiResponseEntity.setStatus(0);
		return ResponseEntity.ok(apiResponseEntity);
	}
}
