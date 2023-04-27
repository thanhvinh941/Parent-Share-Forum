package com.se1.notifyservice.domain.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.se1.notifyservice.domain.model.Notify;

@Repository
public interface NotifyRepository extends CrudRepository<Notify, Long> {

	@Query("Select n from Notify n where n.userId = ?1 and n.delFlg = 0")
	List<Notify> findByUserId(Long userId);

	@Transactional
	@Modifying
	@Query("DELETE FROM Notify n WHERE n.userId = ?1")
	void uDeleteNotifyByUserId(Long userId);

	@Transactional
	@Modifying
	@Query("Update Notify n set n.status = 1 where n.userId = ?1 and n.status = 0")
	void updateNotifyByUserId(Long userId);

	@Transactional
	@Modifying
	@Query("Update Notify n set n.status = 1 where n.id = ?1 and n.status = 0")
	void updateNotifyId(Long notifyId);

}
