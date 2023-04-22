package com.se1.userservice.domain.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.userservice.domain.common.CommonUtil;
import com.se1.userservice.domain.common.SCMConstant;
import com.se1.userservice.domain.db.read.RUserMapper;
import com.se1.userservice.domain.db.write.WUserMapper;
import com.se1.userservice.domain.model.AuthProvider;
import com.se1.userservice.domain.model.FindAllUserRequest;
import com.se1.userservice.domain.model.User;
import com.se1.userservice.domain.model.UserDescription;
import com.se1.userservice.domain.model.UserRole;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.payload.UserDto;
import com.se1.userservice.domain.payload.UserResponseDto;
import com.se1.userservice.domain.payload.request.CreateUserRequest;
import com.se1.userservice.domain.payload.request.CreateUserRequest.Description;
import com.se1.userservice.domain.payload.response.UserResponseForClient;
import com.se1.userservice.domain.payload.response.UserResponseForClient.ExpertInfo;
import com.se1.userservice.domain.repository.UserDescriptionRepository;
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
	private final UserDescriptionRepository userDescriptionRepository;
	private final ObjectMapper objectMapper;

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

	public List<UserDto> find(Map<String, Object> findRequestMap) {

		String query = generateQueryContionFind(findRequestMap);
		List<User> userList = rUserMapper.find(query);

		List<UserDto> userDtos = userList.stream().map(user -> {
			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setName(user.getName());
			userDto.setEmail(user.getEmail());
			userDto.setEmailVerified(user.getEmailVerified());
			userDto.setRole(user.getRole().toString());
			userDto.setProvider(user.getProvider().toString());
			userDto.setStatus(user.getStatus());
			userDto.setPhoneNumber(user.getPhoneNumber());
			userDto.setIsExpert(user.getIsExpert());
			userDto.setDelFlg(user.getDelFlg());
			userDto.setCreateAt(user.getCreateAt());
			userDto.setUpdateAt(user.getUpdateAt());

			return userDto;
		}).collect(Collectors.toList());

		return userDtos;
	}

	private String generateQueryContionFind(Map<String, Object> findRequestMap) {
		String query = "";
		query += " WHERE ";

		List<String> queryList = findRequestMap.entrySet().stream().map(entry -> {
			String key = camelToSnake(entry.getKey());
			Object value = entry.getValue();

			String valueStr = "";
			String mapResult = "";

			if (Objects.isNull(value)) {

			} else if (value instanceof Boolean) {
				valueStr = (boolean) value ? "1" : "0";
				mapResult = key + " = " + valueStr;
			} else if (value instanceof String) {
				valueStr = String.format("%s", value);
				valueStr = valueStr.trim();
				String[] valueArray = valueStr.split(" ");
				if (valueArray.length > 1) {

					mapResult = key + " REGEXP ";
					mapResult += "'";
					mapResult += String.join("|", valueArray);
					mapResult += "'";
				} else {
					mapResult = key + " LIKE '%" + valueStr + "%'";
				}
			} else if (value instanceof Timestamp) {
				String timestampStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(value);
				valueStr = String.format("'%s'", timestampStr);
				mapResult = key + valueStr;
			} else {
				mapResult = key + " = " + value.toString();
			}
			return mapResult;
		}).collect(Collectors.toList());

		queryList = queryList.stream().filter(x -> {
			return !x.isBlank() && !x.equals(null) && !x.isEmpty();
		}).collect(Collectors.toList());
		query += String.join(" AND ", queryList);

		return query;
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

	public List<UserDto> findByName(String name) {
		String query = generateQueryContionFindByName(name);
		List<User> userList = rUserMapper.find(query);
		List<UserDto> userDtos = userList.stream().map(user -> {
			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setName(user.getName());
			userDto.setEmail(user.getEmail());
			userDto.setEmailVerified(user.getEmailVerified());
			userDto.setRole(user.getRole().toString());
			userDto.setProvider(user.getProvider().toString());
			userDto.setStatus(user.getStatus());
			userDto.setPhoneNumber(user.getPhoneNumber());
			userDto.setIsExpert(user.getIsExpert());
			userDto.setDelFlg(user.getDelFlg());
			userDto.setCreateAt(user.getCreateAt());
			userDto.setUpdateAt(user.getUpdateAt());

			return userDto;
		}).collect(Collectors.toList());

		return userDtos;
	}

	private String generateQueryContionFindByName(String name) {
		String query = "";
		query += " WHERE ";
		String[] nameArray = name.trim().split(" ");
		String nameQuery = "('" + String.join("|", nameArray) + "')";
		query += " name REGEXP " + nameQuery;
		query += " OR ";
		query += " email REGEXP " + nameQuery;
		return query;
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
			List<UserDescription> userDescription = userFind.getDescription();
			Map<String, List<String>> descriptions = userDescription.stream()
					.collect(Collectors.groupingBy(UserDescription::getTitle, TreeMap::new,
							Collectors.mapping(UserDescription::getDescription, Collectors.toList())));

			ExpertInfo expertInfo = new ExpertInfo();
			BeanUtils.copyProperties(userFind, expertInfo);
			expertInfo.setRating(rating);
			expertInfo.setDescriptions(descriptions);

			userResponseDto.setExpertInfo(expertInfo);
		}

		return userResponseDto;
	}

	public void processFindUser(Map<String, Object> findRequestMap, ApiResponseEntity apiResponseEntity) {

		String query = generateQueryContionFind(findRequestMap);
		List<User> userList = rUserMapper.find(query);
		List<UserResponseDto> responseList = userList.stream().map(ul -> {
			double rating = 0;
			if (ul.getIsExpert()) {
				rating = ratingService.getRatingByUserId(ul.getId());
			}
			UserResponseDto userResponseDto = convertUserEntityToUserResponseEntity(ul, rating);
			return userResponseDto;
		}).collect(Collectors.toList());

		apiResponseEntity.setData(responseList);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	public void processFindByName(String name, ApiResponseEntity apiResponseEntity) {
		String query = generateQueryContionFindByName(name);
		List<User> userList = rUserMapper.find(query);
		List<UserResponseDto> responseList = userList.stream().filter(ul -> ul.getEmailVerified() && !ul.getDelFlg())
				.map(ul -> {
					double rating = 0;
					if (ul.getIsExpert()) {
						rating = ratingService.getRatingByUserId(ul.getId());
					}
					UserResponseDto userResponseDto = convertUserEntityToUserResponseEntity(ul, rating);
					return userResponseDto;
				}).collect(Collectors.toList());

		apiResponseEntity.setData(responseList);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	public void processUpdateStatus(Long id, Integer status, ApiResponseEntity apiResponseEntity) {
		wUserMapper.updateUserStatus(id, status);
	}

	public void processRegistExpert(UserDetail userDetail, String imageLicenceBase64,
			ApiResponseEntity apiResponseEntity) throws Exception {
		Long userId = userDetail.getId();
		Boolean isExpert = userDetail.getIsExpert();
		if (isExpert) {
			throw new Exception("Người dùng đã là chuyên gia");
		}

		String[] imageBase64 = imageLicenceBase64.split(",");

		boolean isImage = checkImage(imageBase64[0].split("/")[1]);

		if (!isImage) {
			throw new Exception("Chỉ nhận file hình ảnh hoặc file pdf");
		}

		String licenceFileName = restTemplateClient.uploadFile(imageLicenceBase64);
		if (Objects.isNull(licenceFileName)) {
			throw new Exception("Lưu bằng cấp thất bại. Xin hãy thử lại !!!");
		}

		wUserMapper.updateLicenceImageToUser(licenceFileName, userId);

		apiResponseEntity.setData(true);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
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

		if (user.getDescription() != null) {
			List<UserDescription> userDescriptions = userDescriptionRepository.saveAll(user.getDescription());
			user.setDescription(userDescriptions);
		}

		User userSave = repository.save(user);

		apiResponseEntity.setData(Map.of("userId", userSave.getId(), "role", userSave.getRole()));
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);

		return null;
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
		if (requestRole.equals("admin")) {

		} else {
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
			List<Description> description = request.getExpertInfo().getDescription();
			List<List<UserDescription>> userDescriptions = description.stream().filter(des -> {
				return (!Objects.isNull(des.getTitle()) || !Objects.isNull(des.getDescription()));
			}).map(des -> {
				List<UserDescription> userDescriptionsList = new ArrayList<>();
				for (String descriptionStr : des.getDescription()) {
					UserDescription userDescription = new UserDescription();
					userDescription.setTitle(des.getTitle());
					userDescription.setDescription(descriptionStr);

					userDescriptionsList.add(userDescription);
				}

				return userDescriptionsList;
			}).collect(Collectors.toList());

			List<UserDescription> flat = userDescriptions.stream().flatMap(List::stream).collect(Collectors.toList());
			user.setDescription(flat);
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

	public void findAll(FindAllUserRequest request, UserDetail userDetail, ApiResponseEntity apiResponseEntity) {
		Long userId = userDetail.getId();

		String nameQuery = !request.getName().isEmpty() ? " name like '% " + request.getName() + " %'" : "";
		String emailQuery = !request.getEmail().isEmpty() ? " email like '% " + request.getEmail() + " %'" : "";
		String providerQuery = (!Objects.isNull(request.getProvider()) && request.getProvider().size() > 0)
				? " provider in (" + String.join(", ",
						request.getProvider().stream().map(p -> String.format("'%s'", p)).collect(Collectors.toList()))
						+ ")"
				: "";
		String roleQuery = (!Objects.isNull(request.getRole()) && request.getRole().size() > 0) 
				? " role in (" + String.join(", ",
						request.getRole().stream().map(r -> String.format("'%s'", r)).collect(Collectors.toList()))
				+ ")" : "";

		List<String> mergeQuery = List.of(nameQuery, emailQuery, providerQuery, roleQuery);

		List<User> allUser = rUserMapper.findAll(mergeQuery, userId);
		apiResponseEntity.setData(allUser);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}
}
