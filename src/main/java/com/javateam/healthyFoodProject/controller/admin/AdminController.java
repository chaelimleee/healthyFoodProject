package com.javateam.healthyFoodProject.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.healthyFoodProject.domain.MemberDTO;
import com.javateam.healthyFoodProject.domain.PageVO;
import com.javateam.healthyFoodProject.service.MemberService;

import lombok.extern.slf4j.Slf4j;

/**
 * 관리자 컨트롤러
 * @author cofla
 *
 */

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

	@Autowired
	MemberService memberService;
	
	/**
	 * 관리자 로그인 시 메인화면.
	 * @return
	 */
	@GetMapping("/")
	public String admin() {
		
		log.info("관리자 화면 admin");
		// return "redirect:/admin/viewAllWithRoles";
		
		return "/";
	}
	/**
	 * 관리자 1대1 문의 알림 게시판
	 * @return
	 */
	// 0430  추가
	@GetMapping("/adminQna.do")
	public String adminQnaD() {
		
		log.info("관리자 화면 adminQna.do");
		// return "redirect:/admin/viewAllWithRoles";
		return "/admin/demo";
	}
	
	@GetMapping("/viewAll.do")
	public String adminView(@RequestParam(value="currPage", defaultValue="1", required=true) int currPage, 
							@RequestParam(value="limit", defaultValue="10") int limit,
							Model model) {
		
		log.info("관리자 화면");
		List<MemberDTO> members = new ArrayList<>();
		
		members = memberService.selectMembersByPaging(currPage, limit);
		
		// 총 인원 수
		int listCount = memberService.selectMembersCount();
		
		log.info("총 인원수 : {}", listCount);
		
		// 총 페이지 수
   		// int maxPage=(int)((double)listCount/limit+0.95); //0.95를 더해서 올림 처리
		int maxPage = PageVO.getMaxPage(listCount, limit);
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
   		// int startPage = (((int) ((double)currPage / 10 + 0.9)) - 1) * 10 + 1;
		int startPage = PageVO.getStartPage(currPage, limit);
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
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
		model.addAttribute("members", members);
		
		// title 0430 레시피
		model.addAttribute("pageTitle", "회원관리");
		model.addAttribute("bgImg", "food4.jpg");
		
		return "/admin/admin_view_all";
	} //
	
	/** 
	 * 모든 회원 정보 확인.
	 * @param currPage 페이징 한페이지에 10개의 게시글을 보여줌.
	 * @param limit
	 * @param model
	 * @return
	 */
	@GetMapping("/viewAllWithRoles.do")
	public String adminViewWithRoles(@RequestParam(value="currPage", defaultValue="1", required=true) int currPage, 
									 @RequestParam(value="limit", defaultValue="10") int limit,
									 Model model) {
		
		log.info("관리자 화면 : role 표시");
		List<Map<String, Object>> members = new ArrayList<>();
		
		members = memberService.selectMembersWithRolesByPaging(currPage, limit);
		
		// 총 인원 수
		int listCount = memberService.selectMembersCount();
		
		log.info("총 인원수 : {}", listCount);
		
		// 총 페이지 수
		int maxPage = PageVO.getMaxPage(listCount, limit);
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
		int startPage = PageVO.getStartPage(currPage, limit);
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
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
		model.addAttribute("members", members);
		
		// title 0430 레시피
		model.addAttribute("pageTitle", "회원관리");
		model.addAttribute("bgImg", "bg-tomato.png");
		
		return "/admin/admin_view_all_with_roles";
	} //

} //