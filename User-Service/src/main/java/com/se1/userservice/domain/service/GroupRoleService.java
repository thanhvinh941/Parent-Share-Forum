package com.se1.userservice.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se1.userservice.domain.common.SCMConstant;
import com.se1.userservice.domain.model.GroupRole;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.GroupRoleResponse;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.payload.request.CreateGroupRoleRequest;
import com.se1.userservice.domain.payload.request.UDeleteGroupRoleRequest;
import com.se1.userservice.domain.payload.request.UpdateGroupRoleRequest;
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

	public void processupdateGoupRole(UpdateGroupRoleRequest request, UserDetail userDetail,
			ApiResponseEntity apiResponseEntity) throws Exception {
		Optional<GroupRole> groupRoleFind = groupRoleRepository.findById(request.getId());
		if(groupRoleFind.isEmpty()) {
			throw new Exception("Group role không tồn tại");
		}
		
		GroupRole groupRole = groupRoleFind.get();
		if(request.getName() != null) {
			groupRole.setName(request.getName());
		}
		
		if(request.getRoles() != null && request.getRoles().size() > 0) {
			groupRole.setRoles(request.getRoles());
		}
		
		GroupRole groupRoleSave = groupRoleRepository.save(groupRole);
		
		GroupRoleResponse response = new GroupRoleResponse();
		BeanUtils.copyProperties(groupRoleSave, response);
		response.setRoles(SCMConstant.getAllAuthorItemByIds(groupRoleSave.getRoles()));
		
		apiResponseEntity.setData(response);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	@Transactional
	public void processdeleteGoupRole(UDeleteGroupRoleRequest request, UserDetail userDetail,
			ApiResponseEntity apiResponseEntity) throws Exception {
		Optional<GroupRole> groupRoleFind = groupRoleRepository.findById(request.getId());
		if(groupRoleFind.isEmpty()) {
			throw new Exception("Group role không tồn tại");
		}
		
		groupRoleRepository.uDelete(request.getId());
		
		apiResponseEntity.setData(true);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
		
	}

}
