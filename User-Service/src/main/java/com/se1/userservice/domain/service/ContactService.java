package com.se1.userservice.domain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.se1.userservice.domain.common.SCMConstant;
import com.se1.userservice.domain.model.Contact;
import com.se1.userservice.domain.model.User;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.ContactDto;
import com.se1.userservice.domain.payload.UserContactDto;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.payload.UserDto;
import com.se1.userservice.domain.payload.response.RabbitRequest;
import com.se1.userservice.domain.rabbitMQ.payload.ContactResponse;
import com.se1.userservice.domain.repository.ContactRepository;
import com.se1.userservice.domain.repository.UserRepository;

@Service
public class ContactService {

	@Autowired
	private RabbitSenderService rabbitSenderService;
	
	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private UserRepository userRepository;

	public void processCreate(Contact contactCreate, ApiResponseEntity apiResponseEntity) throws Exception {
		User userReciver = userRepository.findById(contactCreate.getUserReciverId()).orElse(null);
		if (userReciver == null) {
			throw new Exception("Người dùng không tồn tại");
		}

		Contact contact = null;
		try {
			contact = contactRepository.save(contactCreate);
			User userSender = userRepository.findById(contact.getUserSenderId()).orElse(null);
			
			UserDetail userSenderDto = new UserDetail();
			BeanUtils.copyProperties(userSender, userSenderDto);
			
			UserDetail userReciverDto = new UserDetail();
			BeanUtils.copyProperties(userReciver, userReciverDto);
			
			ContactResponse contactResponse = new ContactResponse();
			BeanUtils.copyProperties(contact, contactResponse);
			contactResponse.setUserReciver(userReciverDto);
			contactResponse.setUserSender(userSenderDto);
			
			if (contact.getStatus() == 1) {
				//Send to system
				RabbitRequest rabbitResponse = new RabbitRequest();
				rabbitResponse.setAction(SCMConstant.SYSTEM_CONTACT);
				rabbitResponse.setData(contactResponse);

				rabbitSenderService.convertAndSendSysTem(rabbitResponse);

			}
		} catch (DataAccessException e) {
			throw new Exception(e.getMessage());
		}
		apiResponseEntity.setData(contact);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	public void processUpdate(long userId, long userLoginId, int statusUpdate, ApiResponseEntity apiResponseEntity)
			throws Exception {

		Contact oldContact = contactRepository.findByUserReciverIdAndUserSenderId(userId, userLoginId);

		if (oldContact != null) {
			long userReciverId = oldContact.getUserReciverId();
			long userSenderId = oldContact.getUserSenderId();

			validStatus(oldContact.getStatus(), statusUpdate);

			long contactId = contactRepository.updateContact(userReciverId, userSenderId, statusUpdate);

			Contact contactUpdate = contactRepository.findById(contactId).orElse(null);

			if (contactUpdate != null) {

				//Send to system
				RabbitRequest rabbitResponse = new RabbitRequest();
				rabbitResponse.setAction(SCMConstant.SYSTEM_CONTACT);
				rabbitResponse.setData(contactUpdate);
				rabbitSenderService.convertAndSendSysTem(rabbitResponse);

				apiResponseEntity.setData(true);
				apiResponseEntity.setErrorList(null);
				apiResponseEntity.setStatus(1);
			} else {
				throw new Exception("Thao tác không hợp lệ");
			}
		}else {
			throw new Exception("Tin nhắn không hợp lệ");
		}

	}

	private void validStatus(int statusOld, int statusUpdate) throws Exception {
		if (statusOld == 2 && statusUpdate != 0) {
			throw new Exception("Thao tác không hợp lệ");
		}

		if (statusOld == 1 && statusUpdate == 1) {
			throw new Exception("Thao tác không hợp lệ");
		}

		if (statusOld == 0 && statusUpdate != 1) {
			throw new Exception("Thao tác không hợp lệ");
		}
	}

	public void processGetListFriend(UserDetail userDetail, ApiResponseEntity apiResponseEntity) {
		Long userId = userDetail.getId();
		List<Contact> contact = contactRepository.findByUserId(userId);
		List<Contact> contactFriend = contact.stream().filter(c->c.getStatus() == 2).collect(Collectors.toList());
		List<Long> userFriendIds = contact.stream().map(c -> c.getUserReciverId().equals(userId) ? c.getUserSenderId() : c.getUserReciverId()).collect(Collectors.toList());
		List<User> userFriends = (List<User>) userRepository.findAllById(userFriendIds);
		List<UserContactDto> userFriendResponse = userFriends.stream().map(uf -> {
			UserContactDto contactDto = new UserContactDto();
			contactDto.setId(uf.getId());
			contactDto.setImageUrl(uf.getImageUrl());
			contactDto.setName(uf.getName());
			contactDto.setUserStatus(uf.getStatus().intValue());
			
			return contactDto;
		}).collect(Collectors.toList());
		List<ContactDto> contactResponses = contactFriend.stream().map(ct->{
			ContactDto contactDto = new ContactDto();
			contactDto.setCreateAt(ct.getCreateAt());
			contactDto.setId(ct.getId());
			contactDto.setStatus(ct.getStatus());
			contactDto.setTopicContactId(ct.getTopicId());
			
			Long userFriendId = ct.getUserReciverId().equals(userId) ? ct.getUserSenderId() : ct.getUserReciverId();
			UserContactDto userFriend = userFriendResponse.stream().filter(u -> userFriendId.equals(u.getId())).findFirst().get();
			contactDto.setUserFriend(userFriend);
			
			return contactDto;
		}).collect(Collectors.toList());
		
		Map<Integer , List<ContactDto>> mapResult = new HashMap<>();
		mapResult.put(2, contactResponses);
		
		apiResponseEntity.setData(mapResult);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

}
