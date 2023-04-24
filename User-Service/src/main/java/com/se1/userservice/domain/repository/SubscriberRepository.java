package com.se1.userservice.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.se1.userservice.domain.model.Subscribe;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscribe, Long>{

	Subscribe findByUserExpertIdAndUserSubscriberId(Long expertid, Long id);

	List<Subscribe> findByUserSubscriberId(Long id);

	@Modifying
	@Query("DELETE Subscribe s WHERE s.id = ?1")
	void deleteById(Long id);

}
