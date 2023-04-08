package com.se1.userservice.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.se1.userservice.domain.model.Subscribe;
import com.se1.userservice.domain.model.User;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.SubscribeDto;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.repository.SubscriberRepository;
import com.se1.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscribeService {

	private final SubscriberRepository subscriberRepository;
	private final UserRepository userRepository;

	public void processDoSub(Long expertid, UserDetail userDetail, ApiResponseEntity apiResponseEntity)
			throws Exception {
		
		//TODO check expert
		Subscribe subscribe = new Subscribe();
		subscribe.setCreateAt(new Date());
		subscribe.setUserExpertId(expertid);
		subscribe.setUserSubscriberId(userDetail.getId());

		Subscribe subscribeSave = subscriberRepository.save(subscribe);
		if (Objects.isNull(subscribeSave)) {
			throw new Exception("Có lỗi xảy ra xin hãy thực hiện lại thao tác");
		}

		apiResponseEntity.setData(true);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	public void processDoUnSub(Long expertid, UserDetail userDetail, ApiResponseEntity apiResponseEntity) {
		List<Subscribe> subscribe = subscriberRepository.findByUserExpertIdAndUserSubscriberId(expertid,
				userDetail.getId());
		subscriberRepository.deleteById(subscribe.get(0).getId());
		apiResponseEntity.setData(true);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	public void processGetAllExpertSubscribe(Long id, ApiResponseEntity apiResponseEntity) {
		List<Subscribe> subscribe = subscriberRepository.findByUserSubscriberId(id);
		List<Long> expertIds = subscribe.stream().map(s->s.getUserExpertId()).collect(Collectors.toList());
		List<User> userFriends = (List<User>) userRepository.findAllById(expertIds);
		List<UserDetail> userFriendResponse = userFriends.stream().map(uf -> {
			UserDetail ud = new UserDetail();
			BeanUtils.copyProperties(uf, ud);
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
		apiResponseEntity.setData(subscribeDtos);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

}
