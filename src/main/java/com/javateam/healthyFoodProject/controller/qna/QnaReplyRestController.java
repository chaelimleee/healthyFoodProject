package com.javateam.healthyFoodProject.controller.qna;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.javateam.healthyFoodProject.domain.QnaVO;
import com.javateam.healthyFoodProject.service.QnaService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("qna")
@Slf4j
public class QnaReplyRestController {
	
	@Autowired
	QnaService qnaService;

	@PostMapping("replyWrite.do")
	// public ResponseEntity<Boolean> replyWrite(@RequestBody Map<String, Object> map) {
	// 댓글을 작성하면서 즉시 현재까지의 댓글들 현황을 집계하여 리턴
	public ResponseEntity<List<QnaVO>> replyWrite(@RequestBody Map<String, Object> map) {
		
		log.info("replyWrite.do : qnaReRef={}, qnaContent={}, memberEmail={}, memberNick={}", 
				map.get("qnaReRef"), map.get("qnaContent"), map.get("memberEmail"), map.get("memberNick"));
		
		List<QnaVO> replyList = new ArrayList<>();

		ResponseEntity<List<QnaVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			QnaVO qnaVO = new QnaVO();
			
			//
			// 주의사항) 
			// 여기서 댓글의 고유 아이디는 DB를 통해서 생성되므로 원글의 아이디(qnaCode)는 다른 필드에 입력됩니다.
			qnaVO.setMemberEmail(map.get("memberEmail").toString());
			qnaVO.setMemberNick(map.get("memberNick").toString());
			//qnaVO.setQnaPass(map.get("qnaPass").toString());
			qnaVO.setQnaTitle("제목");
			qnaVO.setQnaContent(map.get("qnaContent").toString());
			//qnaVO.setQnaCode(Integer.parseInt(map.get("qnaCode").toString()));
			qnaVO.setQnaReRef(Integer.parseInt(map.get("qnaReRef").toString()));
			//qnaVO.setQnaReLev(1);
			
			log.info("--- result_1 : {}", qnaVO);
			
			// 댓글의 현황을 보면서 댓글 시퀀스 결정
			//0422 song QnaVO-->Optional<QnaVO> (empty체크위해)
			Optional<QnaVO> resultVO = qnaService.insertQna(qnaVO);
			
			log.info("--- resultVO : {}", resultVO);
		
			//0422 song 
			if (resultVO.isEmpty() == false) {
	//		if (qnaVO != null) {
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
				replyList = qnaService.selectReplysById(qnaVO.getQnaReRef());	
				
				log.info("replyList size : : {}", replyList.size());
				
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
	public ResponseEntity<List<QnaVO>> getRepliesAll(@RequestParam("qnaCode") int qnaCode) { 
		
		log.info("getRepliesAll.do : qnaCode={}", qnaCode);
		
		List<QnaVO> replyList = new ArrayList<>();
		ResponseEntity<List<QnaVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
			// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
			replyList = qnaService.selectReplysById(qnaCode);				
			
			//replyList.forEach(x-> { log.info("날짜 : "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(x.getQnaDate())); });
			replyList.forEach(x-> { log.info("날짜 : {}", x.getQnaDate()); });
			
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
	public ResponseEntity<List<QnaVO>> replyUpdate(@RequestBody Map<String, Object> map) { 
		
		log.info("replyUpdate.do : qnaCode={}, qnaContent={}", map.get("qnaCode"), map.get("qnaContent"));
		
		List<QnaVO> replyList = new ArrayList<>();

		ResponseEntity<List<QnaVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		try {
			QnaVO qnaVO = new QnaVO();
			
			int qnaCode = Integer.parseInt(map.get("qnaCode").toString());
			
			// 주의) 댓글 수정에서는 댓글의 아이디가 이미 등록시 발행이 되어 있으므로 댓글의 실제 아이디 !
			qnaVO.setQnaCode(qnaCode);  
			qnaVO.setMemberEmail(map.get("memberEmail").toString());
			//qnaVO.setQnaPass(map.get("qnaPass").toString());
			qnaVO.setQnaTitle("댓글");
			qnaVO.setQnaReRef(Integer.parseInt(map.get("qnaReRef").toString()));
			qnaVO.setQnaContent(map.get("qnaContent").toString());
			qnaVO.setQnaCode(Integer.parseInt(map.get("qnaCode").toString()));
			qnaVO.setMemberNick(map.get("memberNick").toString());
			//qnaVO.setQnaReLev(1);
			qnaVO.setQnaDate(new Date(System.currentTimeMillis()));
			
			log.info("qnaVO : {}", qnaVO);
			
			// 패쓰워드 체크
//			String originalQnaPass = qnaService.selectQna(qnaCode).getQnaPass();
//			
//			boolean isPass = map.get("qnaPass").toString().equals(originalQnaPass) ? true : false;
//			
//			if (isPass == true) {
//		
			// 0422 song qnaVO → Optional<QnaVO> resultVO    
			Optional<QnaVO> resultVO = qnaService.updateQna(qnaVO);
//				
//				log.info("--- result : {}", qnaVO);
//				
//			} else { // 패쓰워드가 틀리면...
//				
//				log.error("게시글 패쓰워드 불일치");
//
//				// Http Status Code : 401
//				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//			} // 
			
			//0422 song Optional<QnaVO> resultVO
			//if (qnaVO != null) {
			
			//0422 song
			if (resultVO.isEmpty() == false) {
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
				replyList = qnaService.selectReplysById(qnaVO.getQnaReRef());//0422 song getQnaReRef로 변경				
				
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
	public ResponseEntity<List<QnaVO>> replyDelete(@RequestBody Map<String, Object> map) { 
		
		log.info("replyDelete.do : qnaCode={}, originalQnaCode={}", 
					map.get("qnaCode"), map.get("originalQnaCode")); //0422 song pass 삭제
		
		List<QnaVO> replyList = new ArrayList<>();

		ResponseEntity<List<QnaVO>> responseEntity = null;
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		int qnaCode = Integer.parseInt(map.get("qnaCode").toString());
		int originalQnaCode = Integer.parseInt(map.get("originalQnaCode").toString());
		//String qnaPass= map.get("qnaPass").toString();
		
		try {
			
			//0422 song 아래 일부 삭제
			// 패쓰워드 체크
			//String originalQnaPass = qnaService.selectQna(qnaCode).getQnaPass();
			
			//log.info("originalQnaPass : {}", originalQnaPass);
			
			//boolean isPass = qnaPass.equals(originalQnaPass) ? true : false;
			
			//log.info("패스워드 일치 여부 : {}", isPass);
			
			boolean result = false; // 삭제 결과
			
			//if (isPass == true) {
				
			result = qnaService.deleteReplysById(qnaCode);
				
			/*
			 * } else { // 패쓰워드가 틀리면...
			 * 
			 * log.error("게시글 패쓰워드 불일치");
			 * 
			 * // Http Status Code : 401 return new
			 * ResponseEntity<>(HttpStatus.UNAUTHORIZED); }
			 */ // 
			
			log.info("삭제 결과 : {}", result);
			
			if (result == true) { // 삭제
				
				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
				replyList = qnaService.selectReplysById(originalQnaCode);				
				
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
	
}