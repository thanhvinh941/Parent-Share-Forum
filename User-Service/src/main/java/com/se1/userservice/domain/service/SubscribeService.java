package com.se1.userservice.domain.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.se1.userservice.domain.common.SCMConstant;
import com.se1.userservice.domain.model.Subscribe;
import com.se1.userservice.domain.model.User;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.SubscribeDto;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.payload.response.RabbitRequest;
import com.se1.userservice.domain.payload.response.UserResponseForClient;
import com.se1.userservice.domain.payload.response.UserResponseForClient.ExpertInfo;
import com.se1.userservice.domain.repository.SubscriberRepository;
import com.se1.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscribeService {

	private final SubscriberRepository subscriberRepository;
	private final UserRepository userRepository;
	private final RatingService ratingService;
	private final RabbitSenderService rabbitSenderService;
	public void processDoSub(Long expertid, UserDetail userDetail, ApiResponseEntity apiResponseEntity)
			throws Exception {
		User user = userRepository.findExpertById(expertid);
		if(user == null) {
			throw new Exception("Chuyên gia không tồn tại hoặc người dùng không phải chuyên gia");
		}
		
		Subscribe subscribeFind = subscriberRepository.findByUserExpertIdAndUserSubscriberId(expertid, userDetail.getId());
		if(subscribeFind != null) {
			subscriberRepository.deleteById(subscribeFind.getId());
		}else {
			Subscribe subscribe = new Subscribe();
			subscribe.setCreateAt(new Date());
			subscribe.setUserExpertId(expertid);
			subscribe.setUserSubscriberId(userDetail.getId());
			Subscribe subscribeSave = subscriberRepository.save(subscribe);
			if (Objects.isNull(subscribeSave)) {
				throw new Exception("Có lỗi xảy ra xin hãy thực hiện lại thao tác");
			}
			
			sendNotify(subscribeSave);
		}
		
		apiResponseEntity.setData(true);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	private void sendNotify(Subscribe subscribeSave) {
		User userSender = userRepository.findById(subscribeSave.getUserExpertId()).orElse(null);
		User userReciver = userRepository.findById(subscribeSave.getUserSubscriberId()).orElse(null);
		
		UserDetail userSenderDto = new UserDetail();
		BeanUtils.copyProperties(userSender, userSenderDto);
		
		UserDetail userReciverDto = new UserDetail();
		BeanUtils.copyProperties(userReciver, userReciverDto);
		
		Map<String, Object> response = new HashMap<>();
		response.put("userId", subscribeSave.getUserSubscriberId());
		response.put("userReciver", userReciverDto);
		response.put("userSender", userSenderDto);
		
		RabbitRequest rabbitResponse = new RabbitRequest();
		rabbitResponse.setAction(SCMConstant.SYSTEM_SUBCRIBER);
		rabbitResponse.setData(response);
		
		rabbitSenderService.convertAndSendSysTem(rabbitResponse);
	}

	public Object processGetAllExpertSubscribe(Long id) {
		List<Subscribe> subscribe = subscriberRepository.findByUserSubscriberId(id);
		List<Long> expertIds = subscribe.stream().map(s->s.getUserExpertId()).collect(Collectors.toList());
		List<User> userFriends = (List<User>) userRepository.findAllById(expertIds);
		List<UserDetail> userFriendResponse = userFriends.stream().map(uf -> {
			UserDetail ud = new UserDetail();
			BeanUtils.copyProperties(uf, ud);
			ud.setRole(uf.getRole().name());
			return ud;
		}).collect(Collectors.toList());
		List<SubscribeDto> subscribeDtos = subscribe.stream().map(s->{
			SubscribeDto subscribeDto = new SubscribeDto();
			BeanUtils.copyProperties(s, subscribeDto);
			UserDetail userDetail = userFriendResponse.stream()
					.filter(u->u.getId().equals(s.getUserExpertId()))
					.findFirst().get();
			subscribeDto.setUserExpertId(userDetail);
			return subscribeDto;
		}).collect(Collectors.toList());
		return subscribeDtos;
	}

	public Object getAllSubscribeForExpert(Long id) {
		List<Subscribe> subscribe = subscriberRepository.findByUserExpertId(id);
		List<Long> userSubscriIds = subscribe.stream().map(s->s.getUserSubscriberId()).collect(Collectors.toList());
		List<User> userSubscris = (List<User>) userRepository.findAllById(userSubscriIds);
		List<UserResponseForClient> responseList = userSubscris.stream().filter(ul -> ul.getEmailVerified() && !ul.getDelFlg())
				.map(ul -> {
					Double rating = 0.0;
					Long ratingCount = null;
					Boolean isRate = false;
					if (ul.getIsExpert()) {
						rating = (Double) ratingService.getRatingByUserId(ul.getId(), null).get("rating");
						ratingCount = (Long) ratingService.getRatingByUserId(ul.getId(), null).get("count");
						isRate = (Boolean) ratingService.getRatingByUserId(ul.getId(), null).get("isRate");
					}
					UserResponseForClient userResponseDto = convertUserEntityToUserResponseForClient(ul, rating);
					return userResponseDto;
				}).collect(Collectors.toList());
		
		return responseList;
	}

	private UserResponseForClient convertUserEntityToUserResponseForClient(User userFind, double rating) {
		UserResponseForClient userResponseDto = new UserResponseForClient();
		BeanUtils.copyProperties(userFind, userResponseDto);

		if (userFind.getIsExpert()) {
			ExpertInfo expertInfo = new ExpertInfo();
			BeanUtils.copyProperties(userFind, expertInfo);
			expertInfo.setRating(rating);

			userResponseDto.setExpertInfo(expertInfo);
		}

		return userResponseDto;
	}
}
