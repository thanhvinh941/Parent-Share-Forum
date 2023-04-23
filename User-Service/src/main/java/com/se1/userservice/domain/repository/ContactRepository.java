package com.se1.userservice.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.se1.userservice.domain.model.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

	@Query("SELECT c FROM Contact c WHERE c.userReciverId = ?1")
	List<Contact> findByUserReciverId(Long userReciverId);

	@Query("SELECT c FROM Contact c WHERE (c.userReciverId = ?1 AND c.userSenderId = ?2) "
			+ "OR (c.userReciverId = ?2 AND c.userSenderId = ?1) ")
	Contact findByUserReciverIdAndUserSenderId(Long userId1, Long userId2);

	@Query("SELECT c FROM Contact c WHERE c.userSenderId = ?1")
	List<Contact> findByUserSenderId(Long userSenderId);

	@Modifying
	@Query("UPDATE Contact c SET c.status = ?2 WHERE c.id = ?1")
	void updateContact(Long contactId, int statusUpdate);

	@Query("SELECT c FROM Contact c WHERE c.userSenderId = :userId OR c.userReciverId = :userId AND (:status is null or c.status = :status)")
	List<Contact> findByUserId(@Param("userId") Long userId, @Param("status") Integer status);

	@Modifying
	@Query("UPDATE Contact c SET c.userSenderId = ?2 , c.userReciverId = ?1 , c.status = ?4 WHERE c.id = ?3")
	void updateContactV2(Long userReciverId, Long userSenderId, Long id,
			int status);

}
