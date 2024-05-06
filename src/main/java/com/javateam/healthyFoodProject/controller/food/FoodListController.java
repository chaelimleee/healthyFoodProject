package com.javateam.healthyFoodProject.controller.food;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.healthyFoodProject.dao.FoodDAO;
import com.javateam.healthyFoodProject.domain.FoodVO;
import com.javateam.healthyFoodProject.domain.PageVO;
import com.javateam.healthyFoodProject.service.FoodService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("food")
@Slf4j
public class FoodListController {

	@Autowired
	FoodService foodService;

	@Autowired
	FoodDAO foodDao; 

	@GetMapping("list.do")
	public String list(@RequestParam(value = "currPage", defaultValue = "1") int currPage,
			@RequestParam(value = "limit", defaultValue = "20") int limit,
			Model model) {

		log.info("food 게시글 목록");
		List<FoodVO> foodList = new ArrayList<>();
		

		// // 총 게시글 수
		// int listCount = foodService.selectFoodsCount();
		//
		// foodList = foodService.selectFoodsByPaging(currPage, limit);

		// 총 게시글 수 (댓글들을 제외한)
		int listCount = foodService.selectFoodsCountWithoutReplies();
		log.info("listCount 총 게시글 수 확인 ==> " + listCount);
		// 댓글들 제외
		
		foodList = foodService.selectFoodsByPagingWithoutReplies(currPage, limit);
//		foodList = foodDao.findAllByOrderByFoodCodeDesc();
//		Pageable pageable = PageRequest.of(currPage - 1, limit);
//		foodList = foodService.findAllByOrderByFoodCode();
		//log.info("foodList 리스트 확인 ==> " + foodList.get(0).toString());

		// 총 페이지 수
		// int maxPage=(int)((double)listCount/limit+0.95); //0.95를 더해서 올림 처리
		int maxPage = PageVO.getMaxPage(listCount, limit);
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
		// int startPage = (((int) ((double)currPage / 10 + 0.9)) - 1) * 10 + 1;
		//int startPage = PageVO.getStartPage(currPage, limit);
		int startPage = currPage == 1 ? 1 : PageVO.getStartPage(currPage, limit);//0430 수정. 
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
		int endPage = startPage + 20;

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
		model.addAttribute("foodList", foodList);
		model.addAttribute("listCount", listCount);// 추가 및 수정

		// 0404 leee 페이지네이션 위해서 현재 페이지에 보여줄 시작 페이지, 마지막 페이지 list.html에 보냄
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		// title 0430 레시피
		model.addAttribute("pageTitle", "건강식 레시피");
		model.addAttribute("bgImg", "food4.jpg");
		
		log.info("foodList end : ");

		return "/food/food_list";
	} //

}