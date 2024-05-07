package com.javateam.healthyFoodProject.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.javateam.healthyFoodProject.domain.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MemberUpdateSasangTest {
	
	@Autowired
	MemberDAO memberDAO;
	
	@Test
	public void test() {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberEmail("user1234@naver.com");
		memberDTO.setMemberSasang("소음인");
		memberDAO.updateMemberSasang(memberDTO);
	}
	
	@Test
	public void test2() {
		
		
		
	}
}
