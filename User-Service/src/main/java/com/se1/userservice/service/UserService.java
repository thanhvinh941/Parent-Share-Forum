package com.se1.userservice.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.se1.userservice.model.User;
import com.se1.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;
	
	public User save(User user) throws Exception {
		
		User userSave = null;
		try {
			userSave = repository.save(user);
		} catch (DataAccessException e) {
			throw new Exception(e.getMessage());
		}
		return userSave;
	}

	public User findByEmail(String email) throws Exception {
		User userFind = null ;
		try {
			userFind = repository.findByEmail(email);
		} catch (DataAccessException e) {
			throw new Exception(e.getMessage());
		}
		return userFind;
	}
	
	public User findById(Long id) throws Exception {
		User userFind = null ;
		try {
			userFind = repository.findById(id).get();
		} catch (DataAccessException e) {
			throw new Exception(e.getMessage());
		}
		return userFind;
	}
}
