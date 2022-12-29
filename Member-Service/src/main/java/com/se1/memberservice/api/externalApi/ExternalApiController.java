package com.se1.memberservice.api.externalApi;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se1.memberservice.domain.request.dto.GetMemberDto;
import com.se1.memberservice.domain.request.dto.RegisMemberRequestDto;
import com.se1.memberservice.domain.response.dto.ApiResponseEntity;
import com.se1.memberservice.domain.response.dto.MemberResponse;
import com.se1.memberservice.domain.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/external")
@RequiredArgsConstructor
public class ExternalApiController {
	
	private final MemberService service;
		
	@PostMapping("/regisMember")
	public ResponseEntity<?> regisMember(@Valid @RequestBody RegisMemberRequestDto request,BindingResult result){
		ApiResponseEntity<Boolean> apiResponseEntity = new ApiResponseEntity<>();
		if(result.hasErrors()) {
			apiResponseEntity.setStatus(0);
			apiResponseEntity.setErrorList(List.of(result.getAllErrors()));
			return ResponseEntity.ok(apiResponseEntity);
		}
		
		Boolean responseRegisMember = service.regisMember(request);
		
		if(responseRegisMember) {
			apiResponseEntity.setData(responseRegisMember);
			apiResponseEntity.setStatus(1);
			apiResponseEntity.setErrorList(List.of());
			return ResponseEntity.ok(apiResponseEntity);
		}
		
		apiResponseEntity.setStatus(0);
		apiResponseEntity.setErrorList(List.of("Server error"));
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/getLoginMemberInfo")
	public ResponseEntity<?> getLoginMemberInfo(@Valid @RequestBody GetMemberDto request ){
		ApiResponseEntity<List<MemberResponse>> apiResponseEntity = new ApiResponseEntity<>();
		List<MemberResponse> list = service.getMemberWithConditon(request);
		
		if(list.size() != 0) {
			apiResponseEntity.setData(list);
			apiResponseEntity.setStatus(1);
			apiResponseEntity.setErrorList(List.of());
			return ResponseEntity.ok(apiResponseEntity);
		}
		
		apiResponseEntity.setStatus(0);
		apiResponseEntity.setErrorList(List.of("Member not found"));
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/existMemberEmail")
	public ResponseEntity<?> existMemberEmail(){
		return null;
	}
	
	@PostMapping("/existMemberLoginId")
	public ResponseEntity<?> existMemberLoginId(){
		return null;
	}
	
	@PostMapping("/updateMemberInfo")
	public ResponseEntity<?> updateMemberInfo(){
		return null;
	}
	
	@GetMapping("/forgetPassword")
	public ResponseEntity<?> forgetPassword(){
		return null;
	}
	
	@GetMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(){
		return null;
	}
	
	@PostMapping("/getMemberAuthorization")
	public ResponseEntity<?> getMemberAuthorization(){
		return null;
	}
}
