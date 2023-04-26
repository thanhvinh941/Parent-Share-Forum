package com.se1.userservice.domain.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.se1.userservice.domain.common.SCMConstant;
import com.se1.userservice.domain.db.read.RUserMapper;
import com.se1.userservice.domain.db.write.WUserMapper;
import com.se1.userservice.domain.model.AuthProvider;
import com.se1.userservice.domain.model.FindAllUserRequest;
import com.se1.userservice.domain.model.User;
import com.se1.userservice.domain.model.UserRole;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.payload.UserResponseDto;
import com.se1.userservice.domain.payload.request.CreateUserRequest;
import com.se1.userservice.domain.payload.request.UpdateUserRequest;
import com.se1.userservice.domain.payload.response.UserResponseForClient;
import com.se1.userservice.domain.payload.response.UserResponseForClient.ExpertInfo;
import com.se1.userservice.domain.repository.UserRepository;
import com.se1.userservice.domain.restClient.SystemServiceRestTemplateClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;
	private final RUserMapper rUserMapper;
	private final RatingService ratingService;
	private final SystemServiceRestTemplateClient restTemplateClient;
	private final WUserMapper wUserMapper;
	private final PasswordEncoder passwordEncoder;

	public User save(User user) throws Exception {

		User userSave = null;
		try {
			userSave = repository.save(user);
		} catch (DataAccessException e) {
			throw new Exception(e.getMessage());
		}
		return userSave;
	}

	public User findByEmail(String email) throws Exception {
		User userFind = null;
		try {
			userFind = repository.findByEmail(email);
		} catch (DataAccessException e) {
			throw new Exception(e.getMessage());
		}
		return userFind;
	}

	public User findById(Long id) throws Exception {
		User userFind = null;
		try {
			userFind = repository.findById(id).get();
		} catch (DataAccessException e) {
			throw new Exception(e.getMessage());
		}
		return userFind;
	}

	public static String camelToSnake(String str) {
		String result = "";

		char c = str.charAt(0);
		result = result + Character.toLowerCase(c);

		for (int i = 1; i < str.length(); i++) {

			char ch = str.charAt(i);
			if (Character.isUpperCase(ch)) {
				result = result + '_';
				result = result + Character.toLowerCase(ch);
			}

			else {
				result = result + ch;
			}
		}

		return result;
	}

	public void processFindUserByEmail(String email, ApiResponseEntity apiResponseEntity) throws Exception {
		User userFind = null;
		try {
			userFind = repository.findByEmail(email);
		} catch (DataAccessException e) {
			throw new Exception(e.getMessage());
		}

		validationUser(userFind);

		generatorResponse(userFind, apiResponseEntity);
	}

	public void processFindUserById(Long id, ApiResponseEntity apiResponseEntity) throws Exception {
		User userFind = null;
		try {
			userFind = repository.findById(id).orElse(null);
		} catch (DataAccessException e) {
			throw new Exception(e.getMessage());
		}

		validationUser(userFind);

		generatorResponseForClient(userFind, apiResponseEntity);
	}

	private void validationUser(User userFind) throws Exception {
		if (userFind == null) {
			throw new Exception("Không tìm thấy người dùng hợp lệ");
		}

		if (userFind.getDelFlg()) {
			throw new Exception("Tài khoảng người dùng đã bị xóa");
		}

		if (!userFind.getEmailVerified()) {
			throw new Exception("Tài khoảng chưa được xác thực");
		}
	}

	private UserResponseDto convertUserEntityToUserResponseEntity(User user, Double rating) {
		UserResponseDto userResponseDto = null;
		if (user != null) {
			userResponseDto = new UserResponseDto();
			userResponseDto.setId(user.getId());
			userResponseDto.setEmail(user.getEmail());
			userResponseDto.setEmailVerified(user.getEmailVerified());
			userResponseDto.setImageUrl(user.getImageUrl());
			userResponseDto.setName(user.getName());
			userResponseDto.setPassword(user.getPassword());
			userResponseDto.setProvider(user.getProvider());
			userResponseDto.setProviderId(user.getProviderId());
			userResponseDto.setRole(user.getRole().name());
			userResponseDto.setStatus(user.getStatus());
			userResponseDto.setRating(rating);
			userResponseDto.setIsExpert(user.getIsExpert());
			userResponseDto.setTopicId(user.getTopicId());
		}

		return userResponseDto;
	}

	private void generatorResponse(User userFind, ApiResponseEntity apiResponseEntity) {
		double rating = 0;
		if (userFind.getIsExpert()) {
			rating = ratingService.getRatingByUserId(userFind.getId());
		}
		UserResponseDto userResponseDto = convertUserEntityToUserResponseEntity(userFind, rating);

		apiResponseEntity.setData(userResponseDto);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	private void generatorResponseForClient(User userFind, ApiResponseEntity apiResponseEntity) {
		double rating = 0;
		if (userFind.getIsExpert()) {
			rating = ratingService.getRatingByUserId(userFind.getId());
		}
		UserResponseForClient userResponseDto = convertUserEntityToUserResponseForClient(userFind, rating);

		apiResponseEntity.setData(userResponseDto);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
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

	public void processFindByName(Long userId, String name, ApiResponseEntity apiResponseEntity, Integer offset) {
		List<User> users = repository.findByName(name, userId);
		List<UserResponseForClient> responseList = users.stream().filter(ul -> ul.getEmailVerified() && !ul.getDelFlg())
				.map(ul -> {
					double rating = 0;
					if (ul.getIsExpert()) {
						rating = ratingService.getRatingByUserId(ul.getId());
					}
					UserResponseForClient userResponseDto = convertUserEntityToUserResponseForClient(ul, rating);
					return userResponseDto;
				}).collect(Collectors.toList());

		apiResponseEntity.setData(responseList);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	public void processUpdateStatus(Long id, Integer status, ApiResponseEntity apiResponseEntity) {
		wUserMapper.updateUserStatus(id, status);
	}

	private boolean checkImage(String extension) {
		return extension.equals("jpeg;base64") || extension.equals("png;base64") || extension.equals("pdf;base64")
				|| extension.equals("jpg;base64");
	}

	public Map<Integer, String> processcreate(CreateUserRequest request, UserDetail userDetail,
			ApiResponseEntity apiResponseEntity) throws Exception {
		Map<Integer, String> error = new HashMap<>();
		String userRole = userDetail.getRole();
		if (!userRole.equals("admin")) {
			error.put(403, "Hành động không được phép");
			return error;
		}

		User user = generatorCreateEntity(request, error);
		if (error.size() > 0) {
			return error;
		}

		User userSave = repository.save(user);

		apiResponseEntity.setData(Map.of("userId", userSave.getId(), "role", userSave.getRole()));
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);

		return error;
	}

	private User generatorCreateEntity(CreateUserRequest request, Map<Integer, String> error) throws Exception {
		User user = new User();
		String requestRole = request.getRole();
		Boolean isExpert = requestRole.equals("expert");
		if (!requestRole.equals("admin") && !isExpert) {
			error.put(200, "Chỉ được phép tạo chuyên gia hoặc admin");
		}

		String imageUrl = request.getImageUrlBase64();
		if (isExpert && Objects.isNull(imageUrl)) {
			error.put(200, "Chuyên gia cần phải có hình ảnh");
		}

		BeanUtils.copyProperties(request, user);
		if (!requestRole.equals("admin")) {
			String[] imageBase64 = imageUrl.split(",");
			boolean isImage = checkImage(imageBase64[0].split("/")[1]);

			if (!isImage) {
				error.put(200, "Chỉ nhận file hình ảnh hoặc file pdf");
			}

			user.setImageUrl(restTemplateClient.uploadFile(imageUrl));
			com.se1.userservice.domain.payload.request.CreateUserRequest.ExpertInfo expertInfo = request
					.getExpertInfo();
			if (Objects.isNull(expertInfo)) {
				error.put(200, "Thông tin chuyên gia không được trống");
			}

			if (Objects.isNull(expertInfo.getDescription()) || expertInfo.getDescription().size() < 0) {
				error.put(200, "Mô tả chuyên gia không được trống");
			}

			BeanUtils.copyProperties(expertInfo, user);
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreateAt(new Date());
		user.setUpdateAt(new Date());
		user.setLastTime(new Date());
		user.setEmailVerified(true);
		user.setProvider(AuthProvider.local);
		user.setStatus(SCMConstant.DEL_FLG_OFF);
		user.setDelFlg(false);
		user.setTopicId(UUID.randomUUID().toString());
		user.setRole(UserRole.valueOf(requestRole));
		user.setIsExpert(isExpert);

		return user;

	}

	public void findAll(FindAllUserRequest request, UserDetail userDetail, Integer offset,
			ApiResponseEntity apiResponseEntity) {
		Long userId = userDetail.getId();

		String userQuery = String.format(" id != %d", userId);
		String nameQuery = !request.getName().isEmpty() ? " name like '% " + request.getName() + " %'" : "";
		String emailQuery = !request.getEmail().isEmpty() ? " email like '% " + request.getEmail() + " %'" : "";
		String providerQuery = (!Objects.isNull(request.getProvider()) && request.getProvider().size() > 0)
				? " provider in (" + String.join(", ",
						request.getProvider().stream().map(p -> String.format("'%s'", p)).collect(Collectors.toList()))
						+ ")"
				: "";
		String roleQuery = (!Objects.isNull(request.getRole()) && request.getRole().size() > 0) ? " role in ("
				+ String.join(", ",
						request.getRole().stream().map(r -> String.format("'%s'", r)).collect(Collectors.toList()))
				+ ")" : "";

		List<String> mergeQuery = List.of(userQuery, nameQuery, emailQuery, providerQuery, roleQuery);

		List<User> allUser = rUserMapper.findAll(mergeQuery, offset);
		apiResponseEntity.setData(allUser);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	public void update(UpdateUserRequest request, UserDetail userDetail, ApiResponseEntity apiResponseEntity)
			throws Exception {
		Boolean isCurrenUser = userDetail.getId().equals(request.getId());
		if (!userDetail.getRole().equals("admin") && 
				!isCurrenUser) {
			throw new Exception("Hành đông không cho phép");
		}
		Optional<User> userFind = repository.findById(request.getId());
		if (userFind.isEmpty()) {
			throw new Exception("Người dùng không tồn tại");
		}

		User userOld = userFind.get();
		if(request.getName() != null) {
			userOld.setName(request.getName());
		}
		String imageUrl = "";
		if (request.getImageUrlBase64() != null) {
			imageUrl = restTemplateClient.uploadFile(request.getImageUrlBase64());
		}
		if(imageUrl != null && !imageUrl.isEmpty() && !imageUrl.isBlank()) {
			userOld.setImageUrl(imageUrl);
		}
		if(request.getPassword()!= null) {
			userOld.setPassword(passwordEncoder.encode(request.getPassword()));
		}
		if(userOld.getRole().name().equals("expert") && request.getExpertInfo() != null){
			com.se1.userservice.domain.payload.request.UpdateUserRequest.ExpertInfo expertInfo = request.getExpertInfo();
			if(expertInfo.getPhoneNumber() != null) {
				userOld.setPhoneNumber(expertInfo.getPhoneNumber());
			}
			if(expertInfo.getJobTitle() != null) {
				userOld.setJobTitle(expertInfo.getJobTitle());
			}
			if(expertInfo.getSpecialist() != null) {
				userOld.setSpecialist(expertInfo.getSpecialist());
			}
			if(expertInfo.getWorkPlace() != null) {
				userOld.setWorkPlace(expertInfo.getWorkPlace());
			}
		}
		
		User userUpdate = repository.save(userOld);
		if(userUpdate != null) {
			apiResponseEntity.setData(userUpdate.getId());
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		}

	}

	public void findAllExpert(UserDetail userDetail, Integer offset, ApiResponseEntity apiResponseEntity) {
		List<User> users = repository.findAllByRole(UserRole.expert);
		List<UserResponseForClient> responseList = users.stream().filter(ul -> ul.getEmailVerified() && !ul.getDelFlg())
				.map(ul -> {
					double rating = 0;
					if (ul.getIsExpert()) {
						rating = ratingService.getRatingByUserId(ul.getId());
					}
					UserResponseForClient userResponseDto = convertUserEntityToUserResponseForClient(ul, rating);
					return userResponseDto;
				}).collect(Collectors.toList());

		apiResponseEntity.setData(responseList);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
		
	}
}
