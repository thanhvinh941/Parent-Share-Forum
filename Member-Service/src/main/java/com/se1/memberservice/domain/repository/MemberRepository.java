package com.se1.memberservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.se1.memberservice.domain.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{

}
