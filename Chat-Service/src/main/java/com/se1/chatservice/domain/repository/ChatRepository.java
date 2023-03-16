package com.se1.chatservice.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.se1.chatservice.domain.model.Chat;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long>{

}
