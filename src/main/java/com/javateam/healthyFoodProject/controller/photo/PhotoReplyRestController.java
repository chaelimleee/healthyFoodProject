package com.javateam.healthyFoodProject.controller.photo;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javateam.healthyFoodProject.domain.PhotoVO;
import com.javateam.healthyFoodProject.service.PhotoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("photo_board")
@Slf4j
public class PhotoReplyRestController {
	
	@Autowired
	PhotoService photoService; 

	@PostMapping("replyWrite.do")
	// public ResponseEntity<Boolean> replyWrite(@RequestBody Map<String, Object> map) {
	// 댓글을 작성하면서 즉시 현재까지의 댓글들 현황을 집계하여 리턴
	public ResponseEntity<List<PhotoVO>> replyWrite(@RequestBody Map<String, Object> map) {
		
		log.info("replyWrite.do : boardNum={}, boardContent={}, boardWriter={}", map.get("boardNum"), map.get("boardContent"), map.get("boardWriter"));
		
		List<PhotoVO> replyList = new ArrayList<>();

		ResponseEntity<List<PhotoVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			PhotoVO photoVO = new PhotoVO();
			
			//
			// 주의사항) 
			// 여기서 댓글의 고유 아이디는 DB를 통해서 생성되므로 원글의 아이디(boardNum)는 다른 필드에 입력됩니다.
			photoVO.setBoardWriter(map.get("boardWriter").toString());
			photoVO.setBoardSubject("댓글");
			photoVO.setBoardContent(map.get("boardContent").toString());
			photoVO.setBoardReRef(Integer.parseInt(map.get("boardNum").toString()));
			photoVO.setBoardReLev(1);
			
			// 댓글의 현황을 보면서 댓글 시퀀스 결정
			photoVO = photoService.insertBoard(photoVO);
			
			log.info("--- result : {}", photoVO);
			
			if (photoVO != null) {
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
				replyList = photoService.selectReplysById(photoVO.getBoardReRef());				
				
				// 댓글 등록 성공 : 성공 코드(200)
				// responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
				responseEntity = new ResponseEntity<>(replyList, HttpStatus.OK); 
				
			} else {
				// 댓글 등록 실패 : 실패 코드(204)
				// responseEntity = new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("replyWrite error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //
	
	@GetMapping("getRepliesAll.do")
	public ResponseEntity<List<PhotoVO>> getRepliesAll(@RequestParam("boardNum") int boardNum) { 
		
		log.info("getRepliesAll.do : boardNum={}", boardNum);
		
		List<PhotoVO> replyList = new ArrayList<>();
		ResponseEntity<List<PhotoVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
			// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
			replyList = photoService.selectReplysById(boardNum);				
			
			//replyList.forEach(x-> { log.info("날짜 : "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(x.getBoardDate())); });
			replyList.forEach(x-> { log.info("날짜 : {}", x.getBoardDate()); });
			
			// 댓글들이 있다면...
			if (replyList.size() > 0) {

				// 댓글 등록 성공 : 성공 코드(200)
				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
				responseEntity = new ResponseEntity<>(replyList, HttpStatus.OK); 
				
			} else {
				// 댓글 등록 실패 : 실패 코드(204)
				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("getRepliesAll error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //
	
	@PostMapping("replyUpdate.do")
	public ResponseEntity<List<PhotoVO>> replyUpdate(@RequestBody Map<String, Object> map) { 
		
		log.info("replyUpdate.do : boardNum={}, boardContent={}, boardWriter={}, boardReRef={}",
				map.get("boardNum"), map.get("boardContent"), map.get("boardWriter"), map.get("boardReRef"));
		
		List<PhotoVO> replyList = new ArrayList<>();

		ResponseEntity<List<PhotoVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			PhotoVO photoVO = new PhotoVO();
			
			int boardNum = Integer.parseInt(map.get("boardNum").toString());
			
			// 주의) 댓글 수정에서는 댓글의 아이디가 이미 등록시 발행이 되어 있으므로 댓글의 실제 아이디 !
			photoVO.setBoardNum(boardNum);  
			photoVO.setBoardWriter(map.get("boardWriter").toString());
			photoVO.setBoardSubject("댓글");
			photoVO.setBoardReRef(Integer.parseInt(map.get("boardReRef").toString()));
			photoVO.setBoardContent(map.get("boardContent").toString());
			photoVO.setBoardReLev(1);
			photoVO.setBoardDate(new Date(System.currentTimeMillis()));
			
			log.info("photoVO : {}", photoVO);
			
			photoVO = photoService.updateBoard(photoVO);
			log.info("--- result : {}", photoVO);
			
			if (photoVO != null) {
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
				replyList = photoService.selectReplysById(photoVO.getBoardReRef());				
				
				// 댓글 등록 성공 : 성공 코드(200)
				// responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라이언트에 전송)
				responseEntity = new ResponseEntity<>(replyList, HttpStatus.OK); 
				
			} else {
				// 댓글 등록 실패 : 실패 코드(204)
				// responseEntity = new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("replyWrite error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //
	
	@PostMapping("replyDelete.do")
	public ResponseEntity<List<PhotoVO>> replyDelete(@RequestBody Map<String, Object> map) { 
		
		log.info("replyDelete.do : boardNum={}, originalBoardNum={}", 
					map.get("boardNum"), map.get("originalBoardNum"));
		
		List<PhotoVO> replyList = new ArrayList<>();

		ResponseEntity<List<PhotoVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		int boardNum = Integer.parseInt(map.get("boardNum").toString());
		int originalBoardNum = Integer.parseInt(map.get("originalBoardNum").toString());
		//String boardPass= map.get("boardPass").toString();
		
		try {
			/*
			// 패쓰워드 체크
			String originalBoardPass = photoService.selectBoard(boardNum).getBoardPass();
			
			log.info("originalBoardPass : {}", originalBoardPass);
			
			boolean isPass = boardPass.equals(originalBoardPass) ? true : false;
			
			log.info("패스워드 일치 여부 : {}", isPass);
			
			
			
			if (isPass == true) {
			*/ //
			boolean result = photoService.deleteReplysById(boardNum);; // 삭제 결과

			log.info("삭제 결과 : {}", result);
			
			if (result == true) { // 삭제
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
				replyList = photoService.selectReplysById(originalBoardNum);				
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
				responseEntity = new ResponseEntity<>(replyList, HttpStatus.OK); 
				
			} else {
				// 댓글 등록 실패 : 실패 코드(204)
				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			log.error("replyWrite error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //
	
}
