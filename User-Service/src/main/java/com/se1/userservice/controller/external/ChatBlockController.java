package com.se1.userservice.controller.external;

import java.util.List;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.userservice.domain.model.ChatBlock;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.payload.request.ChatBlockRequest;
import com.se1.userservice.domain.service.ChatBlockService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact/external")
public class ChatBlockController {

	private final ChatBlockService chatBlockService;
	private final ObjectMapper objectMapper;
	
	@PostMapping("/create-chat-block")
	public ResponseEntity<ApiResponseEntity> chatBlockCreate(@RequestBody ChatBlockRequest request, @RequestHeader("user_detail") String userDetailHeader) throws JsonMappingException, JsonProcessingException{
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		try {			
			ChatBlock chatBlock = chatBlockService.createChatBlock(request, userDetail.getId());
			if(!Objects.isNull(chatBlock)) {
				return okResponse(true, apiResponseEntity);
			}
			
			return okResponse(false, apiResponseEntity);
		} catch (Exception e) {
			return badResponse(null, e.getMessage() ,apiResponseEntity);
		}
	}
	
	@PostMapping("/delete-chat-block")
	public ResponseEntity<ApiResponseEntity> deleteChatBlock(@RequestBody ChatBlockRequest request, @RequestHeader("user_detail") String userDetailHeader) throws JsonMappingException, JsonProcessingException{
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		try {			
			ChatBlock chatBlock = chatBlockService.deleteChatBlock(request, userDetail.getId());
			if(!Objects.isNull(chatBlock)) {
				return okResponse(true, apiResponseEntity);
			}
			
			return okResponse(false, apiResponseEntity);
		} catch (Exception e) {
			return badResponse(null, e.getMessage() ,apiResponseEntity);
		}
	}
	
	public ResponseEntity<ApiResponseEntity> okResponse(Object data, ApiResponseEntity apiResponseEntity) {
		apiResponseEntity.setData(data);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	public ResponseEntity<ApiResponseEntity> badResponse(Object data, String error, ApiResponseEntity apiResponseEntity) {
		apiResponseEntity.setData(data);
		apiResponseEntity.setErrorList(List.of(error));
		apiResponseEntity.setStatus(0);
		return ResponseEntity.ok(apiResponseEntity);
	}
}
