package com.se1.notifyservice.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se1.notifyservice.domain.db.write.WNotifyMapper;
import com.se1.notifyservice.domain.dto.NotifyDto;

@Service
public class NotifyListenerService {

	@Autowired
	private WNotifyMapper mapper;
	
	public void processNotify(String type, com.se1.notifyservice.domain.rabbitMQ.dto.NotifyDto notifyDtoRequest) {
		NotifyDto notifyDto = new NotifyDto();
		BeanUtils.copyProperties(notifyDtoRequest, notifyDto);
		notifyDto.setType(type);
		
		mapper.insertNotify(notifyDto);
	}

}
