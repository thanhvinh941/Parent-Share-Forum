package com.se1.userservice.controller.contact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.service.ContactService;

@RestController
@RequestMapping("/contact/internal")
public class ContactInternalController {

	@Autowired
	ContactService contactService;
	
	@Autowired
	ApiResponseEntity apiResponseEntity;
	
	@PostMapping("/getListContact")
	public ResponseEntity<?> getListFriend(@RequestParam("id") Long userId){
		try {
			contactService.processGetListContact(userId, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		return ResponseEntity.ok().body(apiResponseEntity);
	}
	
	@PostMapping("/findContactByUserIdAndTopicId")
	public ResponseEntity<?> getContact(@RequestParam("userId") Long userId,
			@RequestParam("topicId") String topicId){
		try {
			contactService.processFindContactByUserIdAndTopicIdGetListContact(userId, topicId,apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		return ResponseEntity.ok().body(apiResponseEntity);
	}
}
