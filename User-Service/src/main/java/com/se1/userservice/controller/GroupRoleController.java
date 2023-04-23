package com.se1.userservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.payload.request.CreateGroupRoleRequest;
import com.se1.userservice.domain.payload.request.UDeleteGroupRoleRequest;
import com.se1.userservice.domain.payload.request.UpdateGroupRoleRequest;
import com.se1.userservice.domain.service.GroupRoleService;

@RestController
@RequestMapping("/group-role/external")
public class GroupRoleController {

	@Autowired
	private ApiResponseEntity apiResponseEntity;

	@Autowired
	private GroupRoleService groupRoleService;

	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping("/create")
	public ResponseEntity<?> createGroupRole(@RequestBody CreateGroupRoleRequest request,
			@RequestHeader("user_detail") String userDetailHeader)
			throws JsonMappingException, JsonProcessingException {

		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);

		try {
			groupRoleService.processGoupRole(request, userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}

		return ResponseEntity.ok(apiResponseEntity);

	}

	@PostMapping("/findById")
	public ResponseEntity<?> findById(@RequestParam("id") Long id,@RequestHeader("user_detail") String userDetailHeader) throws JsonMappingException, JsonProcessingException {
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		try {
			groupRoleService.processFind(id, userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}

		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/findByName")
	public ResponseEntity<?> findByName(@RequestParam("name") String name,@RequestHeader("user_detail") String userDetailHeader) throws JsonMappingException, JsonProcessingException {
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		try {
			groupRoleService.processFindByName(name, userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}

		return ResponseEntity.ok(apiResponseEntity);
	}

	@PostMapping("/findAll")
	public ResponseEntity<?> findAll(@RequestHeader("user_detail") String userDetailHeader) throws JsonMappingException, JsonProcessingException {
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		try {
			groupRoleService.processFindAll(userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}

		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updateGroupRole(@RequestBody UpdateGroupRoleRequest request,
			@RequestHeader("user_detail") String userDetailHeader)
			throws JsonMappingException, JsonProcessingException {

		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);

		try {
			groupRoleService.processupdateGoupRole(request, userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}

		return ResponseEntity.ok(apiResponseEntity);

	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteGroupRole(@RequestBody UDeleteGroupRoleRequest request,
			@RequestHeader("user_detail") String userDetailHeader)
			throws JsonMappingException, JsonProcessingException {

		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);

		try {
			groupRoleService.processdeleteGoupRole(request, userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}

		return ResponseEntity.ok(apiResponseEntity);

	}
}
