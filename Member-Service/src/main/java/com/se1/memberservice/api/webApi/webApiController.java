package com.se1.memberservice.api.webApi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/web")
@RequiredArgsConstructor
public class webApiController {

	@PostMapping("/member/updateMemberInfo")
	public ResponseEntity<?> updateMemberInfo(){
		return null;
	}
}
