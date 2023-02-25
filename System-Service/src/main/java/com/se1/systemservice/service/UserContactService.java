package com.se1.systemservice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.systemservice.model.UserContactDto;

@Service
public class UserContactService {

	@Autowired
	private ObjectMapper objectMapper;
	
	private List<LinkedHashMap<?, ?>> list;
	private List<UserContactDto> listDummy;
	
	private String jsonUserContactDummy = " [\r\n"
			+ "    {\r\n"
			+ "      \"userId\" : 1,\r\n"
			+ "      \"contactTopicId\" : \"f11be589-91a9-436d-bcbc-fd0401a1fb87\",\r\n"
			+ "      \"userInfoName\" : \"Thanh Vinh Test 1\",\r\n"
			+ "      \"isChoose\" : false,\r\n"
			+ "      \"status\" : false\r\n"
			+ "    },\r\n"
			+ "     {\r\n"
			+ "      \"userId\" : 2,\r\n"
			+ "      \"contactTopicId\" : \"934ec3b2-7031-4676-85d2-3cba3006bf15\",\r\n"
			+ "      \"userInfoName\" : \"Thanh Vinh Test 2\",\r\n"
			+ "      \"isChoose\" : false,\r\n"
			+ "      \"status\" : false\r\n"
			+ "    },\r\n"
			+ "     {\r\n"
			+ "      \"userId\" : 3,\r\n"
			+ "      \"contactTopicId\" : \"3f89cd83-192e-450f-97e6-d4ca0eb33144\",\r\n"
			+ "      \"userInfoName\" : \"Thanh Vinh Test 3\",\r\n"
			+ "      \"isChoose\" : false,\r\n"
			+ "      \"status\" : false\r\n"
			+ "    }\r\n"
			+ "  ]";
	
	@PostConstruct
	void setUp() throws JsonMappingException, JsonProcessingException {
		list = objectMapper.readValue(jsonUserContactDummy, List.class);
		listDummy = new ArrayList<>();
		listDummy = list.stream().map(x->{
			Integer userId = (Integer) x.get("userId");
			String contactTopicId = (String) x.get("contactTopicId");
			String userInfoName = (String) x.get("userInfoName");
			Boolean isChoose =  (Boolean) x.get("isChoose");
			Boolean status =  (Boolean) x.get("status");
			UserContactDto contactDto = new UserContactDto();
			contactDto.setUserId(userId);
			contactDto.setContactTopicId(contactTopicId);
			contactDto.setUserInfoName(userInfoName);
			contactDto.setChoose(isChoose);
			contactDto.setStatus(status);
			
			return contactDto;
		}).collect(Collectors.toList());
	}
	
	public List<UserContactDto> getContactDto(){
		return this.listDummy;
	}

	public void updateUserChoose(Integer userId, boolean isChoose) {
		listDummy.stream().forEach(x->{
			if(x.getUserId().equals(userId)) {
				x.setStatus(true);
				x.setChoose(true);
			}
		});
		
	}

	public UserContactDto getUserContactById(Integer userId) {
		return listDummy.stream().filter(x->x.getUserId().equals(userId)).findFirst().orElse(null);
	}

	public List<UserContactDto> getUserContactListById(Integer userId) {
		return listDummy.stream().filter(x -> !x.getUserId().equals(userId)).collect(Collectors.toList());
	}
}
