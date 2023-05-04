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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.common.SCMConstant;
import com.se1.postservice.common.UrlConstant;
import com.se1.postservice.domain.db.read.RPostMapper;
import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.entity.PostView;
import com.se1.postservice.domain.entity.TopicTag;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.ContactDto;
import com.se1.postservice.domain.payload.GetPostResponseDto;
import com.se1.postservice.domain.payload.GetPostResponseDto.User;
import com.se1.postservice.domain.payload.PostDto;
import com.se1.postservice.domain.payload.PostDto.PostTopicTag;
import com.se1.postservice.domain.payload.PostDto.PostUser;
import com.se1.postservice.domain.payload.CreatePostRequest;
import com.se1.postservice.domain.payload.SubscribeDto;
import com.se1.postservice.domain.payload.UpdatePostRequest;
import com.se1.postservice.domain.payload.UserDetail;
import com.se1.postservice.domain.payload.request.RabbitRequest;
import com.se1.postservice.domain.repository.PostRepository;
import com.se1.postservice.domain.repository.PostViewRepository;
import com.se1.postservice.domain.repository.TopicTagRepository;
import com.se1.postservice.domain.service.CallApiService;
import com.se1.postservice.domain.service.PostService;
import com.se1.postservice.domain.service.RabbitSenderService;
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
	private final PostViewRepository viewRepository;
	private final CallApiService<List<GetPostResponseDto.User>> callApiService;
	private final RabbitSenderService rabbitSenderService;
	SimpleDateFormat dateFormatYYYYMMDDHHMMSS = new SimpleDateFormat(SCMConstant.DATE_YYYYMMDD_HHMMSS);

	@Override
	public void processSavePost(CreatePostRequest request, UserDetail detail, ApiResponseEntity apiResponseEntity)
			throws Exception {
		long userId = detail.getId();
		String userName = detail.getName();

		TopicTag topicTag = topicTagRepository.findById(request.getTopicTagId()).orElseGet(null);
		if (topicTag == null) {
			throw new Exception("Chủ đề không hợp lệ");
		}

		List<String> imageList = request.getImageList();
		List<String> imageNameList = new ArrayList<>();
		for (String image : imageList) {
			imageNameList.add(getFileName(image));
		}
		request.setImageList(imageNameList);

		Post postRegist = convertPostRequestToPostEntity(request, userId, userName);

		try {
			Post postSave = postRepository.save(postRegist);
			
			if(detail.getIsExpert()) {
				List<GetPostResponseDto.User> users = getUserSubscriber(postSave.getUserId());
				for(GetPostResponseDto.User user : users) {
					sendNotify(user, postSave);
				}
			}
			
			apiResponseEntity.setData(true);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	private void sendNotify(User users, Post postSave) {
		com.se1.postservice.domain.payload.dto.PostDto postDto = new com.se1.postservice.domain.payload.dto.PostDto();
		postDto.setPostId(postSave.getId());
		postDto.setUserReciver(getUser(postSave.getUserId()));
		postDto.setUserSender(getUser(users.getId()));
		postDto.setAction(SCMConstant.POST_NEW);
		
		RabbitRequest rabbitRequest = new RabbitRequest();
		rabbitRequest.setAction(SCMConstant.SYSTEM_POST);
		rabbitRequest.setData(postDto);
		
		rabbitSenderService.convertAndSendSysTem(rabbitRequest);
	}

	private com.se1.postservice.domain.payload.dto.PostDto.UserDetail getUser(Long userId) {
		com.se1.postservice.domain.payload.dto.PostDto.UserDetail user = new com.se1.postservice.domain.payload.dto.PostDto.UserDetail();
		ApiResponseEntity userResult = (ApiResponseEntity) restTemplateClient.findById(userId);
		if (userResult.getStatus() == 1) {
			String apiResultStr;
			try {
				apiResultStr = objectMapper.writeValueAsString(userResult.getData());
				user = objectMapper.readValue(apiResultStr, com.se1.postservice.domain.payload.dto.PostDto.UserDetail.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	private List<GetPostResponseDto.User> getUserSubscriber(Long userId) {

		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.add("id", userId.toString());

		List<GetPostResponseDto.User> userChatParent = null;
		try {
			userChatParent = objectMapper.readValue(callApiService.callPostMenthodForParam(request,
					CallApiService.USER_SERVICE, UrlConstant.SUBSCRIPER_GETALLSUB), new TypeReference<List<GetPostResponseDto.User>>() {});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return userChatParent;
	}
	
	private String getFileName(String file) {
		return serviceRestTemplateClient.uploadFile(file);
	}

	Post convertPostRequestToPostEntity(CreatePostRequest request, long userId, String userName) {
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

	void doViewPost(List<Long> postIds, Long userId) {
		List<PostView> postViews = postIds.stream().map(p->{
			PostView postView = new PostView();
			postView.setPostId(p);
			postView.setUserId(userId);
			
			return postView;
		}).collect(Collectors.toList());
		
		viewRepository.saveAll(postViews);
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

	@Override
	public void findAllPost(UserDetail userDetail, ApiResponseEntity apiResponseEntity, int offset)
			throws JsonMappingException, JsonProcessingException {
		if(userDetail == null) {
			List<com.se1.postservice.domain.db.dto.PostDto> allPost = rPostMapper.findAll(
					offset);
			List<Long> postIds = allPost.stream().map(post->post.getId()).collect(Collectors.toList());
			doViewPost(postIds, new Long("0"));
			apiResponseEntity.setData(getResponseList(allPost));
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		}else {			
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
			List<com.se1.postservice.domain.db.dto.PostDto> allPost = new ArrayList<>();
			if(allIdUserIdDistinct != null && allIdUserIdDistinct.size() > 0) {
				allPost = rPostMapper.findAllPostByUserId(
						String.join(", ", allIdUserIdDistinct.stream().map(m -> m.toString()).collect(Collectors.toList())),
						offset, userId);
			}else {
				allPost = rPostMapper.findAll(
						offset);
			}
			if(allPost!= null && allPost.size() > 0) {				
				List<Long> postIds = allPost.stream().map(post->post.getId()).collect(Collectors.toList());
				doViewPost(postIds, userId);
			}else {
				allPost = rPostMapper.findAll(
						offset);
				List<Long> postIds = allPost.stream().map(post->post.getId()).collect(Collectors.toList());
				doViewPost(postIds, userId);
			}
			
			apiResponseEntity.setData(getResponseList(allPost));
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		}
	}

	@Override
	public void findAllPostByUserId(Long userId, ApiResponseEntity apiResponseEntity, int offset) {
		List<com.se1.postservice.domain.db.dto.PostDto> allPost = rPostMapper.findAllPostByUserId(userId.toString(),
				offset, userId);
		List<Long> postIds = allPost.stream().map(post->post.getId()).collect(Collectors.toList());
		doViewPost(postIds, userId);

		apiResponseEntity.setData(getResponseList(allPost));
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	@Override
	public void findAllPostByCondition(Long userId, Map<String, Object> param, ApiResponseEntity apiResponseEntity,
			int offset) {
		Map<String, String> paramConvert = convertMapToQuery(param);
		List<com.se1.postservice.domain.db.dto.PostDto> allPost = rPostMapper.findAllPostByCondition(paramConvert,
				offset, userId);
		List<Long> postIds = allPost.stream().map(post->post.getId()).collect(Collectors.toList());
		doViewPost(postIds, userId);

		apiResponseEntity.setData(getResponseList(allPost));
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

	@Override
	public void findById(Long id, Long userId, ApiResponseEntity apiResponseEntity) {
		List<com.se1.postservice.domain.db.dto.PostDto> allPost = rPostMapper.findPostById(id.toString(), userId);
		List<Long> postIds = allPost.stream().map(post->post.getId()).collect(Collectors.toList());
		doViewPost(postIds, userId);
		
		apiResponseEntity.setData(getResponseList(allPost));
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	@Override
	public void findPostAllMost(Long id, ApiResponseEntity apiResponseEntity) {
		List<com.se1.postservice.domain.db.dto.PostDto> allPostMostLike = rPostMapper.findPostMostLike(id);
		List<com.se1.postservice.domain.db.dto.PostDto> allPostMostComment = rPostMapper.findPostMostComment(id);
		List<com.se1.postservice.domain.db.dto.PostDto> allPostMostView = rPostMapper.findPostMostView(id);
		List<com.se1.postservice.domain.db.dto.PostDto> allMerge = new ArrayList<>(allPostMostLike);
		allMerge.addAll(allPostMostComment);
		allMerge.addAll(allPostMostView);
		List<Long> postIds = allMerge.stream().map(post->post.getId()).collect(Collectors.toList());
		doViewPost(postIds, id);

		Map<String, List<GetPostResponseDto>> listResponse = new HashMap<>();
		listResponse.put("mostLike", getResponseList(allPostMostLike));
		listResponse.put("mostComment", getResponseList(allPostMostComment));
		listResponse.put("mostView", getResponseList(allPostMostView));
		
		apiResponseEntity.setData(listResponse);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}
	
	List<GetPostResponseDto> getResponseList(List<com.se1.postservice.domain.db.dto.PostDto> allMerge){
		List<Integer> topicTagIds = allMerge.stream().map(ap -> ap.getTopicTagId()).collect(Collectors.toList());
		List<GetPostResponseDto.TopicTag> listTopicTagResponse = getTopicTag(topicTagIds);
		List<GetPostResponseDto> getPostResponseDtos = allMerge.stream().map(p -> {
			return getPostResponseObejct(p,listTopicTagResponse);
		}).filter(res -> res.getUser() != null).collect(Collectors.toList());
		
		return getPostResponseDtos;
	}
	
	GetPostResponseDto getPostResponseObejct (com.se1.postservice.domain.db.dto.PostDto p, List<GetPostResponseDto.TopicTag> listTopicTagResponse) {
		GetPostResponseDto postResponseDto = new GetPostResponseDto();
		BeanUtils.copyProperties(p, postResponseDto);
		String imageListStr = p.getImageList();
		if (imageListStr != null) {
			String[] imageList = imageListStr.split(", ");
			postResponseDto.setImageList(List.of(imageList));
		}
		postResponseDto.setIsLike((p.getIsLike() != null && p.getIsLike().equals(1)) ? true : false);
		postResponseDto.setIsDislike((p.getIsDislike() != null && p.getIsDislike().equals(1)) ? true : false);
		postResponseDto.setUser(getUSerPost(p.getUserId()));
		postResponseDto.setTopicTag(
				listTopicTagResponse.stream().filter(t -> p.getTopicTagId().equals(t.getId())).findFirst().get());
		postResponseDto.setViewCount(p.getViewCount()+1);
		
		return postResponseDto;
	}

	@Override
	public void update(UpdatePostRequest postRequest, UserDetail detail, ApiResponseEntity apiResponseEntity) throws Exception {
		Optional<Post> postFind = postRepository.findById(postRequest.getPostId());
		if(postFind.isEmpty()) {
			throw new Exception("Bài viết không tồn tại");
		}
		
		Post postOld = postFind.get();
		Boolean isCurrenUser = detail.getId().equals(postOld.getUserId());
		if (!detail.getRole().equals("admin") && !isCurrenUser) {
			throw new Exception("Hành đông không cho phép");
		}
		if(postRequest.getContext() != null) {
			postOld.setContext(postRequest.getContext());
		}
		if(postRequest.getHashTag() != null) {
			postOld.setHashTag(postRequest.getHashTag());
		}
		if(postRequest.getTitle() != null) {
			postOld.setTitle(postRequest.getTitle());
		}
		if(postRequest.getTopicTagId() != null) {
			Optional<TopicTag> topicTag = topicTagRepository.findById(postRequest.getTopicTagId());
			if (topicTag.isEmpty()) {
				throw new Exception("Chủ đề không hợp lệ");
			}
			postOld.setTopicTagId(postRequest.getTopicTagId());
		}
		if(postRequest.getImageList() != null && postRequest.getImageList().size() > 0) {
			List<String> imageList = postRequest.getImageList();
			List<String> imageNameList = new ArrayList<>();
			for (String image : imageList) {
				imageNameList.add(getFileName(image));
			}
			postOld.setImageList(imageNameList);
		}
		Post postUpdate = postRepository.save(postOld);
		apiResponseEntity.setData(postUpdate.getId());
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	@Override
	public void delete(Long postId, UserDetail detail, ApiResponseEntity apiResponseEntity) throws Exception {
		Optional<Post> postFind = postRepository.findById(postId);
		if(postFind.isEmpty()) {
			throw new Exception("Bài viết không tồn tại");
		}
		
		Post postOld = postFind.get();
		Boolean isCurrenUser = detail.getId().equals(postOld.getUserId());
		if (!detail.getRole().equals("admin") && !isCurrenUser) {
			throw new Exception("Hành đông không cho phép");
		}
		postRepository.deleteById(postId);
		apiResponseEntity.setData(true);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

}
