package com.se1.memberservice.domain.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.se1.memberservice.domain.db.read.RMemberMapper;
import com.se1.memberservice.domain.dto.InsertConditionDto;
import com.se1.memberservice.domain.dto.MemberDto;
import com.se1.memberservice.domain.entity.Member;
import com.se1.memberservice.domain.repository.MemberRepository;
import com.se1.memberservice.domain.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private final MemberRepository memberRepository;
	
	private final RMemberMapper mapper;
	
	@SuppressWarnings("removal")
	@Override
	public List<Member> regisMember(InsertConditionDto<Member> request) {
		request.getRecords().stream().forEach(record -> {
			record.setCreateDt(Timestamp.valueOf(LocalDateTime.now()));
			record.setUpdateDt(Timestamp.valueOf(LocalDateTime.now()));
			record.setLastLoginTime(Timestamp.valueOf(LocalDateTime.now()));
			record.setStatusFlg(new Byte("0"));
			record.setValidFlg(new Byte("1"));
			record.setDelFlg(new Byte("0"));
		});
		
		List<Member> memberSave = memberRepository.saveAll(request.getRecords());
		
		return memberSave != null ? memberSave : null ;
	}

	@Override
	public List<MemberDto> getMemberWithConditonSpecifiedField(Map<String, Object> request) {
		
		String whereStr = generatorWhereConditionSpecifiedField(request);
		
		return mapper.selectWhere(whereStr);
	}

	@Override
	public List<MemberDto> getMemberWithConditonSpecifiedPattern(Map<String, Object> request) {

		String whereStr = generatorWhereConditionSpecifiedPattern(request);
		
		return mapper.selectWhere(whereStr);
	}
	
	private String generatorWhereConditionSpecifiedField(Map<String, Object> condition) {
		StringBuilder stringBuilder =  new StringBuilder();
		stringBuilder.append("WHERE ");
		Function<Map.Entry<String, Object>, String> mapToString = entry -> {
			String key = entry.getKey();
			Object value = entry.getValue();
			String valueStr = "";
			String mapResult = "";
			if( value instanceof String) {
				valueStr = String.format("'%s'", value);
				
				mapResult = key + " = " + valueStr;
			}else if(value instanceof Timestamp) {
				String timestampStr = new SimpleDateFormat("yyyy-MM-dd ").format(value);
				valueStr = String.format(" BETWEEN '%s 00:00:00.000000' AND '%s 23:59:59.000000'", timestampStr, timestampStr);
				
				mapResult = key + valueStr;	
			}else {
				
				mapResult = key + " = " + value.toString();	
			}
			return mapResult;
		};
		
		List<String> listCondition = condition.entrySet()
				.stream()
				.map(mapToString)
				.collect(Collectors.toList());
		
		String whereCondition =	String.join(" AND ", listCondition);
		stringBuilder.append(whereCondition);
		
		return stringBuilder.toString();
	}

	@SuppressWarnings("unchecked")
	private String generatorWhereConditionSpecifiedPattern(Map<String, Object> condition) {
		StringBuilder stringBuilder =  new StringBuilder();
		stringBuilder.append("WHERE ");
		Function<Map.Entry<String, Object>, String> mapToString = entry -> {
			String key = entry.getKey();
			Object value = entry.getValue();
			StringBuilder valueStr = new StringBuilder();
			String mapResult = "";
			if(value instanceof String) {
				valueStr.append("'%");
				valueStr.append(String.format("%s", value));
				valueStr.append("%'");
				
				mapResult = key + " LIKE " + valueStr;
			}else if(value instanceof Timestamp) {
				String timestampStr = new SimpleDateFormat("yyyy-MM-dd ").format(value);
				valueStr.append(String.format(" BETWEEN '%s 00:00:00.000000' AND '%s 23:59:59.000000'", timestampStr, timestampStr));
				
				mapResult = key + valueStr;
			}else if(value instanceof List && ((List<String>) value).get(0) instanceof String) {
				List<String> valueFomater = ((List<String>) value).stream().map(s -> "'"+s+"'").collect(Collectors.toList());
				valueStr.append("( ");
				valueStr.append(String.join(", ", valueFomater));
				valueStr.append(" )");
				
				mapResult = key + " IN " + valueStr;
			}else if(value instanceof List && ((List<Integer>) value).get(0) instanceof Integer) {
				List<String> valueFomater = ((List<Integer>) value).stream().map(s -> s.toString()).collect(Collectors.toList());
				valueStr.append("( ");
				valueStr.append(String.join(", ", valueFomater));
				valueStr.append(" )");
				
				mapResult = key + " IN " + valueStr;
			}else if(value instanceof List && ((List<Timestamp>) value).get(0) instanceof Timestamp) {
				String fromAt = new SimpleDateFormat("yyyy-MM-dd ").format(((List<Timestamp>) value).get(0));
				String endAt = new SimpleDateFormat("yyyy-MM-dd ").format(((List<Timestamp>) value).get(1));
				valueStr.append(String.format(" BETWEEN '%s 00:00:00.000000' AND '%s 23:59:59.000000'", fromAt, endAt));
				
				mapResult = key + valueStr;
			}else {
				mapResult = key + " = " + value.toString();
			}
			
			return mapResult;
		};
		
		List<String> listCondition = condition.entrySet()
				.stream()
				.map(mapToString)
				.collect(Collectors.toList());
		
		String whereCondition =	String.join(" AND ", listCondition);
		stringBuilder.append(whereCondition);
		
		return stringBuilder.toString();
	}
	
}
