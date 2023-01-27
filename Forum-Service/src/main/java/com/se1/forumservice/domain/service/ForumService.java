package com.se1.forumservice.domain.service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.forumservice.domain.db.read.RForumMapper;
import com.se1.forumservice.domain.db.write.WForumMapper;
import com.se1.forumservice.domain.entity.Forum;
import com.se1.forumservice.domain.repository.ForumRepository;
import com.se1.forumservice.util.Util;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ForumService {
	
	private final ForumRepository repository;
	private final RForumMapper rForumMapper;
	private final ObjectMapper objectMapper;
	private final WForumMapper wForumMapper;
	
	public Forum createdForum(Forum forumRequest) {

		Integer forumParentId = forumRequest.getForumParent();
		
		if(forumParentId != null) {
			Forum forumParent = repository.findById(forumParentId).get();
			forumRequest.setForumParentName(forumParent.getForumName());
		}
		
		Forum forumSave = repository.save(forumRequest);
		
		return forumSave;
	}

	public List<Forum> getAllForumParent() {
		List<Forum> forums = rForumMapper.getAllForumParent();
		return forums;
	}

	public Forum updateForum(Forum forumRequest) {
		Integer forumParentId = forumRequest.getForumParent();
		Integer forumRecordId = forumRequest.getForumId();
		if(forumParentId != null) {
			Forum forumParent = repository.findById(forumParentId).get();
			forumRequest.setForumParentName(forumParent.getForumName());
		}
		
		Map<String, Object> updateRecord = objectMapper.convertValue(forumRequest, Map.class);
		Map<String, Object> updateRecordFilter = updateRecord.entrySet().stream()
			.filter(entry -> entry.getValue() != null)
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		Function<Map.Entry<String, Object>, Map.Entry<String, Object>> mapCamelToSnake = entry -> {
			String key = entry.getKey();
			Object value = entry.getValue();
			return new AbstractMap.SimpleEntry<String, Object>(Util.camelToSnake(key), value);
		};
		Map<String, Object> updateRecordCamelToSnake = updateRecordFilter.entrySet().stream()
			.map(mapCamelToSnake)
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		String updateQuery = generatorUpdateQuery(updateRecordCamelToSnake);
		Integer forumUpdateId = wForumMapper.updateForum(updateQuery, forumRecordId);
		Forum forumUpdate = repository.findById(forumUpdateId).get();
		return forumUpdate;
	}
	

	public Boolean uDeleteForum(Integer forumId) {
		Integer forumDeleteId = wForumMapper.uDeleteForum(forumId);
		return forumDeleteId > 0 ? true : false;
	}
	
	private String generatorUpdateQuery(Map<String, Object> condition) {
		StringBuilder stringBuilder =  new StringBuilder();
		stringBuilder.append("SET ");
		Function<Map.Entry<String, Object>, String> mapToString = entry -> {
			String key = entry.getKey();
			Object value = entry.getValue();
			String valueStr = "";
			String mapResult = "";
			
			if( value instanceof String) {
				valueStr = String.format("'%s'", value);
				mapResult = key + " = " + valueStr;
			}else {
				mapResult = key + " = " + value.toString();	
			}
			return mapResult;
		};
		
		List<String> listCondition = condition.entrySet()
				.stream()
				.map(mapToString)
				.collect(Collectors.toList());
		
		String whereCondition =	String.join(", ", listCondition);
		stringBuilder.append(whereCondition);
		
		return stringBuilder.toString();
	}

}
