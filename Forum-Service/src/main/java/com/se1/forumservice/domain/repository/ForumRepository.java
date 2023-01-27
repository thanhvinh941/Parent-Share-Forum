package com.se1.forumservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.se1.forumservice.domain.entity.Forum;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Integer>{

	
}
