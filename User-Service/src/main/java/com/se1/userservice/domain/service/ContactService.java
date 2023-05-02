package com.se1.userservice.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.userservice.domain.common.SCMConstant;
import com.se1.userservice.domain.db.read.RContactMapper;
import com.se1.userservice.domain.model.Contact;
import com.se1.userservice.domain.model.User;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.ContactDto;
import com.se1.userservice.domain.payload.ContactDtoForChat;
import com.se1.userservice.domain.payload.ContactDtoForChat.Chat;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.payload.response.RabbitRequest;
import com.se1.userservice.domain.rabbitMQ.payload.ContactResponse;
import com.se1.userservice.domain.repository.ContactRepository;
import com.se1.userservice.domain.repository.SubscriberRepository;
import com.se1.userservice.domain.repository.UserRepository;
import com.se1.userservice.domain.restClient.ChatServiceRestTemplate;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ContactService {

	private final RabbitSenderService rabbitSenderService;

	private final ContactRepository contactRepository;

	private final UserRepository userRepository;

	private final ChatServiceRestTemplate restTemplate;

	private final ObjectMapper objectMapper;

	private final RContactMapper contactMapper;

	public void processCreate(Contact contactCreate, ApiResponseEntity apiResponseEntity) throws Exception {
		User userReciver = userRepository.findById(contactCreate.getUserReciverId()).orElse(null);
		if (userReciver == null) {
			throw new Exception("Người dùng không tồn tại");
		}

		Contact contact = null;

		com.se1.userservice.domain.db.dto.ContactDto oldContact = contactMapper
				.findByUserReciverIdOrUserSenderId(contactCreate.getUserReciverId(), contactCreate.getUserSenderId());

		try {
			if (oldContact != null) {
				contactRepository.updateContactV2(contactCreate.getUserReciverId(), contactCreate.getUserSenderId(),
						oldContact.getId(), contactCreate.getStatus());
				contact = contactRepository.findById(oldContact.getId()).get();
			} else {
				contact = contactRepository.save(contactCreate);
			}
			User userSender = userRepository.findById(contact.getUserSenderId()).orElse(null);

			if (userSender != null && userReciver != null) {

				if (contact.getStatus() == 1) {
					UserDetail userSenderDto = new UserDetail();
					BeanUtils.copyProperties(userSender, userSenderDto);
					
					UserDetail userReciverDto = new UserDetail();
					BeanUtils.copyProperties(userReciver, userReciverDto);
					
					ContactResponse contactResponse = new ContactResponse();
					BeanUtils.copyProperties(contact, contactResponse);
					contactResponse.setUserReciver(userReciverDto);
					contactResponse.setUserSender(userSenderDto);
					
					RabbitRequest rabbitResponse = new RabbitRequest();
					rabbitResponse.setAction(SCMConstant.SYSTEM_CONTACT);
					rabbitResponse.setData(contactResponse);

					rabbitSenderService.convertAndSendSysTem(rabbitResponse);

				}
			}
		} catch (DataAccessException e) {
			throw new Exception(e.getMessage());
		}
		apiResponseEntity.setData(contact);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	public void processUpdate(long userReciverId, long userSenderId, int statusUpdate,
			ApiResponseEntity apiResponseEntity) throws Exception {

		com.se1.userservice.domain.db.dto.ContactDto oldContact = null;
		if (statusUpdate == 2) {
			oldContact = contactMapper.findByUserReciverIdAndUserSenderId(userReciverId, userSenderId);
		} else if (statusUpdate == 0) {
			oldContact = contactMapper.findByUserReciverIdOrUserSenderId(userReciverId, userSenderId);
		}

		if (oldContact != null) {
			validStatus(oldContact.getStatus(), statusUpdate);

			contactRepository.updateContact(oldContact.getId(), statusUpdate);

			Contact contactUpdate = contactRepository.findById(oldContact.getId()).orElse(null);

			if (contactUpdate != null) {

				if (contactUpdate.getStatus() != 0) {
					User userReciver = userRepository.findById(contactUpdate.getUserReciverId()).orElse(null);
					User userSender = userRepository.findById(contactUpdate.getUserSenderId()).orElse(null);
					
					UserDetail userSenderDto = new UserDetail();
					BeanUtils.copyProperties(userSender, userSenderDto);
					
					UserDetail userReciverDto = new UserDetail();
					BeanUtils.copyProperties(userReciver, userReciverDto);
					
					ContactResponse contactResponse = new ContactResponse();
					BeanUtils.copyProperties(contactUpdate, contactResponse);
					contactResponse.setUserReciver(userReciverDto);
					contactResponse.setUserSender(userSenderDto);
					
					try {
						RabbitRequest rabbitResponse = new RabbitRequest();
						rabbitResponse.setAction(SCMConstant.SYSTEM_CONTACT);
						rabbitResponse.setData(contactResponse);

						rabbitSenderService.convertAndSendSysTem(rabbitResponse);
					} catch (Exception e) {
						e.getMessage();
					}

				}
				apiResponseEntity.setData(true);
				apiResponseEntity.setErrorList(null);
				apiResponseEntity.setStatus(1);
			} else {
				throw new Exception("Thao tác không hợp lệ");
			}
		} else {
			throw new Exception("Thao tác không hợp lệ");
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

		apiResponseEntity.setData(getContactResponse(userId));
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	public void processGetContactRequest(UserDetail userDetail, ApiResponseEntity apiResponseEntity) {
		Long userId = userDetail.getId();
		List<Contact> contact = contactRepository.findByUserId(userId, 1);
		List<Long> userFriendIds = contact.stream()
				.map(c -> c.getUserReciverId().equals(userId) ? c.getUserSenderId() : c.getUserReciverId())
				.collect(Collectors.toList());
		List<User> userFriends = (List<User>) userRepository.findAllById(userFriendIds);
		List<com.se1.userservice.domain.payload.ContactDto.User> userFriendResponse = userFriends.stream().map(uf -> {
			com.se1.userservice.domain.payload.ContactDto.User ud = new com.se1.userservice.domain.payload.ContactDto.User();
			BeanUtils.copyProperties(uf, ud);
			return ud;
		}).filter(res -> !Objects.isNull(res)).collect(Collectors.toList());

		List<Contact> contactFriendReciver = contact.stream().filter(t -> t.getStatus() == 1)
				.filter(c -> c.getUserReciverId().equals(userId)).collect(Collectors.toList());
		List<ContactDto> contactResponsesReciver = contactFriendReciver.stream().map(ct -> {
			ContactDto contactDto = new ContactDto();
			BeanUtils.copyProperties(ct, contactDto);
			contactDto.setTopicContactId(ct.getTopicId());
			Long userFriendId = ct.getUserReciverId().equals(userId) ? ct.getUserSenderId() : ct.getUserReciverId();
			com.se1.userservice.domain.payload.ContactDto.User userFriend = userFriendResponse.stream()
					.filter(u -> userFriendId.equals(u.getId())).findFirst().orElse(null);
			contactDto.setUserFriend(userFriend);

			return contactDto;
		}).filter(c -> c.getUserFriend() != null).collect(Collectors.toList());

		Map<String, List<ContactDto>> mapResult = new HashMap<>();
		mapResult.put("Reciver", contactResponsesReciver);
		apiResponseEntity.setData(mapResult);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);

	}

	boolean checkValidContact(String topicId) {
		boolean result = false;
		ApiResponseEntity userChatParentResult = (ApiResponseEntity) restTemplate.existChat(topicId);
		if (userChatParentResult.getStatus() == 1) {
			String apiResultStr;
			try {
				apiResultStr = objectMapper.writeValueAsString(userChatParentResult.getData());
				result = objectMapper.readValue(apiResultStr, Boolean.class);
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
		}

		return result;
	}

	public ApiResponseEntity processGetListContactForChat(UserDetail userDetail, ApiResponseEntity apiResponseEntity) {
		Long userId = userDetail.getId();

		List<ContactDto> contactDtos = getContactResponse(userId);
		List<ContactDtoForChat> contactDtoForChats = contactDtos.stream().map(cd -> {
			ContactDtoForChat contactDtoForChat = new ContactDtoForChat();
			BeanUtils.copyProperties(cd, contactDtoForChat);
			com.se1.userservice.domain.payload.ContactDtoForChat.User user = new com.se1.userservice.domain.payload.ContactDtoForChat.User();
			BeanUtils.copyProperties(cd.getUserFriend(), user);
			contactDtoForChat.setUserFriend(user);
			List<ContactDtoForChat.Chat> chats = getListChatByTopicId(cd.getTopicContactId());
			Collections.sort(chats, new Comparator<ContactDtoForChat.Chat>() {

				@Override
				public int compare(ContactDtoForChat.Chat o1, ContactDtoForChat.Chat o2) {
					Date lastTimeChat1 = o1.getCreateAt();
					Date lastTimeChat2 = o2.getCreateAt();
					return lastTimeChat1.compareTo(lastTimeChat2);
				}
				
			});
			Collections.reverse(chats);
			contactDtoForChat.setChats(chats);
			return contactDtoForChat;
		}).filter(c -> c.getUserFriend() != null).collect(Collectors.toList());
		Collections.sort(contactDtoForChats, new Comparator<ContactDtoForChat>() {

			@Override
			public int compare(ContactDtoForChat o1, ContactDtoForChat o2) {
				Date lastTimeChat1 = (o1.getChats() != null && o1.getChats().size() > 0) ? o1.getChats().get(0).getCreateAt() : o1.getCreateAt();
				Date lastTimeChat2 = (o2.getChats() != null && o2.getChats().size() > 0) ? o2.getChats().get(0).getCreateAt() : o2.getCreateAt();
				return lastTimeChat1.compareTo(lastTimeChat2);
			}
			
		});
		Collections.reverse(contactDtoForChats);
		
		apiResponseEntity.setData(contactDtoForChats);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
		return apiResponseEntity;
	}

	private List<Chat> getListChatByTopicId(String topicContactId) {
		List<Chat> result = null;
		ApiResponseEntity userChatParentResult = (ApiResponseEntity) restTemplate.getNewChat(topicContactId);
		if (userChatParentResult.getStatus() == 1) {
			String apiResultStr;
			try {
				apiResultStr = objectMapper.writeValueAsString(userChatParentResult.getData());
				result = objectMapper.readValue(apiResultStr, new TypeReference<List<Chat>>() {
				});
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}

	private List<ContactDto> getContactResponse(Long userId) {
		List<Contact> contact = contactRepository.findByUserId(userId, null);
		List<Contact> contactIsValid = contact.stream().filter(c -> c.getStatus() == 2).collect(Collectors.toList());
		List<Contact> contactValid = contact.stream().filter(c -> c.getStatus() != 2)
				.filter(c -> checkValidContact(c.getTopicId())).collect(Collectors.toList());
		List<Contact> contactMerge = new ArrayList<>(contactIsValid);
		contactMerge.addAll(contactValid);
		List<ContactDto> contactResponses = generatorContactResponse(userId, contactMerge, null);

		return contactResponses;
	}

	public void processGetListContact(Long userId, ApiResponseEntity apiResponseEntity) {
		apiResponseEntity.setData(getContactResponse(userId));
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	public void processFindContactByUserIdAndTopicIdGetListContact(Long userId, String topicId,
			ApiResponseEntity apiResponseEntity) {

		List<com.se1.userservice.domain.db.dto.ContactDto> contactDtos = contactMapper
				.findContactByUserIdAndTopicId(userId, topicId);
		List<Long> userFriendIds = contactDtos.stream()
				.map(c -> c.getUserReciverId().equals(userId) ? c.getUserSenderId() : c.getUserReciverId())
				.collect(Collectors.toList());
		List<User> userFriends = (List<User>) userRepository.findAllById(userFriendIds);
		List<com.se1.userservice.domain.payload.ContactDto.User> userFriendResponse = userFriends.stream().map(uf -> {
			com.se1.userservice.domain.payload.ContactDto.User ud = new com.se1.userservice.domain.payload.ContactDto.User();
			BeanUtils.copyProperties(uf, ud);
			return ud;
		}).filter(res -> !Objects.isNull(res)).collect(Collectors.toList());
		List<ContactDto> contactResponses = contactDtos.stream().map(ct -> {
			ContactDto contactDto = new ContactDto();
			BeanUtils.copyProperties(ct, contactDto);
			contactDto.setTopicContactId(ct.getTopicId());
			Long userFriendId = ct.getUserReciverId().equals(userId) ? ct.getUserSenderId() : ct.getUserReciverId();
			com.se1.userservice.domain.payload.ContactDto.User userFriend = userFriendResponse.stream()
					.filter(u -> userFriendId.equals(u.getId())).findFirst().orElse(null);
			contactDto.setUserFriend(userFriend);

			return contactDto;
		}).filter(c -> c.getUserFriend() != null).collect(Collectors.toList());

		apiResponseEntity.setData(contactResponses);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);

	}

	public List<ContactDto> generatorContactResponse(Long userId, List<Contact> contact, Integer status) {
		List<Long> userFriendIds = contact.stream()
				.map(c -> c.getUserReciverId().equals(userId) ? c.getUserSenderId() : c.getUserReciverId())
				.collect(Collectors.toList());
		List<User> userFriends = (List<User>) userRepository.findAllById(userFriendIds);
		List<com.se1.userservice.domain.payload.ContactDto.User> userFriendResponse = userFriends.stream().map(uf -> {
			com.se1.userservice.domain.payload.ContactDto.User ud = new com.se1.userservice.domain.payload.ContactDto.User();
			BeanUtils.copyProperties(uf, ud);
			return ud;
		}).filter(res -> !Objects.isNull(res)).collect(Collectors.toList());

		if (!Objects.isNull(status)) {
			List<Contact> contactFriendReciver = contact.stream().filter(t -> t.getStatus() == status)
					.filter(c -> c.getUserReciverId().equals(userId)).collect(Collectors.toList());

			List<ContactDto> contactResponsesReciver = contactFriendReciver.stream().map(ct -> {
				ContactDto contactDto = new ContactDto();
				BeanUtils.copyProperties(ct, contactDto);
				contactDto.setTopicContactId(ct.getTopicId());
				Long userFriendId = ct.getUserReciverId().equals(userId) ? ct.getUserSenderId() : ct.getUserReciverId();
				com.se1.userservice.domain.payload.ContactDto.User userFriend = userFriendResponse.stream()
						.filter(u -> userFriendId.equals(u.getId())).findFirst().orElse(null);
				contactDto.setUserFriend(userFriend);

				return contactDto;
			}).filter(c -> c.getUserFriend() != null).collect(Collectors.toList());

			return contactResponsesReciver;
		}

		List<ContactDto> contactResponsesReciver = contact.stream().map(ct -> {
			ContactDto contactDto = new ContactDto();
			BeanUtils.copyProperties(ct, contactDto);
			contactDto.setTopicContactId(ct.getTopicId());
			Long userFriendId = ct.getUserReciverId().equals(userId) ? ct.getUserSenderId() : ct.getUserReciverId();
			com.se1.userservice.domain.payload.ContactDto.User userFriend = userFriendResponse.stream()
					.filter(u -> userFriendId.equals(u.getId())).findFirst().orElse(null);
			contactDto.setUserFriend(userFriend);

			return contactDto;
		}).filter(c -> c.getUserFriend() != null).collect(Collectors.toList());

		return contactResponsesReciver;
	}

	public Object processGetListContact(UserDetail userDetail, ApiResponseEntity apiResponseEntity) {
		List<Contact> contact = contactRepository.findByUserId(userDetail.getId(), 2);
		List<Contact> contactIsValid = contact.stream().filter(c -> c.getStatus() == 2).collect(Collectors.toList());
		List<Contact> contactMerge = new ArrayList<>(contactIsValid);
		List<ContactDto> contactResponses = generatorContactResponse(userDetail.getId(), contactMerge, null);

		apiResponseEntity.setData(contactResponses);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
		
		return apiResponseEntity;
	}
}
