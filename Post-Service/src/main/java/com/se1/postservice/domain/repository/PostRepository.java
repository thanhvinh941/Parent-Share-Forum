package com.se1.postservice.domain.repository;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.se1.postservice.domain.entity.Post;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

	List<Post> findByTitleContaining(String title);

	List<Post> findByUserId(Long id);

	List<Post> findAllById(List<Long> userFriendId, Pageable pageable);

	@Query(name="SELECT p FROM Post WHERE p.userId In :userFriendId AND p.createAt >= :limitDate ", nativeQuery = true)
	List<Post> findAllByIdWithCondition(@Param("userFriendId") List<Long> userFriendId,@Param("limitDate")  Date limitDate);

}
