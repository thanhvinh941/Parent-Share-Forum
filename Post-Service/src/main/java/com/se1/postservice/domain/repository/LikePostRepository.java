package com.se1.postservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.se1.postservice.domain.entity.PostLike;

@Repository
public interface LikePostRepository extends JpaRepository<PostLike, Integer>{

	@Query("SELECT l from PostLike l where l.postId = ?1 and l.userId = ?2")
	PostLike findByPostIdAndUserId(Long postId, Long id);

}
