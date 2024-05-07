package com.javateam.healthyFoodProject.service;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.healthyFoodProject.domain.MemberDTO;
import com.javateam.healthyFoodProject.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class MemberServiceInsertMemberTest {
	
	@Autowired
	MemberService memberService;
	
	MemberDTO memberDTO;

	@BeforeEach
	void setUp() throws Exception {
		
		memberDTO = MemberDTO.builder()
	  			 .memberEmail("swimgoldenboy@abcd.com")
	  			 .memberPw("$2a$10$1t3vaIa5jtsMp2RY9y7xhuJz0xDRNEl0csvPYvgCbyKuKeyOVucES")
	  			 .memberNick("황선우")
	  			 .memberMobile("01082827979")
	  			 .memberZip("08290")
	  			 .memberAddress1("서울특별시 관악구 남부순환로 1633 (신림동)")
	  			 .memberAddress2("이젠아카데미 신림점 별관 8층")
	  			 .build();
	}

	@Transactional
	// @Rollback(true)
	@Rollback(false)
	@Test
	void testInsertMemberRole() {
		
		log.info("MemberServiceInsertMemberTest");
		assertTrue(memberService.insertMemberRole(memberDTO));
	}

}
