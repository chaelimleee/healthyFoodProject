package com.javateam.healthyFoodProject.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.healthyFoodProject.dao.MemberDAO;
import com.javateam.healthyFoodProject.domain.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class UpdateMemberTest {
	
	@Autowired
	MemberDAO memberDAO;
	
	MemberDTO memberDTO;
	
	@BeforeEach
	public void setUp() throws ParseException {
		
		memberDTO = MemberDTO.builder()
	  			 .memberEmail("abcd1234")
	  			 .memberPw("$2a$10$lhx31tqVr9IDrG8NMrtdX.Q..cd1CDd4gRHPxaXZo47aJUOIdrZxK")
	  			 .memberMobile("010-7878-9090")
	  			 .memberZip("08285")
	  			 .memberAddress1("서울 관악구 신림로 340")
	  			 .memberAddress2("이젠아카데미 신림점 본관")
	  			 .build();
		
	}
	
	@Test
	@Transactional
	// @Rollback(true)
	@Rollback(false)
	public void test() {
		
		log.info("memberDTO : {}", memberDTO);
		boolean result = false;
		
		try {
			memberDAO.updateMember(memberDTO);
			result = true;
		} catch (Exception e) {
			log.error("UpdateMemberTest.updateMember : {}", e);
			result = false;
			e.printStackTrace();
		}
		
		assertTrue(result);		
	} //

}
