package com.se1.commentservice.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.se1.commentservice.domain.entity.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>{

}
