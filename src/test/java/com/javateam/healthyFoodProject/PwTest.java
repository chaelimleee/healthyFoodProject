package com.javateam.healthyFoodProject;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class PwTest {

	@Test
	public void test() {
		String pw = "Abcd1234!";
		String pw2 = "$2a$10$DREv6Tvnw.ST4Xj1.VuZg.M80I.oT6KhWYv7v5dk3/jiSaytmC6Wa";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		assertTrue(passwordEncoder.matches(pw, pw2));
	}
}
