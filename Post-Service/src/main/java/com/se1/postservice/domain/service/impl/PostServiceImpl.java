package com.se1.postservice.domain.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.common.SCMConstant;
import com.se1.postservice.domain.db.read.RPostMapper;
import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.entity.TopicTag;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.ContactDto;
import com.se1.postservice.domain.payload.GetPostResponseDto;
import com.se1.postservice.domain.payload.GetPostResponseDto.User;
import com.se1.postservice.domain.payload.PostDto;
import com.se1.postservice.domain.payload.PostDto.PostTopicTag;
import com.se1.postservice.domain.payload.PostDto.PostUser;
import com.se1.postservice.domain.payload.PostRequest;
import com.se1.postservice.domain.payload.SubscribeDto;
import com.se1.postservice.domain.payload.UserDetail;
import com.se1.postservice.domain.repository.PostRepository;
import com.se1.postservice.domain.repository.TopicTagRepository;
import com.se1.postservice.domain.service.PostService;
import com.se1.postservice.domain.util.CommonUtil;
import com.se1.postservice.domain.util.SystemServiceRestTemplateClient;
import com.se1.postservice.domain.util.UserServiceRestTemplateClient;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final TopicTagRepository topicTagRepository;
	private final RPostMapper rPostMapper;
	private final ObjectMapper objectMapper;
	private final UserServiceRestTemplateClient restTemplateClient;
	private final SystemServiceRestTemplateClient serviceRestTemplateClient;
	SimpleDateFormat dateFormatYYYYMMDDHHMMSS = new SimpleDateFormat(SCMConstant.DATE_YYYYMMDD_HHMMSS);

	@Override
	public Post save(Post post) {
		return postRepository.save(post);
	}

	@Override
	public void processSavePost(PostRequest request, UserDetail detail, ApiResponseEntity apiResponseEntity)
			throws Exception {
		long userId = detail.getId();
		String userName = detail.getName();

		TopicTag topicTag = topicTagRepository.findById(request.getTopicTagId()).orElseGet(null);
		if (topicTag == null) {
			throw new Exception("Chủ đề không hợp lệ");
		}

		// TODO: validation post
		validation(request);

		List<String> imageList = request.getImageList();
		List<String> imageNameList = new ArrayList<>();
		for (String image : imageList) {
			imageNameList.add(getFileName(image));
		}
		request.setImageList(imageNameList);

		Post postRegist = convertPostRequestToPostEntity(request, userId, userName);

		try {
			postRepository.save(postRegist);
			apiResponseEntity.setData(true);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	private void validation(PostRequest request) {

	}

	private String getFileName(String file) {
		return serviceRestTemplateClient.uploadFile(file);
	}

	Post convertPostRequestToPostEntity(PostRequest request, long userId, String userName) {
		Post post = new Post();
		BeanUtils.copyProperties(request, post);
		post.setSummary(request.getTitle());
		post.setCreateAt(new Date());
		post.setUpdateAt(new Date());
		post.setUserId(userId);
		post.setStatus(1);
		return post;
	}

	PostDto convertPostEntityToPostDto(Post post, PostUser postUser, PostTopicTag postTopicTag) {

		PostDto postDto = new PostDto();
		BeanUtils.copyProperties(post, postDto);
		postDto.setUser(postUser);
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

		List<String> queryList = response.entrySet().stream().map(res -> {
			String key = CommonUtil.camelToSnake(res.getKey());
			Object value = res.getValue();
			String valueStr = null;
			String result = null;

			if (value instanceof List) {
				List<String> valueInstanceofList = (List<String>) ((List) value).stream()
						.map(v -> CommonUtil.convertObjectToValueSql(v)).collect(Collectors.toList());
				valueStr += "(";
				valueStr = String.join(", ", valueInstanceofList);
				valueStr += ")";
				result = String.format(" %s in %s ", key, valueStr);
			} else {
				valueStr = CommonUtil.convertObjectToValueSql(value);
				result = String.format(" %s = %s ", key, valueStr);
			}

			return result;
		}).collect(Collectors.toList());

		query = String.join(" AND ", queryList);
		return query;
	}

	@Override
	public void processGetByTitle(String title, UserDetail detail, ApiResponseEntity apiResponseEntity) {
		List<Post> posts = postRepository.findByTitleContaining(title);
		List<GetPostResponseDto> getPostResponseDtos = posts.stream().map(p -> {
			Long postId = p.getId();
			GetPostResponseDto postResponseDto = new GetPostResponseDto();
			BeanUtils.copyProperties(p, postResponseDto);
			postResponseDto.setUser(getUSerPost(p.getUserId()));
			postResponseDto.setTopicTag(getTopicTag(p.getTopicTagId()));
			postResponseDto.setLikeCount(likeCountPost(postId));
			postResponseDto.setDislikeCount(disLikeCount(postId));
			postResponseDto.setCommentCount(commentCount(postId));
			postResponseDto.setShareCount(shareCount(postId));

			return postResponseDto;
		}).collect(Collectors.toList());
		apiResponseEntity.setData(getPostResponseDtos);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	private GetPostResponseDto.User getUSerPost(Long userId) {
		GetPostResponseDto.User user = new User();
		ApiResponseEntity userResult = (ApiResponseEntity) restTemplateClient.findById(userId);
		if (userResult.getStatus() == 1) {
			String apiResultStr;
			try {
				apiResultStr = objectMapper.writeValueAsString(userResult.getData());
				user = objectMapper.readValue(apiResultStr, GetPostResponseDto.User.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	private GetPostResponseDto.TopicTag getTopicTag(Integer topicTagId) {
		TopicTag tag = topicTagRepository.findById(topicTagId).get();
		GetPostResponseDto.TopicTag result = new com.se1.postservice.domain.payload.GetPostResponseDto.TopicTag();
		BeanUtils.copyProperties(tag, result);
		return result;
	}

	private List<GetPostResponseDto.TopicTag> getTopicTag(List<Integer> topicTagId) {
		List<TopicTag> tagList = (List<TopicTag>) topicTagRepository.findAllById(topicTagId);
		List<GetPostResponseDto.TopicTag> resultList = tagList.stream().map(tag -> {

			GetPostResponseDto.TopicTag result = new com.se1.postservice.domain.payload.GetPostResponseDto.TopicTag();
			BeanUtils.copyProperties(tag, result);
			return result;
		}).collect(Collectors.toList());
		return resultList;
	}

	private Integer likeCountPost(Long postId) {
		return 0;
	}

	private Integer commentCount(Long postId) {
		return 0;
	}

	private Integer disLikeCount(Long postId) {
		return 0;
	}

	private Integer shareCount(Long postId) {
		return 0;
	}

	@Override
	public void processGetAllPost(UserDetail detail, ApiResponseEntity apiResponseEntity) {
		List<Post> posts = postRepository.findByUserId(detail.getId());
		List<GetPostResponseDto> getPostResponseDtos = posts.stream().map(p -> {
			Long postId = p.getId();
			GetPostResponseDto postResponseDto = new GetPostResponseDto();
			BeanUtils.copyProperties(p, postResponseDto);
			postResponseDto.setUser(getUSerPost(p.getUserId()));
			postResponseDto.setTopicTag(getTopicTag(p.getTopicTagId()));
			postResponseDto.setLikeCount(likeCountPost(postId));
			postResponseDto.setDislikeCount(disLikeCount(postId));
			postResponseDto.setCommentCount(commentCount(postId));
			postResponseDto.setShareCount(shareCount(postId));

			return postResponseDto;
		}).collect(Collectors.toList());
		apiResponseEntity.setData(getPostResponseDtos);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	@Override
	public void findPostById(Long postId, ApiResponseEntity apiResponseEntity) throws Exception {
		Optional<Post> post = postRepository.findById(postId);
		if (post.isEmpty()) {
			throw new Exception("Bài viết không tồn tại");
		} else {
			GetPostResponseDto postResponseDto = new GetPostResponseDto();
			BeanUtils.copyProperties(post.get(), postResponseDto);
			postResponseDto.setUser(getUSerPost(post.get().getUserId()));
			postResponseDto.setTopicTag(getTopicTag(post.get().getTopicTagId()));
			postResponseDto.setLikeCount(likeCountPost(postId));
			postResponseDto.setDislikeCount(disLikeCount(postId));
			postResponseDto.setCommentCount(commentCount(postId));
			postResponseDto.setShareCount(shareCount(postId));

			apiResponseEntity.setData(postResponseDto);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		}
	}

	@Override
	public void findAllPost(UserDetail userDetail, ApiResponseEntity apiResponseEntity, int offset)
			throws JsonMappingException, JsonProcessingException {
		Long userId = userDetail.getId();
		List<ContactDto> contactDtos = restTemplateClient.getListFriend(userId);
		List<SubscribeDto> subscribeDtos = restTemplateClient.getAllExpertSubscribe(userId);
		List<Long> userFriendId = contactDtos.stream().map(c -> c.getUserFriend().getId()).collect(Collectors.toList());
		List<Long> listExpertId = subscribeDtos.stream().map(s -> s.getUserExpertId().getId())
				.collect(Collectors.toList());
		List<Long> allIdUserId = new ArrayList<>(userFriendId);
		allIdUserId.addAll(listExpertId);
		allIdUserId.add(userId);

		List<Long> allIdUserIdDistinct = allIdUserId.stream().distinct().collect(Collectors.toList());

		List<com.se1.postservice.domain.db.dto.PostDto> allPost = rPostMapper.findAllPostByUserId(
				String.join(", ", allIdUserIdDistinct.stream().map(m -> m.toString()).collect(Collectors.toList())),
				offset, userId);
		List<Integer> topicTagIds = allPost.stream().map(ap -> ap.getTopicTagId()).collect(Collectors.toList());
		List<GetPostResponseDto.TopicTag> listTopicTagResponse = getTopicTag(topicTagIds);
		List<GetPostResponseDto> getPostResponseDtos = allPost.stream().map(p -> {
			GetPostResponseDto postResponseDto = new GetPostResponseDto();
			postResponseDto.setImageList(List.of(p.getImageList()));
			BeanUtils.copyProperties(p, postResponseDto);
			postResponseDto.setIsLike((p.getIsLike() != null && p.getIsLike().equals(1)) ? true : false);
			postResponseDto.setIsDislike((p.getIsDislike() != null && p.getIsDislike().equals(1)) ? true : false);
			postResponseDto.setUser(getUSerPost(p.getUserId()));
			postResponseDto.setTopicTag(
					listTopicTagResponse.stream().filter(t -> p.getTopicTagId().equals(t.getId())).findFirst().get());

			return postResponseDto;
		}).collect(Collectors.toList());

		apiResponseEntity.setData(getPostResponseDtos);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	@Override
	public void findAllPostByUserId(Long userId, ApiResponseEntity apiResponseEntity, int offset) {
		List<com.se1.postservice.domain.db.dto.PostDto> allPost = rPostMapper.findAllPostByUserId(userId.toString(),
				offset, userId);
		List<Integer> topicTagIds = allPost.stream().map(ap -> ap.getTopicTagId()).collect(Collectors.toList());
		List<GetPostResponseDto.TopicTag> listTopicTagResponse = getTopicTag(topicTagIds);
		List<GetPostResponseDto> getPostResponseDtos = allPost.stream().map(p -> {
			GetPostResponseDto postResponseDto = new GetPostResponseDto();
			BeanUtils.copyProperties(p, postResponseDto);
			String imageListStr = p.getImageList();
			if (imageListStr != null) {
				String[] imageList = imageListStr.split(", ");
				postResponseDto.setImageList(List.of(imageList));
			}
			postResponseDto.setUser(getUSerPost(p.getUserId()));
			postResponseDto.setTopicTag(
					listTopicTagResponse.stream().filter(t -> p.getTopicTagId().equals(t.getId())).findFirst().get());

			return postResponseDto;
		}).collect(Collectors.toList());

		apiResponseEntity.setData(getPostResponseDtos);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	@Override
	public void findAllPostByCondition(Long userId, Map<String, Object> param, ApiResponseEntity apiResponseEntity,
			int offset) {
		Map<String, String> paramConvert = convertMapToQuery(param);
		List<com.se1.postservice.domain.db.dto.PostDto> allPost = rPostMapper.findAllPostByCondition(paramConvert,
				offset, userId);
		List<Integer> topicTagIds = allPost.stream().map(ap -> ap.getTopicTagId()).collect(Collectors.toList());
		List<GetPostResponseDto.TopicTag> listTopicTagResponse = getTopicTag(topicTagIds);
		List<GetPostResponseDto> getPostResponseDtos = allPost.stream().map(p -> {
			GetPostResponseDto postResponseDto = new GetPostResponseDto();
			BeanUtils.copyProperties(p, postResponseDto);
			String imageListStr = p.getImageList();
			if (imageListStr != null) {
				String[] imageList = imageListStr.split(", ");
				postResponseDto.setImageList(List.of(imageList));
			}
			postResponseDto.setUser(getUSerPost(p.getUserId()));
			postResponseDto.setTopicTag(
					listTopicTagResponse.stream().filter(t -> p.getTopicTagId().equals(t.getId())).findFirst().get());

			return postResponseDto;
		}).collect(Collectors.toList());

		apiResponseEntity.setData(getPostResponseDtos);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	private Map<String, String> convertMapToQuery(Map<String, Object> param) {
		Map<String, String> result = param.entrySet().stream().map(p -> {
			String key = p.getKey();
			Class<?> classType = CommonUtil.checkTypeByKey(key, Post.class);
			String value = CommonUtil.convertObjectToValueSql(p.getValue(), classType);

			return CommonUtil.camelToSnake(key) + "=" + value;
		}).map(s -> s.split("=")).collect(Collectors.toMap(s -> s[0], s -> s[1]));

		return result;
	}

}
