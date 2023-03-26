package com.se1.notifyservice.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.se1.notifyservice.domain.model.Notify;
import com.se1.notifyservice.domain.payload.ApiResponseEntity;
import com.se1.notifyservice.domain.repository.NotifyRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NotifyService {

	private final NotifyRepository notifyRepository;
	public void processFind(Long userId, ApiResponseEntity apiResponseEntity) {
		List<Notify> notifies = notifyRepository.findByUserId(userId); 
		
		apiResponseEntity.setData(notifies);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	public void processDeleteAll(Long userId, ApiResponseEntity apiResponseEntity) throws Exception {
		Long uDelete = notifyRepository.uDeleteNotifyByUserId(userId);
		
		if(uDelete > 0) {
			apiResponseEntity.setData(true);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);			
		}else {
			throw new Exception("Delete không thành công");
		}
	}

	public void processUpdateAll(Long userId, ApiResponseEntity apiResponseEntity) throws Exception {
		Long update = notifyRepository.updateNotifyByUserId(userId);
		
		if(update > 0) {
			apiResponseEntity.setData(true);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);			
		}else {
			throw new Exception("Update không thành công");
		}
	}

	public void processUpdate(Long notifyId, ApiResponseEntity apiResponseEntity) throws Exception {
		Long update = notifyRepository.updateNotifyId(notifyId);
		
		if(update > 0) {
			apiResponseEntity.setData(true);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);			
		}else {
			throw new Exception("Update không thành công");
		}
	}

}
