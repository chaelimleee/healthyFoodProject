package com.javateam.healthyFoodProject.controller.qna;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.healthyFoodProject.domain.PageVO;
import com.javateam.healthyFoodProject.domain.QnaVO;
import com.javateam.healthyFoodProject.service.QnaService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("qna")
@Slf4j
public class QnaSearchController {
	
	@Autowired
	QnaService qnaService;

	@GetMapping("searchList.do")
	public String list(@RequestParam(value="currPage", defaultValue="1") int currPage,
					   @RequestParam(value="limit", defaultValue="10") int limit,
					   @RequestParam(value="searchKey") String searchKey,
					   @RequestParam(value="searchWord") String searchWord,
					   Model model) {
		
		log.info("게시글 검색 목록");
		log.info("검색 구분 : {}", searchKey);
		log.info("검색어 : {}", searchWord);
		
		List<QnaVO> qnaList = new ArrayList<>();
		
		// 검색시는 "댓글"도 검색에 반영 (기존 대비 변경 없음)
		// 총 "검색" 게시글 수
		int listCount = qnaService.selectQnasCountBySearching(searchKey, searchWord.trim());
		
		qnaList = qnaService.selectQnasBySearching(currPage, limit, searchKey, searchWord.trim());	
		
		// 총 페이지 수
		int maxPage = PageVO.getMaxPage(listCount, limit);
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
		int startPage = PageVO.getStartPage(currPage, limit);
   	    int endPage = startPage + 10;
   	    
   	    if (endPage> maxPage) endPage = maxPage;
   	    
   	    PageVO pageVO = new PageVO();
		pageVO.setEndPage(endPage);
		pageVO.setListCount(listCount);
		pageVO.setMaxPage(maxPage);
		pageVO.setCurrPage(currPage);
		pageVO.setStartPage(startPage);
		
		pageVO.setPrePage(pageVO.getCurrPage()-1 < 1 ? 1 : pageVO.getCurrPage()-1);
		pageVO.setNextPage(pageVO.getCurrPage()+1 > pageVO.getEndPage() ? pageVO.getEndPage() : pageVO.getCurrPage()+1);
	
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("qnaList", qnaList);	

		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchWord", searchWord);
		
		// title 0506 qna 문의게시판
		model.addAttribute("pageTitle", "1:1 문의게시판");
		model.addAttribute("bgImg", "");
		
		return "/qna/qna_list";		
	} //

}