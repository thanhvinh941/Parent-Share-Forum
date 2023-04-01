package com.se1.postservice.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.se1.postservice.domain.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	List<Post> findByTitleContaining(String title);

}
