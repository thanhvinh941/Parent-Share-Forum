package com.se1.postservice.domain.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.domain.db.read.RTopicTagMapper;
import com.se1.postservice.domain.db.write.WTopicTagMapper;
import com.se1.postservice.domain.entity.TopicTag;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.CreateTopicTagRequest;
import com.se1.postservice.domain.payload.UpdateTopicTagRequest;
import com.se1.postservice.domain.payload.UserDetail;
import com.se1.postservice.domain.payload.dto.TopicTagResponse;
import com.se1.postservice.domain.repository.TopicTagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicTagService {

	private final TopicTagRepository topicTagRepository;
	private final ObjectMapper objectMapper;
	private final RTopicTagMapper rTopicTagMapper;
	
	private String createQueryUpdate(TopicTag propertyTag) throws JsonProcessingException {
		String queryUpdate = "";
		queryUpdate += " SET ";
		String topicTagStr = objectMapper.writeValueAsString(propertyTag);
		Map<String, Object> propertyTagLinked = objectMapper.readValue(topicTagStr, Map.class);
		
		List<String> queryUpdateList = propertyTagLinked.entrySet().stream().map(entry -> {
			String key = camelToSnake(entry.getKey());
			Object value = entry.getValue();
			String valueStr = "";
			String mapResult = "";
			if (key.equals("id") || Objects.isNull(value)) {
				
			}else if( value instanceof Boolean) {
				valueStr = (boolean) value ? "1" : "0";
				mapResult = key + " = " + valueStr;	
			}else if( value instanceof String) {
				valueStr = String.format("'%s'", value);
				mapResult = key + " = " + valueStr;
			}else if(value instanceof Timestamp) {
				String timestampStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(value);
				valueStr = String.format("'%s'", timestampStr);
				mapResult = key + valueStr;	
			}else {
				mapResult = key + " = " + value.toString();	
			}
			return mapResult;
		}).collect(Collectors.toList());
		
		queryUpdateList = queryUpdateList.stream().filter(x ->{return !x.isBlank() && !x.equals(null) && !x.isEmpty();}).collect(Collectors.toList());;
		queryUpdate += String.join(", ", queryUpdateList);
		queryUpdate += " WHERE id = " ;
		queryUpdate += (Integer) propertyTagLinked.get("id");
		
		return queryUpdate;
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

	public void processCreate(CreateTopicTagRequest topicTagRequest, UserDetail userDetail, ApiResponseEntity apiResponseEntity) throws Exception {
		String userRole = userDetail.getRole();
		if(userRole.equals("admin") || userRole.equals("expert")) {
			TopicTag topicTag = new TopicTag();
			BeanUtils.copyProperties(topicTagRequest, topicTag);
			
			topicTag.setCreateAt(new Date());
			topicTag.setUpdateAt(new Date());
			topicTag.setDelFlg(new Byte("0"));
			topicTag.setUserUpdate(userDetail.getEmail());
			topicTag.setUserCreate(userDetail.getEmail());
			
			topicTagRepository.save(topicTag);
			apiResponseEntity.setData(true);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		}else {
			throw new Exception("Không có quyền tạo thẻ thuộc tính");
		}
		
	}

	public void processUpdate(UpdateTopicTagRequest topicTagRequest, UserDetail userDetail,
			ApiResponseEntity apiResponseEntity) throws Exception {
		String userRole = userDetail.getRole();
		if(userRole.equals("admin") || userRole.equals("expert")) {
			TopicTag topicTag = topicTagRepository.findById(topicTagRequest.getId()).get();
			BeanUtils.copyProperties(topicTagRequest, topicTag, getNullPropertyNames(topicTagRequest));
			
			topicTag.setUpdateAt(new Date());
			topicTag.setUserUpdate(userDetail.getEmail());
			
			topicTagRepository.save(topicTag);
			apiResponseEntity.setData(true);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		}else {
			throw new Exception("Không có quyền cập nhật thẻ thuộc tính");
		}
	}
	
	public static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    Set<String> emptyNames = new HashSet<String>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	    }

	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}

	public void processGetAllTopicTag(String tagName,
			ApiResponseEntity apiResponseEntity) throws JsonMappingException, JsonProcessingException {
		List<TopicTagResponse> getAllTopicTag = rTopicTagMapper.findAllByName(tagName);
		apiResponseEntity.setData(getAllTopicTag);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}
}
