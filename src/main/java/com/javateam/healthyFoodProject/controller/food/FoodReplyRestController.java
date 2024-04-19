package com.javateam.healthyFoodProject.controller.food;

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

import com.javateam.healthyFoodProject.domain.FoodVO;
import com.javateam.healthyFoodProject.service.FoodService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("food")
@Slf4j
public class FoodReplyRestController {
	/**
	 * 0401 leee 수정 완. 하지만 패스워드 부분 손 봐야함.
	 * 
	 */
	@Autowired
	FoodService foodService; 

//	@PostMapping("replyWrite.do")
//	// public ResponseEntity<Boolean> replyWrite(@RequestBody Map<String, Object> map) {
//	// 댓글을 작성하면서 즉시 현재까지의 댓글들 현황을 집계하여 리턴
//	public ResponseEntity<List<FoodVO>> replyWrite(@RequestBody Map<String, Object> map) {
//		
//		log.info("replyWrite.do : foodCode={}, foodContent={}", map.get("foodCode"), map.get("foodContent"));
//		
//		List<FoodVO> replyList = new ArrayList<>();
//
//		// ResponseEntity<Boolean> responseEntity = null; 
//		ResponseEntity<List<FoodVO>> responseEntity = null;
//		
//		HttpHeaders responseHeaders = new HttpHeaders();
//		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
//		
//		try {
//			FoodVO foodVO = new FoodVO();
//			
//			//
//			// 주의사항) 
//			// 여기서 댓글의 고유 아이디는 DB를 통해서 생성되므로 원글의 아이디(foodCode)는 다른 필드에 입력됩니다.
//			foodVO.setMemberEmail(map.get("memberEmail").toString());
//			foodVO.setMemberNick(map.get("memberNick").toString());
//			foodVO.setFoodTitle("댓글");
//			foodVO.setFoodContent(map.get("foodContent").toString());
//			foodVO.setFoodOrigin(Integer.parseInt(map.get("foodCode").toString()));
////			foodVO.setFoodReLev(1);
//			
//			// 댓글의 현황을 보면서 댓글 시퀀스 결정
//			foodVO = foodService.insertFood(foodVO);
//			
//			log.info("--- result : {}", foodVO);
//			
//			if (foodVO != null) {
//				
//				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
//				replyList = foodService.selectReplysById(foodVO.getFoodOrigin());				
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
//		} catch (Exception e) {
//			log.error("replyWrite error : {}", e);
//			e.printStackTrace();
//
//			// 실패 코드(417) : 내부 서버 에러
//			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		
//		return responseEntity;		
//	} //
//	
//	@GetMapping("getRepliesAll.do")
//	public ResponseEntity<List<FoodVO>> getRepliesAll(@RequestParam("foodCode") int foodCode) { 
//		
//		log.info("getRepliesAll.do : foodCode={}", foodCode);
//		
//		List<FoodVO> replyList = new ArrayList<>();
//		ResponseEntity<List<FoodVO>> responseEntity = null;
//		
//		HttpHeaders responseHeaders = new HttpHeaders();
//		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
//		
//		try {
//			// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
//			// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
//			replyList = foodService.selectReplysById(foodCode);				
//			
//			// replyList.forEach(x-> { log.info("날짜 : {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(x.getFoodDate())); });
//			replyList.forEach(x-> { log.info("날짜 : {}", x.getFoodDate()); });//주석 뺐음, 수정
//			
//			// 댓글들이 있다면...
//			if (replyList.size() > 0) {
//
//				// 댓글 등록 성공 : 성공 코드(200)
//				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
//				responseEntity = new ResponseEntity<>(replyList, HttpStatus.OK); 
//				
//			} else {
//				// 댓글 등록 실패 : 실패 코드(204)
//				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//			}
//			
//		} catch (Exception e) {
//			log.error("getRepliesAll error : {}", e);
//			e.printStackTrace();
//
//			// 실패 코드(417) : 내부 서버 에러
//			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		
//		return responseEntity;		
//	} //
//	
//	@PostMapping("replyUpdate.do")
//	public ResponseEntity<List<FoodVO>> replyUpdate(@RequestBody Map<String, Object> map) { 
//		
//		log.info("replyUpdate.do : foodCode={}, foodContent={}", map.get("foodCode"), map.get("foodContent"));
//		
//		List<FoodVO> replyList = new ArrayList<>();
//
//		ResponseEntity<List<FoodVO>> responseEntity = null;
//		
//		HttpHeaders responseHeaders = new HttpHeaders();
//		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
//		
//		try {
//			FoodVO foodVO = new FoodVO();
//			
//			int foodCode = Integer.parseInt(map.get("foodCode").toString());
//			
//			// 주의) 댓글 수정에서는 댓글의 아이디가 이미 등록시 발행이 되어 있으므로 댓글의 실제 아이디 !
//			foodVO.setFoodCode(foodCode);  
//			foodVO.setMemberEmail(map.get("memberEmail").toString());
//			foodVO.setMemberNick(map.get("memberNick").toString());
//			foodVO.setFoodTitle("댓글");
//			foodVO.setFoodContent(map.get("foodContent").toString());
//			foodVO.setFoodOrigin(Integer.parseInt(map.get("foodCode").toString()));
//			foodVO.setFoodDate(new Date(System.currentTimeMillis()));
//			
//			log.info("foodVO : {}", foodVO);
//			
////			// 패쓰워드 체크
////			String originalMemberEmail = foodService.selectFood(foodCode).getMemberEmail();
//////			
////			boolean isPass = map.get("memberEmail").toString().equals(originalMemberEmail) ? true : false;
////			
////			if (isPass == true) {
////				
////				foodVO = foodService.updateFood(foodVO);
////				
////				log.info("--- result : {}", foodVO);
////				
////			} else { // 패쓰워드가 틀리면...
////				
////				log.error("게시글 패쓰워드 불일치");
////
////				// Http Status Code : 401
////				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
////			} // 
//			
//			if (foodVO != null) {
//				
//				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
//				replyList = foodService.selectReplysById(foodVO.getFoodOrigin());				
//				
//				// 댓글 등록 성공 : 성공 코드(200)
//				// responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
//				
//				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라이언트에 전송)
//				responseEntity = new ResponseEntity<>(replyList, HttpStatus.OK); 
//				
//			} else {
//				// 댓글 등록 실패 : 실패 코드(204)
//				// responseEntity = new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
//				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//			}
//			
//		} catch (Exception e) {
//			log.error("replyWrite error : {}", e);
//			e.printStackTrace();
//
//			// 실패 코드(417) : 내부 서버 에러
//			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		
//		return responseEntity;		
//	} //
//	
//	@PostMapping("replyDelete.do")
//	public ResponseEntity<List<FoodVO>> replyDelete(@RequestBody Map<String, Object> map) { 
//		
//		log.info("replyDelete.do : foodCode={}, originalfoodCode={}, memberEmail={}", 
//					map.get("foodCode"), map.get("originalfoodCode"), map.get("memberEmail"));
//		
//		List<FoodVO> replyList = new ArrayList<>();
//
//		ResponseEntity<List<FoodVO>> responseEntity = null;
//		
//		HttpHeaders responseHeaders = new HttpHeaders();
//		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
//		
//		int foodCode = Integer.parseInt(map.get("foodCode").toString());
//		int originalfoodCode = Integer.parseInt(map.get("originalfoodCode").toString());
//		String foodPass= map.get("memberEmail").toString();
//		
//		try {
//			
//			// 패쓰워드 체크
////			String originalFoodPass = foodService.selectFood(foodCode).getFoodPass();
//			
////			log.info("originalFoodPass : {}", originalFoodPass);
////			
////			boolean isPass = foodPass.equals(originalFoodPass) ? true : false;
//			
////			log.info("패스워드 일치 여부 : {}", isPass);
////			
////			boolean result = false; // 삭제 결과
////			
////			if (isPass == true) {
////				
////				result = foodService.deleteReplysById(foodCode);
////				
////			} else { // 패쓰워드가 틀리면...
////				
////				log.error("게시글 패쓰워드 불일치");
////
////				// Http Status Code : 401
////				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
////			} // 
////			
////			log.info("삭제 결과 : {}", result);
////			
////			if (result == true) { // 삭제
////				
////				// 원글에 따른 전체 댓글 현황 목록(리스트) 가져오기 => 리턴 => Client(웹 브라우저)
////				replyList = foodService.selectReplysById(originalfoodCode);				
////				
////				// 댓글 등록 성공 : 성공 코드(200)
////				// responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
////				
////				// 원글에 따른 전체 댓글 현황 목록(리스트) 리턴(클라리언트에 전송)
////				responseEntity = new ResponseEntity<>(replyList, HttpStatus.OK); 
////				
////			} else {
////				// 댓글 등록 실패 : 실패 코드(204)
////				// responseEntity = new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
////				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
////			}
////			
//		} catch (Exception e) {
//			log.error("replyWrite error : {}", e);
//			e.printStackTrace();
//
//			// 실패 코드(417) : 내부 서버 에러
//			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		
//		return responseEntity;		
//	} //
	
}
