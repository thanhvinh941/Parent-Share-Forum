package com.se1.memberservice.api.externalApi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external")
public class ExternalApiController {
	
	@PostMapping("/regis")
	public ResponseEntity<?> regisMember(){
		return null;
	}
	
	@PostMapping("/getLoginMemberInfo")
	public ResponseEntity<?> getLoginMemberInfo(){
		return null;
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
