package com.javateam.healthyFoodProject.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import jakarta.servlet.ServletContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javateam.healthyFoodProject.domain.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
// @ExtendWith(SpringExtension.class)
// @AutoConfigureMockMvc
// @WebMvcTest
@Slf4j
public class MemberJoinControllerTest2 {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Autowired
    private FilterChainProxy springSecurityFilterChain;
		
	@Autowired
	ObjectMapper objectMapper; // fasterxml jackson object(객체) 
	
	MemberDTO memberDTO;
	
	@Autowired
	ServletContext application;

	@BeforeEach
	public void setUp() throws ParseException {
	
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		
		// 주의) 아래의 전통적인 방법은 상위 버전(Spring Boot 3.1.x 이상)에서는 
		// 컨트롤러(Controller) 객체가 주입이 잘 안될 수 있습니다. 
		
		// mockMvc = MockMvcBuilders.standaloneSetup(MemberController.class)
					// .apply(springSecurity(springSecurityFilterChain))
		//			.build();
		
		String joindate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		memberDTO = MemberDTO.builder()
				  			 .memberEmail("swim50m@abcd.com")
				  			 .memberPw("$2a$10$1t3vaIa5jtsMp2RY9y7xhuJz0xDRNEl0csvPYvgCbyKuKeyOVucES")
				  			 .memberNick("지유찬")
				  			 .memberMobile("010-1212-3434")
				  			 .memberZip("08290")
				  			 .memberAddress1("서울특별시 관악구 남부순환로 1633 (신림동)")
				  			 .memberAddress2("이젠아카데미 신림점 별관 8층")
				  			 .memberDate(new SimpleDateFormat("yyyy-MM-dd").parse(joindate)) // NullPointerException 방지
				  			 // .regDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-09-26")) // NullPointerException 방지
				  			 .enabled(0)
				  			 .build();
	} //
	
	@Transactional
	// @Rollback(true)
	@Rollback(false)
	@Test
	public void test() {
		
		log.info("회원 가입 REST 테스트 : {}", memberDTO);

		String contextPath = application.getContextPath();
		
		try {
						
//			mockMvc.perform(post(contextPath + "/member/joinProc.do")					       		
//					       		.contentType("text/html; charset=UTF-8")
//					       		.param("id", memberDTO.getId())
//					       		.param("password", memberDTO.getPassword())
//					       		.param("name", memberDTO.getName())
//					       		.param("gender", memberDTO.getGender())
//					       		.param("email", memberDTO.getEmail())
//					       		.param("mobile", memberDTO.getMobile())
//					       		.param("phone", memberDTO.getPhone())
//					       		.param("zip", memberDTO.getZip())
//					       		.param("address1",  memberDTO.getAddress1())
//					       		.param("address2",  memberDTO.getAddress2())
//					       		.param("birthday", 
//				       					new SimpleDateFormat("yyyy-MM-dd").format(memberDTO.getBirthday()))
//					       		.param("joindate", 
//					       				new SimpleDateFormat("yyyy-MM-dd").format(memberDTO.getJoindate())) // NullPointException 방지 : 인자 채우기
//					       		.param("enabled", "1"))
//						  .andExpect(status().isOk())
//						  .andExpect(redirectedUrl(contextPath + "/member/join.do"))
//						  .andDo(print());
			
			mockMvc.perform(post("/member/joinProc.do")
						.contextPath(contextPath)
						.params(MemberDTO.toMap(memberDTO)))
				   .andExpect(redirectedUrl(contextPath + "/login"))
				   .andDo(print());			
						  
			
		} catch (Exception e) {
			log.error("MemberJoinControllerTest 에러 메시지 : " + e);
			e.printStackTrace();
		} //	
		
	}

}
