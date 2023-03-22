package com.se1.chatservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.se1.chatservice.model.Chat;


@Repository
public interface ChatRepository extends CrudRepository<Chat, Long>{

}
