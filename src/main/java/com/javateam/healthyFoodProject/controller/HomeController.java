package com.javateam.healthyFoodProject.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.javateam.healthyFoodProject.domain.CustomUser;

import lombok.extern.slf4j.Slf4j;

/**
 * 메인화면, 로그인
 * @author cofla
 *
 */

@Controller
@Slf4j
public class HomeController {
	
	@GetMapping("/")
	public String home(Model model) {
		
		log.info("$$$ home확인 ");
		
		model.addAttribute("arg", "인자");
		
		return "home4"; 
		
	} //
	
//	// 0509 로그인 시 에러. http://localhost:8181/healthyFoodProject/error?continue
//	@GetMapping("/error")
//	public String error() {
//		log.error("에러 발생했음.");
//		
//		return "home4";
//	}
	
	/**
	 * @param model 해당하는 페이지의 타이틀, 사진을 보냄 
	 */
	@GetMapping("/login")
	public String login(Model model) {
		
		log.info("login");
		
		// title 0506 로그인
		model.addAttribute("pageTitle", "로그인");
		model.addAttribute("bgImg", "bg-tomato.png");
		return "login";
	} //
	
	@GetMapping("/loginError")
    public String loginError(Model model, HttpSession session) {
		
		log.error("$$$ 인증오류");
    	
		// Spring CustomProvider 인증(Auth) 에러 메시지 처리
		Object secuSess = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		
		if(secuSess != null) {
			log.info("#### 인증 오류 메시지-1 : " + secuSess);
			log.info("#### 인증 오류 메시지-2 : " + secuSess.toString());

			model.addAttribute("error", "true");
			model.addAttribute("msg", secuSess);
			
			// title 0513 로그인 헤더 추가. 
			model.addAttribute("pageTitle", "로그인");
			model.addAttribute("bgImg", "bg-tomato.png");
		}
	
		return "login";
	}	
	
	// /403 : 접근 권한 문제시 이동 페이지 : SecurityConfig.java
	/*
	   .and()
           .exceptionHandling().accessDeniedPage("/403");     
	 */
	@GetMapping("/403")
    public String acessDenided(Model model, HttpSession session) {
		
		log.error("403 mapping");
    	
		model.addAttribute("errMsg", "페이지 접근 권한이 없습니다.");
		model.addAttribute("movePage", "/");
	
		return "/error/error";
	}	

}
