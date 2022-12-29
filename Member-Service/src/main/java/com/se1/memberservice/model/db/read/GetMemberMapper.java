package com.se1.memberservice.model.db.read;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.se1.memberservice.domain.entity.Member;

@Mapper
public interface GetMemberMapper {

	
	List<Member> selectWhere(@Param("where") String where);
}
