package com.javateam.healthyFoodProject.controller.photo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.healthyFoodProject.domain.PhotoVO;
import com.javateam.healthyFoodProject.service.PhotoService;

import lombok.extern.slf4j.Slf4j;

@Controller
//240405- es-song - RequestMapping 위치 수정(to photo_board)
@RequestMapping("photo_board")
@Slf4j
public class PhotoController { 
	
	@Autowired
	PhotoService photoService;
	
	@GetMapping("/write.do")
	public String write(Model model) {
		model.addAttribute("PhotoVO", new PhotoVO());
		
		// title 0430 포토 커뮤니티
		model.addAttribute("pageTitle", "커뮤니티");
		model.addAttribute("bgImg", "bg_strawberry_1.png");
		
		return "/photo_board/photo_write";
	} //
	
	// DTO 대신 Map 형태의 인자 수신 
	@PostMapping("/writeProc.do")
	public String writeProc(@RequestParam Map<String, Object> map, 
							Model model) {
		
		log.info("게시글 쓰기 인자 전송 현황 : ");
		map.entrySet().forEach(arg -> {
			log.info("{}", arg);
		});
		
		PhotoVO PhotoVO = new PhotoVO(map); // Map → VO
		
		// 첨부 파일이 있다면...				
		String msg = ""; // 메시지
		
		log.info("PhotoVO : {}", PhotoVO);
		
		PhotoVO = photoService.insertBoard(PhotoVO);
		
		log.info("----- 답변게시글 저장 PhotoBoardVO : {}", PhotoVO);
		
		if (PhotoVO != null) {
			msg = "게시글 저장에 성공하였습니다.";
		} 
		model.addAttribute("errMsg", msg);
		model.addAttribute("movePage", "/photo_board/list.do"); 
		
		// title 0430 포토 커뮤니티
		model.addAttribute("pageTitle", "커뮤니티");
		model.addAttribute("bgImg", "bg_strawberry_1.png");
		
		return "/error/error"; 
	} //
	
	@GetMapping("/photo_view.do/{boardNum}")
	public String view(@PathVariable("boardNum") int boardNum, Model model) {
		
		
		
		PhotoVO PhotoVO =photoService.selectBoard(boardNum);
		log.info("PhotoVO : {}", PhotoVO);
		
		model.addAttribute("board", PhotoVO);

		// title 0430 포토 커뮤니티
		model.addAttribute("pageTitle", "커뮤니티");
		model.addAttribute("bgImg", "bg_strawberry_1.png");
		
		// 조회할 때마다 조회수 갱신(+)
		photoService.updateBoardReadcountByBoardNum(boardNum);
		
		return "/photo_board/photo_view";
	}
	
}