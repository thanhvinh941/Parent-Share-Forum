package com.se1.postservice.api.internalApi;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.domain.entity.TopicTag;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.TopicTagRequest;
import com.se1.postservice.domain.payload.RegistTopicTagDto;
import com.se1.postservice.domain.payload.UpdateTopicTagDto;
import com.se1.postservice.domain.payload.UserDetail;
import com.se1.postservice.domain.service.impl.TopicTagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post/internal/topicTag")
@RequiredArgsConstructor
public class TopicTagInternalController {

	private final TopicTagService propertyTagService;
	private final ApiResponseEntity apiResponseEntity;
	private final ObjectMapper objectMapper;
	
	@RequestMapping("/save")
	public ResponseEntity<?> savePropertyTag(
			@RequestParam(value = "topicTag", required = false) TopicTagRequest propertyTagRequest, @RequestParam("file") MultipartFile multipartFile) throws JsonMappingException, JsonProcessingException{
		List<String> errorList = propertyTagService.validation(propertyTagRequest);
		
		if(errorList.size() > 0) {
			return this.badResponse(errorList);
		}
		
//		UserDetail detail = objectMapper.readValue(userDetail, UserDetail.class);
//		if(!detail.getRole().equals("admin")) {
//			return this.forbidenResponse(List.of("admin"));
//		}
		TopicTag propertyTag = null;
		if(Objects.isNull(propertyTagRequest.getId())) {
			propertyTag = convertPropertyTagRequestToPropertyTagEntityForRegist(propertyTagRequest);
			TopicTag propertyTagSave = propertyTagService.savePropertyTag(propertyTag);
			RegistTopicTagDto registPropertyTagDto = new RegistTopicTagDto();
			if(!Objects.isNull(propertyTagSave)) {
				registPropertyTagDto.setIsRegist(true);
				registPropertyTagDto.setMessage(List.of("create post success"));
				
				return this.okResponse(registPropertyTagDto, null);
			}else {
				registPropertyTagDto.setIsRegist(false);
				registPropertyTagDto.setMessage(List.of("create post fail"));
				return this.okResponse(registPropertyTagDto, null);
			}
		}else {
			propertyTag = convertPropertyTagRequestToPropertyTagEntityForUpdate(propertyTagRequest);
			boolean isUpdate = propertyTagService.updateById(propertyTag);
			UpdateTopicTagDto updatePropertyTagDto = new UpdateTopicTagDto();
			if(isUpdate) {
				updatePropertyTagDto.setIsUpdate(true);
				updatePropertyTagDto.setMessage(List.of("update post success"));
				return this.okResponse(updatePropertyTagDto, null);
			}else {
				updatePropertyTagDto.setIsUpdate(false);
				updatePropertyTagDto.setMessage(List.of("update post fail"));
				return this.okResponse(updatePropertyTagDto, null);
			}
		}
	
		
	}
	
	private ResponseEntity<?> forbidenResponse(List<String> errorMessage){
		apiResponseEntity.setData(null);
		apiResponseEntity.setErrorList(errorMessage);
		apiResponseEntity.setStatus(0);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponseEntity);
	}

	private ResponseEntity<?> badResponse(List<String> errorMessage){
		apiResponseEntity.setData(null);
		apiResponseEntity.setErrorList(errorMessage);
		apiResponseEntity.setStatus(0);
		return ResponseEntity.badRequest().body(apiResponseEntity);
	}
	
	private ResponseEntity<?> okResponse(Object data, List<String> errorMessage){
		apiResponseEntity.setData(data);
		apiResponseEntity.setErrorList(errorMessage);
		apiResponseEntity.setStatus(1);
		return ResponseEntity.ok().body(apiResponseEntity);
	}
	
	private TopicTag convertPropertyTagRequestToPropertyTagEntityForRegist(TopicTagRequest propertyTagRequest) {
		TopicTag propertyTag = new TopicTag();
		propertyTag.setTagName(propertyTagRequest.getTagName());
		propertyTag.setColor(propertyTagRequest.getColor());
		propertyTag.setValidFlag(propertyTagRequest.getValidFlg() != null ? propertyTagRequest.getValidFlg() : true);
		propertyTag.setCreateAt(new Date());
		propertyTag.setUpdateAt(new Date());
		
		return propertyTag;
	}
	
	private TopicTag convertPropertyTagRequestToPropertyTagEntityForUpdate(TopicTagRequest propertyTagRequest) {
		TopicTag propertyTag = new TopicTag();
		propertyTag.setId(propertyTagRequest.getId());
		propertyTag.setTagName(propertyTagRequest.getTagName());
		propertyTag.setColor(propertyTagRequest.getColor());
		propertyTag.setValidFlag(propertyTagRequest.getValidFlg() != null ? propertyTagRequest.getValidFlg() : true);
		propertyTag.setUpdateAt(new Date());
		
		return propertyTag;
	}
	
}
