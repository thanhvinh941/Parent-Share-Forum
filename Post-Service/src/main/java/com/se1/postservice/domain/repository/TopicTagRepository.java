package com.se1.postservice.domain.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.se1.postservice.domain.entity.TopicTag;

@Repository
public interface TopicTagRepository extends CrudRepository<TopicTag, Integer>{

	@Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "Update db02.topic_tag ?1", nativeQuery = true)
	int updateById(String updateCondition);

}
