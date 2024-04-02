package com.javateam.healthyFoodProject.controller;

//import java.text.SimpleDateFormat;
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

import com.javateam.healthyFoodProject.domain.BoardVO;
import com.javateam.healthyFoodProject.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("board")
@Slf4j
public class BoardReplyRestController {
	/**
	 * 0401 leee 수정 완. 하지만 패스워드 부분 손 봐야함.
	 * 
	 */
	@Autowired
	BoardService boardService; 

	@PostMapping("replyWrite.do")
	// public ResponseEntity<Boolean> replyWrite(@RequestBody Map<String, Object> map) {
	// 댓글을 작성하면서 즉시 현재까지의 댓글들 현황을 집계하여 리턴
	public ResponseEntity<List<BoardVO>> replyWrite(@RequestBody Map<String, Object> map) {
		
		log.info("replyWrite.do : boardCode={}, boardContent={}", map.get("boardCode"), map.get("boardContent"));
		
		List<BoardVO> replyList = new ArrayList<>();

		// ResponseEntity<Boolean> responseEntity = null; 
		ResponseEntity<List<BoardVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			BoardVO boardVO = new BoardVO();
			
			//
			// 주의사항) 
			// 여기서 댓글의 고유 아이디는 DB를 통해서 생성되므로 원글의 아이디(boardCode)는 다른 필드에 입력됩니다.
			boardVO.setMemberEmail(map.get("memberEmail").toString());
			boardVO.setMemberNick(map.get("memberNick").toString());
			boardVO.setBoardTitle("댓글");
			boardVO.setBoardContent(map.get("boardContent").toString());
			boardVO.setBoardOrigin(Integer.parseInt(map.get("boardCode").toString()));
//			boardVO.setBoardReLev(1);
			
			// 댓글의 현황을 보면서 댓글 시퀀스 결정
			boardVO = boardService.insertBoard(boardVO);
			
			log.info("--- result : {}", boardVO);
			
			if (boardVO != null) {
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
				replyList = boardService.selectReplysById(boardVO.getBoardOrigin());				
				
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
	public ResponseEntity<List<BoardVO>> getRepliesAll(@RequestParam("boardCode") int boardCode) { 
		
		log.info("getRepliesAll.do : boardCode={}", boardCode);
		
		List<BoardVO> replyList = new ArrayList<>();
		ResponseEntity<List<BoardVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
			// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
			replyList = boardService.selectReplysById(boardCode);				
			
			// replyList.forEach(x-> { log.info("날짜 : {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(x.getBoardDate())); });
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
	public ResponseEntity<List<BoardVO>> replyUpdate(@RequestBody Map<String, Object> map) { 
		
		log.info("replyUpdate.do : boardCode={}, boardContent={}", map.get("boardCode"), map.get("boardContent"));
		
		List<BoardVO> replyList = new ArrayList<>();

		ResponseEntity<List<BoardVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			BoardVO boardVO = new BoardVO();
			
			int boardCode = Integer.parseInt(map.get("boardCode").toString());
			
			// 주의) 댓글 수정에서는 댓글의 아이디가 이미 등록시 발행이 되어 있으므로 댓글의 실제 아이디 !
			boardVO.setBoardCode(boardCode);  
			boardVO.setMemberEmail(map.get("memberEmail").toString());
			boardVO.setMemberNick(map.get("memberNick").toString());
			boardVO.setBoardTitle("댓글");
			boardVO.setBoardContent(map.get("boardContent").toString());
			boardVO.setBoardOrigin(Integer.parseInt(map.get("boardCode").toString()));
			boardVO.setBoardDate(new Date(System.currentTimeMillis()));
			
			log.info("boardVO : {}", boardVO);
			
//			// 패쓰워드 체크
//			String originalMemberEmail = boardService.selectBoard(boardCode).getMemberEmail();
////			
//			boolean isPass = map.get("memberEmail").toString().equals(originalMemberEmail) ? true : false;
//			
//			if (isPass == true) {
//				
//				boardVO = boardService.updateBoard(boardVO);
//				
//				log.info("--- result : {}", boardVO);
//				
//			} else { // 패쓰워드가 틀리면...
//				
//				log.error("게시글 패쓰워드 불일치");
//
//				// Http Status Code : 401
//				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//			} // 
			
			if (boardVO != null) {
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
				replyList = boardService.selectReplysById(boardVO.getBoardOrigin());				
				
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
	public ResponseEntity<List<BoardVO>> replyDelete(@RequestBody Map<String, Object> map) { 
		
		log.info("replyDelete.do : boardCode={}, originalboardCode={}, memberEmail={}", 
					map.get("boardCode"), map.get("originalboardCode"), map.get("memberEmail"));
		
		List<BoardVO> replyList = new ArrayList<>();

		ResponseEntity<List<BoardVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		int boardCode = Integer.parseInt(map.get("boardCode").toString());
		int originalboardCode = Integer.parseInt(map.get("originalboardCode").toString());
		String boardPass= map.get("memberEmail").toString();
		
		try {
			
			// 패쓰워드 체크
//			String originalBoardPass = boardService.selectBoard(boardCode).getBoardPass();
			
//			log.info("originalBoardPass : {}", originalBoardPass);
//			
//			boolean isPass = boardPass.equals(originalBoardPass) ? true : false;
			
//			log.info("패스워드 일치 여부 : {}", isPass);
//			
//			boolean result = false; // 삭제 결과
//			
//			if (isPass == true) {
//				
//				result = boardService.deleteReplysById(boardCode);
//				
//			} else { // 패쓰워드가 틀리면...
//				
//				log.error("게시글 패쓰워드 불일치");
//
//				// Http Status Code : 401
//				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//			} // 
//			
//			log.info("삭제 결과 : {}", result);
//			
//			if (result == true) { // 삭제
//				
//				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
//				replyList = boardService.selectReplysById(originalboardCode);				
//				
//				// 댓글 등록 성공 : 성공 코드(200)
//				// responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
//				
//				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
//				responseEntity = new ResponseEntity<>(replyList, HttpStatus.OK); 
//				
//			} else {
//				// 댓글 등록 실패 : 실패 코드(204)
//				// responseEntity = new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
//				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//			}
//			
		} catch (Exception e) {
			log.error("replyWrite error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;		
	} //
	
}
