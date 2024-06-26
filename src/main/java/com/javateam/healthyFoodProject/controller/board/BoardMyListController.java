package com.javateam.healthyFoodProject.controller.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.healthyFoodProject.domain.BoardVO;
import com.javateam.healthyFoodProject.domain.CustomUser;
import com.javateam.healthyFoodProject.domain.MemberDTO;
import com.javateam.healthyFoodProject.domain.PageVO;
import com.javateam.healthyFoodProject.service.BoardService;
import com.javateam.healthyFoodProject.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
public class BoardMyListController {

	@Autowired
	BoardService boardService;
	
	@Autowired
	MemberService memberService;

	@GetMapping("/member/list.do")
	public String list(@RequestParam(value = "currPage", defaultValue = "1") int currPage,
			@RequestParam(value = "limit", defaultValue = "20") int limit,
			Model model) {
		
		//0507 
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal();

		CustomUser customUser = (CustomUser)principal;
		log.info("principal : {}", principal);
		log.info("memberEmail : {}", customUser.getUsername()); // 로그인 아이디
		
		String id = customUser.getUsername();
		
		/*
		 * MemberDTO memberDTO = memberService.selectMember(id);
		 * 
		 * if (memberDTO == null) { // 에러 처리 model.addAttribute("errMsg",
		 * "회원 정보가 존재하지 않습니다."); return "/error/error"; } else {
		 * model.addAttribute("memberDTO", memberDTO); }
		 */
		
		log.info("게시글 목록");
		List<BoardVO> boardList = new ArrayList<>();

		// // 총 게시글 수
		// int listCount = boardService.selectBoardsCount();
		//
		// boardList = boardService.selectBoardsByPaging(currPage, limit);

		// 총 게시글 수 (댓글들을 제외한)
		int listCount = boardService.selectBoardsCountMemberEmail(id);
		// 댓글들 제외
		boardList = boardService.selectBoardsByPagingWithoutMemberEmail(currPage, limit, id);
		//log.info("boardList 리스트 확인 ==> " + boardList.get(0).toString());
		log.info("list, boardList 확인확인 : "+ listCount + boardList);
		// 총 페이지 수
		// int maxPage=(int)((double)listCount/limit+0.95); //0.95를 더해서 올림 처리
		int maxPage = PageVO.getMaxPage(listCount, limit);
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
		// int startPage = (((int) ((double)currPage / 10 + 0.9)) - 1) * 10 + 1;
		int startPage = 1;
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
		int endPage = startPage + 10;

		if (endPage > maxPage)
			endPage = maxPage;

		PageVO pageVO = new PageVO();
		pageVO.setEndPage(endPage);
		pageVO.setListCount(listCount);
		pageVO.setMaxPage(maxPage);
		pageVO.setCurrPage(currPage);
		pageVO.setStartPage(startPage);

		pageVO.setPrePage(pageVO.getCurrPage() - 1 < 1 ? 1 : pageVO.getCurrPage() - 1);
		pageVO.setNextPage(pageVO.getCurrPage() + 1 > pageVO.getEndPage() ? pageVO.getEndPage() : pageVO.getCurrPage() + 1);

		model.addAttribute("pageVO", pageVO);
		model.addAttribute("boardList", boardList);
		model.addAttribute("listCount", listCount);// 추가 및 수정

		// 0404 leee 페이지네이션 위해서 현재 페이지에 보여줄 시작 페이지, 마지막 페이지 list.html에 보냄
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		// title board 커뮤니티
		model.addAttribute("pageTitle", "내가 작성한 글");
		model.addAttribute("bgImg", "food5.png");

		return "/member/member_my_list";
	} //

} 