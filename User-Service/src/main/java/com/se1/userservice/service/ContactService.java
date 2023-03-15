package com.se1.userservice.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.se1.userservice.config.MqConfig;
import com.se1.userservice.model.Contact;
import com.se1.userservice.model.User;
import com.se1.userservice.payload.ApiResponseEntity;
import com.se1.userservice.payload.NotifycationDto;
import com.se1.userservice.repository.ContactRepository;
import com.se1.userservice.repository.UserRepository;

@Service
public class ContactService {

	private final String CONTACT_ACTION = "contact";
	
	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void processCreate(Contact contactCreate, ApiResponseEntity apiResponseEntity) throws Exception {
		User userReciver = userRepository.findById(contactCreate.getUserReciverId()).orElse(null);
		if (userReciver == null) {
			throw new Exception("Người dùng không tồn tại");
		}

		Contact contact = null;
		try {
			contact = contactRepository.save(contactCreate);

			if (contact.getStatus() == 1) {
				NotifycationDto notifycationToUserReciver = new NotifycationDto();
				notifycationToUserReciver.setAction(CONTACT_ACTION);
				notifycationToUserReciver.setTopicUserId(userReciver.getTopicId());
				notifycationToUserReciver.setRequest(contact);

				rabbitTemplate.convertAndSend(MqConfig.EXCHANGE, MqConfig.ROUTING_KEY, notifycationToUserReciver);

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
			User userReciver = userRepository.findById(userReciverId).orElse(null);

			long userSenderId = oldContact.getUserSenderId();
			User userSender = userRepository.findById(userSenderId).orElse(null);

			validStatus(oldContact.getStatus(), statusUpdate);

			long contactId = contactRepository.updateContact(userReciverId, userSenderId, statusUpdate);

			Contact contactUpdate = contactRepository.findById(contactId).orElse(null);

			if (contactUpdate != null) {

				NotifycationDto notifycationToUserReciver = new NotifycationDto();
				notifycationToUserReciver.setAction(CONTACT_ACTION);
				notifycationToUserReciver.setTopicUserId(userReciver.getTopicId());
				notifycationToUserReciver.setRequest(contactUpdate);

				rabbitTemplate.convertAndSend(MqConfig.EXCHANGE, MqConfig.ROUTING_KEY, notifycationToUserReciver);

				NotifycationDto notifycationToUserSender = new NotifycationDto();
				notifycationToUserSender.setAction(CONTACT_ACTION);
				notifycationToUserSender.setTopicUserId(userSender.getTopicId());
				notifycationToUserSender.setRequest(contactUpdate);

				rabbitTemplate.convertAndSend(MqConfig.EXCHANGE, MqConfig.ROUTING_KEY, notifycationToUserSender);

				apiResponseEntity.setData(true);
				apiResponseEntity.setErrorList(null);
				apiResponseEntity.setStatus(1);
			} else {
				throw new Exception("Thao tác không hợp lệ");
			}
		} else {
			Contact contactCreate = new Contact();
			contactCreate.setStatus(1);
			contactCreate.setTopicId(UUID.randomUUID().toString());
			contactCreate.setUserReciverId(userId);
			contactCreate.setUserSenderId(userLoginId);
			contactCreate.setCreateAt(new Date());
			Contact contact = null;
			try {
				contact = contactRepository.save(contactCreate);
			} catch (DataAccessException e) {
				throw new Exception(e.getMessage());
			}
			apiResponseEntity.setData(contact);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
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

}
