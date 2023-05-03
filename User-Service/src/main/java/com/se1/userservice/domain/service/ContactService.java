package com.se1.userservice.domain.service;

import com.se1.userservice.domain.model.Contact;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserDetail;

public interface ContactService {

	void processCreate(Contact contactCreate, ApiResponseEntity apiResponseEntity) throws Exception;

	void processUpdate(long userReciverId, long userSenderId, int statusUpdate, ApiResponseEntity apiResponseEntity) throws Exception;

	void processGetContactRequest(UserDetail userDetail, ApiResponseEntity apiResponseEntity) throws Exception;

	Object processGetListContactForChat(UserDetail userDetail, ApiResponseEntity apiResponseEntity) throws Exception;

}
