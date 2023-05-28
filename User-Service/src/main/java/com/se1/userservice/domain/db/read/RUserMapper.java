package com.se1.userservice.domain.db.read;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.se1.userservice.domain.db.dto.ReportUserDto;
import com.se1.userservice.domain.model.User;

@Mapper
public interface RUserMapper {

	List<User> find(@Param("query") String query, @Param("offset") Integer offset);

	List<User> findAll(@Param("conditions") List<String> mapRequest, @Param("offset") Integer offset);

	List<ReportUserDto> findAllHaveReport(@Param("conditions") List<String> mapRequest, @Param("offset") Integer offset);

	List<User> selectByConditionStr(@Param("conditionStr") String conditionStr, 
			@Param("order") String order, @Param("limit") Integer limit, @Param("offset")  Integer offset);

}
