package com.javateam.healthyFoodProject;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class PwTest {
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Test
	public void test() {
		
		log.info("test");
		String pw2 = "$2a$10$cmzKcHSAro494SZThxumH.XXkoBvz8hEWSL8Oyjl5HWSnzgJa3gCm";
		String pw = "User12345!";
		
		assertTrue(passwordEncoder.matches(pw, pw2));
		
	} //	

}
