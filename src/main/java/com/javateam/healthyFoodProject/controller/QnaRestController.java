package com.javateam.healthyFoodProject.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javateam.healthyFoodProject.domain.QnaDTO;
import com.javateam.healthyFoodProject.domain.QnaVO;
import com.javateam.healthyFoodProject.service.QnaService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("qna")
@Slf4j
public class QnaRestController {

	@Autowired
	QnaService qnaService;
	
	@PostMapping("checkLock.do")
	public ResponseEntity<String> checkLock(@RequestBody Map<String,Object> map){

		log.info("checkLock : qnaCode = {}, qnaPass = {}, memberEmail = {}", 
		map.get("qnaCode"), map.get("qnaPass"), map.get("memberEmail"));
		
		//HttpHeaders responseHeaders = new HttpHeaders();
		//responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		String result = "";
		ResponseEntity<String> responseEntity = null;
		QnaVO qnaVO = qnaService.selectQna(Integer.parseInt(map.get("qnaCode").toString()));

		//패스워드와 이메일 비교
		boolean emailCheck = qnaVO.getMemberEmail().equals(map.get("memberEmail").toString());
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		log.info("qnaVO.getQnaPass() : {}", qnaVO.getQnaPass());
		
		boolean pwCheck = passwordEncoder.matches(map.get("qnaPass").toString().trim(), qnaVO.getQnaPass());
		
		log.info("pwCheck : {}", pwCheck);
		
		if(emailCheck == false) {
			result = "해당글 작성자가 아닙니다.";
		} else {
			if(pwCheck == false) {
				result = "패스워드가 틀렸습니다.";
			} else {
				result="해당글 작성자가 맞습니다.";
			}
		}
		
		responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
		
		return responseEntity;
	}	
	
}
