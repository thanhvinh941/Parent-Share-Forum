package com.se1.userservice.domain.service;

import java.util.Date;

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
		groupRole.setDelFlg(false);
		
		GroupRole groupRoleSave = groupRoleRepository.save(groupRole);
		
		GroupRoleResponse response = new GroupRoleResponse();
		BeanUtils.copyProperties(groupRoleSave, response);
		response.setRoles(SCMConstant.getAllAuthorItemByIds(groupRoleSave.getRoles()));
		
		apiResponseEntity.setData(response);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
		
	}

}
