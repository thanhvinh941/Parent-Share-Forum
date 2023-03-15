package com.se1.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.se1.userservice.model.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

	@Query("SELECT c FROM Contact c WHERE c.userReciverId = ?1")
	List<Contact> findByUserReciverId(Long userReciverId);

	@Query("SELECT c FROM Contact c WHERE (c.userReciverId = ?1 AND c.userSenderId = ?2) "
			+ "OR (c.userReciverId = ?2 AND c.userSenderId = ?1) ")
	Contact findByUserReciverIdAndUserSenderId(Long userId1, Long userId2);
	
	@Query("SELECT c FROM Contact c WHERE c.userSenderId = ?1")
	List<Contact> findByUserSenderId(Long userSenderId);

	@Query("UPDATE Contact c SET c.status = ?3 WHERE c.userReciverId = ?1 AND c.userSenderId = ?2")
	long updateContact(long userReciverId, long userSenderId, int statusUpdate);
	
}
