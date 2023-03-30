package com.se1.notifyservice.domain.db.write;

import org.apache.ibatis.annotations.Mapper;

import com.se1.notifyservice.domain.dto.NotifyDto;

@Mapper
public interface WNotifyMapper {

	Long insertNotify(NotifyDto notify);
}
