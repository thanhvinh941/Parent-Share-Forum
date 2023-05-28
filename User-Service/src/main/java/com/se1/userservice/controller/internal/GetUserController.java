package com.se1.userservice.controller.internal;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se1.userservice.domain.model.ChatBlock;
import com.se1.userservice.domain.model.User;
import com.se1.userservice.domain.payload.GetApiRequestEntity;
import com.se1.userservice.domain.payload.UpdateApiRequestEntity;
import com.se1.userservice.domain.payload.request.UpdateUserRequest;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/internal")
public class GetUserController {

	private final UserService userService;
	
	@PostMapping("/getUser")
	public ResponseEntity<ApiResponseEntity> getUser(@RequestBody GetApiRequestEntity request){
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		try {			
			List<User> user = userService.getUser(request);
			return okResponse(user, apiResponseEntity);
		} catch (Exception e) {
			return badResponse(e.getMessage() ,apiResponseEntity);
		}
	}
	
	@PostMapping("/updateUser")
	public ResponseEntity<ApiResponseEntity> updateUser(@RequestBody UpdateApiRequestEntity request){
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		try {			
			Boolean isUpdate = userService.updateUser(request);
			return okResponse(isUpdate, apiResponseEntity);
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
