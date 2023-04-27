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
		notifyRepository.uDeleteNotifyByUserId(userId);
		responseTrue(apiResponseEntity);

	}

	public void processUpdateAll(Long userId, ApiResponseEntity apiResponseEntity) throws Exception {
		notifyRepository.updateNotifyByUserId(userId);
		responseTrue(apiResponseEntity);
	}

	public void processUpdate(Long notifyId, ApiResponseEntity apiResponseEntity) throws Exception {
		notifyRepository.updateNotifyId(notifyId);
		responseTrue(apiResponseEntity);
	}

	private void responseTrue(ApiResponseEntity apiResponseEntity) {
		apiResponseEntity.setData(true);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

}
