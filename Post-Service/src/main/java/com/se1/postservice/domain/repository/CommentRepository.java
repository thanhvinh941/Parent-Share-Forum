package com.se1.postservice.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.se1.postservice.domain.entity.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>{

}
