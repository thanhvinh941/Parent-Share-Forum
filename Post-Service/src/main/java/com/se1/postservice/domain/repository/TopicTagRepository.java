package com.se1.postservice.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.se1.postservice.domain.entity.TopicTag;

@Repository
public interface TopicTagRepository extends CrudRepository<TopicTag, Integer>{

	@Query("Select t from TopicTag t Where t.tagName LIKE '%:tagName%'")
	List<TopicTag> findAllByTagNameLike(String tagName);

}
