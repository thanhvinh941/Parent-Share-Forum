package com.se1.postservice.domain.repository;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.se1.postservice.domain.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByTitleContaining(String title);

	List<Post> findByUserId(Long id);

	@Query("SELECT p FROM Post p WHERE p.userId IN :userFriendId AND p.createAt >= :limitDate ")
	List<Post> findAllByIdWithCondition(@Param("userFriendId") List<Long> userFriendId,@Param("limitDate")  Date limitDate);

}
