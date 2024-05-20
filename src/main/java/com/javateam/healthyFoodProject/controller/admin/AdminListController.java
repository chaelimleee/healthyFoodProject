package com.javateam.healthyFoodProject.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.healthyFoodProject.service.QnaService;
import com.javateam.healthyFoodProject.domain.PageVO;
import com.javateam.healthyFoodProject.domain.QnaVO;
import com.javateam.healthyFoodProject.service.QnaService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("admin")
@Slf4j
public class AdminListController {
	
	@Autowired
	QnaService qnaService;

	@GetMapping("/qna_list.do")
	public String list(@RequestParam(value="currPage", defaultValue="1") int currPage,
					   @RequestParam(value="limit", defaultValue="5") int limit,
					   Model model) {
		
		QnaVO qnaVO = new QnaVO();
		
		log.info("게시글 목록");
		List<QnaVO> qnaList = new ArrayList<>();
		List<QnaVO> qnaListOne = new ArrayList<>();
		List<QnaVO> qnaListZero = new ArrayList<>();
		
		// 총 게시글 수 (댓글들을 제외한)
		int listCount = qnaService.selectQnasCountWithoutReplies();
		
		// 답변완료 게시글 수 (댓글들을 제외한)0508
		int listCountOne = qnaService.selectQnasCountOne();
		// 미답변 게시글 수 (댓글들을 제외한)0508
		int listCountZero = qnaService.selectQnasCountZero();
		
		// 댓글들 제외 답변완료 게시글 0508
		qnaListOne = qnaService.selectQnasByPagingWithoutQnaReLevOne(currPage, limit);
		log.info("qnaListOne 확인 : "+ qnaListOne);
		// 댓글들 제외 미답변 게시글 0508
		qnaListZero = qnaService.selectQnasByPagingWithoutQnaReLevZero(currPage, limit);
		
		// 총 페이지 수
   		// int maxPage=(int)((double)listCount/limit+0.95); //0.95를 더해서 올림 처리
		int maxPage = PageVO.getMaxPage(listCount, limit);
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
   		// int startPage = (((int) ((double)currPage / 10 + 0.9)) - 1) * 10 + 1;
		int startPage = PageVO.getStartPage(currPage, limit);
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
   	    int endPage = startPage + 5;
   	    
   	    if (endPage> maxPage) 
   	    	endPage = maxPage;
   	    
   	    PageVO pageVO = new PageVO();
		pageVO.setEndPage(endPage);
		pageVO.setListCount(listCount);
		pageVO.setMaxPage(maxPage);
		pageVO.setCurrPage(currPage);
		pageVO.setStartPage(startPage);
		
		pageVO.setPrePage(pageVO.getCurrPage()-1 < 1 ? 1 : pageVO.getCurrPage()-1);
		pageVO.setNextPage(pageVO.getCurrPage()+1 > pageVO.getEndPage() ? pageVO.getEndPage() : pageVO.getCurrPage()+1);
	
		model.addAttribute("pageVO", pageVO);
		
		//전체 게시글 수 
		model.addAttribute("listCount", listCount);
		
		// 0508 답변완료 게시글수, 게시글
		model.addAttribute("listCountOne", listCountOne);
		model.addAttribute("qnaListOne", qnaListOne);
		
		// 0508 미답변 게시글수, 게시글
		model.addAttribute("listCountZero", listCountZero);
		model.addAttribute("qnaListZero", qnaListZero);
		
		// 0404 leee 페이지네이션 위해서 현재 페이지에 보여줄 시작 페이지, 마지막 페이지 list.html에 보냄
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		log.info("qna 게시글 목록 끝");
		
		// title 0506 qna 문의게시판
		model.addAttribute("pageTitle", "1:1 문의게시판");
		model.addAttribute("bgImg", "bg-tomato.png");
		
		return "/admin/admin_qna_list";		
	} //
	
}