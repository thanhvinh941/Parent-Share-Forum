package com.se1.postservice.domain.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.common.SCMConstant;
import com.se1.postservice.domain.db.read.RPostMapper;
import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.entity.TopicTag;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.PostDto;
import com.se1.postservice.domain.payload.PostDto.PostTopicTag;
import com.se1.postservice.domain.payload.PostDto.PostUser;
import com.se1.postservice.domain.payload.PostRequest;
import com.se1.postservice.domain.payload.UserDetail;
import com.se1.postservice.domain.repository.PostRepository;
import com.se1.postservice.domain.repository.TopicTagRepository;
import com.se1.postservice.domain.service.PostService;
import com.se1.postservice.domain.util.CommonUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final TopicTagService topicTagService;
	private final TopicTagRepository topicTagRepository;
	private final ObjectMapper mapper;
	private final RPostMapper rPostMapper;
	
	SimpleDateFormat dateFormatYYYYMMDDHHMMSS = new SimpleDateFormat(SCMConstant.DATE_YYYYMMDD_HHMMSS);
	
	@Override
	public List<Post> saveAll(List<Post> records) {
		return postRepository.saveAll(records);
	}

	@Override
	public Post save(Post post) {
		return postRepository.save(post);
	}

	@Override
	public void processSavePost(PostRequest request, UserDetail detail, ApiResponseEntity apiResponseEntity) throws Exception {
		long userId = detail.getId();
		String userName = detail.getName();

		TopicTag topicTag = topicTagRepository.findById(request.getTopicTagId()).orElseGet(null);
		if (topicTag == null) {
			throw new Exception("Chủ đề không hợp lệ");
		}

		// TODO: validation post
		validation(request);

		Post postRegist = convertPostRequestToPostEntity(request, userId, userName);

		try {
			postRepository.save(postRegist);
		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}

	private void validation(PostRequest request) {

	}

	Post convertPostRequestToPostEntity(PostRequest request, long userId, String userName) {
		Post post = new Post();
		post.setUserId(userId);
		post.setTitle(request.getTitle());
		post.setSlug(request.getSlug());
		post.setSummary(request.getSummary());
		post.setContext(request.getContext());
		post.setHashTag(request.getHashTag());
		post.setTopicTagId(request.getTopicTagId());
		post.setCreateAt(new Date());
		post.setUpdateAt(new Date());
		post.setImageList(request.getImageList());

		return post;
	}

	PostDto convertPostEntityToPostDto(Post post, PostUser postUser, PostTopicTag postTopicTag) {

		PostDto postDto = new PostDto();
		postDto.setUser(postUser);
		postDto.setTitle(post.getTitle());
		postDto.setSlug(post.getSlug());
		postDto.setSummary(post.getSummary());
		postDto.setContext(post.getContext());
		postDto.setHashTag(post.getHashTag());
		postDto.setImageList(post.getImageList());
		postDto.setTopic(postTopicTag);

		return postDto;
	}

	@Override
	public void processFindUserPost(Long id, ApiResponseEntity apiResponseEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processFindPost(Map<String, Object> response) {
		String queryStr = generatorQueryAbsolute(response);
		Object post = rPostMapper.findPost(queryStr);
		
	}

	private String generatorQueryAbsolute(Map<String, Object> response) {
		String query = "";
		
		List<String> queryList = response.entrySet().stream().map(res ->{
			String key = CommonUtil.camelToSnake(res.getKey());
			Object value = res.getValue();
			String valueStr = null;
			String result = null;
			
			if(value instanceof List) {
				List<String> valueInstanceofList = (List<String>) ((List) value).stream().map(v -> CommonUtil.convertObjectToValueSql(v)
				).collect(Collectors.toList());
				valueStr += "(";
				valueStr = String.join(", ", valueInstanceofList);
				valueStr += ")";
				result = String.format(" %s in %s ", key, valueStr);
			}else {
				valueStr = CommonUtil.convertObjectToValueSql(value);
				result = String.format(" %s = %s ", key, valueStr);
			}
			
			return result;
		}).collect(Collectors.toList());
		
		query = String.join(" AND ", queryList);
		return query;
	}

}
