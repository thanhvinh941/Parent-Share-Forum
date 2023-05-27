package com.se1.userservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.se1.userservice.domain.model.ChatBlock;

public interface ChatBlockRepository extends JpaRepository<ChatBlock, Long> {

}
