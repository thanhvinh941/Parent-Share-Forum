package com.se1.userservice.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.se1.userservice.db.read.RUserMapper;
import com.se1.userservice.model.User;
import com.se1.userservice.payload.UserDto;
import com.se1.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;
	private final RUserMapper rUserMapper;
	
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
		User userFind = null ;
		try {
			userFind = repository.findByEmail(email);
		} catch (DataAccessException e) {
			throw new Exception(e.getMessage());
		}
		return userFind;
	}
	
	public User findById(Long id) throws Exception {
		User userFind = null ;
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
			userDto.setBirthday(user.getBirthday());
			userDto.setEmailVerified(user.getEmailVerified());
			userDto.setRole(user.getRole().toString());
			userDto.setProvider(user.getProvider().toString());
			userDto.setStatus(user.getStatus());
			userDto.setPhoneNumber(user.getPhoneNumber());
			userDto.setIdentifyNo(user.getIdentifyNo());
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
				
			}else if( value instanceof Boolean) {
				valueStr = (boolean) value ? "1" : "0";
				mapResult = key + " = " + valueStr;	
			}else if( value instanceof String) {
				valueStr = String.format("%s", value);
				valueStr = valueStr.trim();
				String[] valueArray = valueStr.split(" ");
				if(valueArray.length > 1) {
					
					mapResult = key + " REGEXP ";
					mapResult += "'";
					mapResult += String.join("|", valueArray);
					mapResult += "'";
				}else {
					mapResult = key + " LIKE '%" + valueStr + "%'";
				}
			}else if(value instanceof Timestamp) {
				String timestampStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(value);
				valueStr = String.format("'%s'", timestampStr);
				mapResult = key + valueStr;	
			}else {
				mapResult = key + " = " + value.toString();	
			}
			return mapResult;
		}).collect(Collectors.toList());
		
		queryList = queryList.stream().filter(x ->{return !x.isBlank() && !x.equals(null) && !x.isEmpty();}).collect(Collectors.toList());
		query += String.join(" AND ", queryList);
		
		return query;
	}
	
	public static String camelToSnake(String str)
    {
        String result = "";
 
        char c = str.charAt(0);
        result = result + Character.toLowerCase(c);
 
        for (int i = 1; i < str.length(); i++) {
 
            char ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                result = result + '_';
                result
                    = result
                      + Character.toLowerCase(ch);
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
			userDto.setBirthday(user.getBirthday());
			userDto.setEmailVerified(user.getEmailVerified());
			userDto.setRole(user.getRole().toString());
			userDto.setProvider(user.getProvider().toString());
			userDto.setStatus(user.getStatus());
			userDto.setPhoneNumber(user.getPhoneNumber());
			userDto.setIdentifyNo(user.getIdentifyNo());
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
}
