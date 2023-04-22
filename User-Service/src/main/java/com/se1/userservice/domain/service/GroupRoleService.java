package com.se1.userservice.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se1.userservice.domain.common.SCMConstant;
import com.se1.userservice.domain.model.GroupRole;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.CreateGroupRoleRequest;
import com.se1.userservice.domain.payload.GroupRoleResponse;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.repository.GroupRoleRepository;

@Service
public class GroupRoleService {

	@Autowired
	private GroupRoleRepository groupRoleRepository;
	
	public void processGoupRole(CreateGroupRoleRequest request, UserDetail userDetail,
			ApiResponseEntity apiResponseEntity) {
		GroupRole groupRole = new GroupRole();
		BeanUtils.copyProperties(request, groupRole);
		groupRole.setCreateAt(new Date());
		groupRole.setUpdateAt(new Date());
		groupRole.setDelFlg(SCMConstant.DEL_FLG_OFF);
		
		GroupRole groupRoleSave = groupRoleRepository.save(groupRole);
		
		GroupRoleResponse response = new GroupRoleResponse();
		BeanUtils.copyProperties(groupRoleSave, response);
		response.setRoles(SCMConstant.getAllAuthorItemByIds(groupRoleSave.getRoles()));
		
		apiResponseEntity.setData(response);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
		
	}

	public void processFindAll(UserDetail userDetail, ApiResponseEntity apiResponseEntity) {

		List<GroupRole> groupRoleSave = groupRoleRepository.findAll();
		
		List<GroupRoleResponse> responses = groupRoleSave.stream().map(g->{
			GroupRoleResponse response = new GroupRoleResponse();
			BeanUtils.copyProperties(g, response);
			response.setRoles(SCMConstant.getAllAuthorItemByIds(g.getRoles()));
			return response;
		}).collect(Collectors.toList());
		
		apiResponseEntity.setData(responses);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	public void processFindByName(String name, UserDetail userDetail, ApiResponseEntity apiResponseEntity) {
		
		List<GroupRole> groupRoleSave = groupRoleRepository.findByName(name);
		
		List<GroupRoleResponse> responses = groupRoleSave.stream().map(g->{
			GroupRoleResponse response = new GroupRoleResponse();
			BeanUtils.copyProperties(g, response);
			response.setRoles(SCMConstant.getAllAuthorItemByIds(g.getRoles()));
			return response;
		}).collect(Collectors.toList());
		
		apiResponseEntity.setData(responses);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	public void processFind(Long id, UserDetail userDetail, ApiResponseEntity apiResponseEntity) throws Exception {

		Optional<GroupRole> groupRoleSave = groupRoleRepository.findById(id);
		if(groupRoleSave.isEmpty()) {
			throw new Exception("Group role không tồn tại");
		}
		
		GroupRoleResponse response = new GroupRoleResponse();
		BeanUtils.copyProperties(groupRoleSave.get(), response);
		response.setRoles(SCMConstant.getAllAuthorItemByIds(groupRoleSave.get().getRoles()));
		
		apiResponseEntity.setData(response);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

}
