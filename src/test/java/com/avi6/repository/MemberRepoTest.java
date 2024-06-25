package com.avi6.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.avi6.entity.TMember;

@SpringBootTest
public class MemberRepoTest {
	
	   @Autowired
	   private TMemberRepository tMemberRepository;
	   
	   //멤버 정보 넣기
	   @Test
	   void insertMember() {
	      IntStream.rangeClosed(1, 50).forEach(i ->{
	         TMember tMember = TMember.builder()
	               .email("abcd" + i +"@avi6.com")
	               .password("1111")
	               .nickname(i + "palyer")
	               .wishList(i + "번째 멤버의 위시리스트")
	               .build();
	         
	         tMemberRepository.save(tMember);
	      });
	   }
	   
}

