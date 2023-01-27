package com.se1.postservice.domain.service;

import java.util.List;

import com.se1.postservice.domain.entity.Post;

public interface PostService {

	List<Post> saveAll(List<Post> request);

	Boolean uDelete(Integer postId);
	
	Boolean uPublish(Integer postId, Byte publishedFlg); 

}
