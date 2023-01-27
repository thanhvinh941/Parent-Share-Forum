package com.se1.memberservice.domain.db.read;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.se1.memberservice.domain.dto.MemberDto;

@Mapper
public interface RMemberMapper {

	List<MemberDto> selectWhere(@Param("where") String where);
}
