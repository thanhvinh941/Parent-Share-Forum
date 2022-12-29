package com.se1.memberservice.api.internalApi;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se1.memberservice.domain.request.dto.RegisMemberRequestDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/internal")
public class InternalApiController {

	@PostMapping("/insert")
	public ResponseEntity<?> insertMember(){
		return null;
	}
	
	@PostMapping("/select")
	public ResponseEntity<?> selectMember(){
		return null;
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updateMember(){
		return null;
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteMember(){
		return null;
	}
}
