package com.se1.postservice.domain.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.domain.db.write.WTopicTagMapper;
import com.se1.postservice.domain.entity.TopicTag;
import com.se1.postservice.domain.payload.TopicTagRequest;
import com.se1.postservice.domain.repository.TopicTagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicTagService {

	private final TopicTagRepository topicTagRepository;
	private final ObjectMapper objectMapper;
	private final WTopicTagMapper wTopicTagMapper;
	
	public TopicTag findById(Integer propertyTagId) {
		return topicTagRepository.findById(propertyTagId).get();
	}

	public List<String> validation(TopicTagRequest propertyTagRequest) {
		// TODO Auto-generated method stub
		return List.of();
	}

	public TopicTag savePropertyTag(TopicTag propertyTag) {
		return topicTagRepository.save(propertyTag);
	}

	public boolean updateById(TopicTag propertyTag) throws JsonProcessingException {
		String updateCondition = createQueryUpdate(propertyTag);
		int updateStatus = wTopicTagMapper.updateTopicTag(updateCondition);
		return updateStatus > 0;
	}

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
}
