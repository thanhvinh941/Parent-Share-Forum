package com.se1.memberservice.api.externalApi;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se1.memberservice.api.form.ApiResponseEntity;
import com.se1.memberservice.api.form.RegisMemberRequestDto;
import com.se1.memberservice.domain.dto.MemberDto;
import com.se1.memberservice.domain.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/external")
@RequiredArgsConstructor
public class ExternalApiController {
	
	private final MemberService service;
		
	@PostMapping("/member/regisMember")
	public ResponseEntity<?> regisMember(@Valid @RequestBody RegisMemberRequestDto request,BindingResult result){
		ApiResponseEntity<Boolean> apiResponseEntity = new ApiResponseEntity<>();
		if(result.hasErrors()) {
			apiResponseEntity.setStatus(0);
			apiResponseEntity.setErrorList(List.of(result.getAllErrors()));
			return ResponseEntity.ok(apiResponseEntity);
		}
		
//		Boolean responseRegisMember = service.regisMember(request);
		
//		if(responseRegisMember) {
//			apiResponseEntity.setData(responseRegisMember);
//			apiResponseEntity.setStatus(1);
//			apiResponseEntity.setErrorList(List.of());
//			return ResponseEntity.ok(apiResponseEntity);
//		}
		
		apiResponseEntity.setStatus(0);
		apiResponseEntity.setErrorList(List.of("Server error"));
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/member/getLoginMemberInfo")
	public ResponseEntity<?> getLoginMemberInfo(){
		ApiResponseEntity<List<MemberDto>> apiResponseEntity = new ApiResponseEntity<>();
//		List<MemberResponse> list = service.getMemberWithConditonSpecifiedField(request);
//		
//		if(list.size() != 0) {
//			apiResponseEntity.setData(list);
//			apiResponseEntity.setStatus(1);
//			apiResponseEntity.setErrorList(List.of());
//			return ResponseEntity.ok(apiResponseEntity);
//		}
		
		apiResponseEntity.setStatus(0);
		apiResponseEntity.setErrorList(List.of("Member not found"));
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/member/existMemberEmail")
	public ResponseEntity<?> existMemberEmail(){
		return null;
	}
	
	@PostMapping("/member/existMemberLoginId")
	public ResponseEntity<?> existMemberLoginId(){
		return null;
	}
	
	@PostMapping("/member/forgetPassword")
	public ResponseEntity<?> forgetPassword(){
		return null;
	}
	
	@PostMapping("/member/resetPassword")
	public ResponseEntity<?> resetPassword(){
		return null;
	}
	
	@PostMapping("/verify/generatorToken")
	public ResponseEntity<?> generatorToken(){
		return null;
	}
	
	@PostMapping("/verify/verifyToken")
	public ResponseEntity<?> verifyToken(){
		return null;
	}
	
	@PostMapping("/refreshToken/generatoRefreshToken")
	public ResponseEntity<?> generatoRefreshToken(){
		return null;
	}
	
	@PostMapping("/refreshToken/existsRefreshToken")
	public ResponseEntity<?> existsRefreshToken(){
		return null;
	}
}
