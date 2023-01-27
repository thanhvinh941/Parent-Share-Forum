package com.se1.postservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.se1.postservice.domain.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	@Query("UPDATE post p SET p.validFlag = 0 WHERE p.postId = ?1")
	Boolean uDelete(Integer postId);

	@Query("UPDATE post p SET p.publishedFlg = ?2 AND p.publishedAt = CURRENT_DATE WHERE p.postId = ?1")
	Boolean uPublish(Integer postId, Byte publishedFlg);
}
