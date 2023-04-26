package com.se1.postservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.se1.postservice.domain.entity.PostView;

@Repository
public interface PostViewRepository extends JpaRepository<PostView, Long> {

}
