package com.se1.userservice.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se1.userservice.domain.common.SCMConstant;
import com.se1.userservice.domain.db.write.WUserMapper;
import com.se1.userservice.domain.model.Licence;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.payload.request.AcceptExpertRequest;
import com.se1.userservice.domain.repository.LicenceRepository;

@Service
public class LicenceService {

	@Autowired
	private LicenceRepository licenceRepository;
	
	@Autowired
	private WUserMapper wUserMapper;
	
	public void processAcceptExpert(UserDetail userDetail, AcceptExpertRequest request,
			ApiResponseEntity apiResponseEntity) {
		Long userId = request.getUserId();
		Licence licence = new Licence();
		BeanUtils.copyProperties(request, licence);
		licence.setValidFlg(SCMConstant.VALID_FLG);
		
		Licence licenceSave = licenceRepository.save(licence);
		Long licenceId = licenceSave.getId();
		
		wUserMapper.updateLicenceToUser(licenceId, userId);
		
		apiResponseEntity.setData(true);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

}
