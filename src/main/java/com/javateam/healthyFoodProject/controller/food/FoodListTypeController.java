package com.javateam.healthyFoodProject.controller.food;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.javateam.healthyFoodProject.dao.FoodDAO;
import com.javateam.healthyFoodProject.domain.BoardDTO;
import com.javateam.healthyFoodProject.domain.BoardVO;
import com.javateam.healthyFoodProject.domain.CustomUser;
import com.javateam.healthyFoodProject.domain.FoodVO;
import com.javateam.healthyFoodProject.domain.MemberDTO;
import com.javateam.healthyFoodProject.domain.MemberUpdateDTO;
import com.javateam.healthyFoodProject.domain.PageVO;
import com.javateam.healthyFoodProject.service.BoardService;
import com.javateam.healthyFoodProject.service.FileUploadService;
import com.javateam.healthyFoodProject.service.FoodService;
import com.javateam.healthyFoodProject.service.ImageService;
import com.javateam.healthyFoodProject.service.MemberService;
import com.javateam.healthyFoodProject.util.FileUploadUtil;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("food")
@Slf4j
public class FoodListTypeController { 
	
	@Autowired
	FoodService foodService;
	
	@Autowired
	FoodDAO foodDao; 
	
	/**
	 * 유형별 레시피 조회.
	 * 해당하는 유형을 파라미터로 받아와서 dao로 보냄.
	 * @param foodType 해당 유형의 번호를 가져옴.
	 * @param currPage 페이징을 위한 시작 페이지
	 * @param limit 한 페이지에 20의 게시물을 보여줌.
	 * @param model
	 * @return
	 */
	//사상별 추천 레시피
	@GetMapping("list.do/{foodType}")
	public String SasangList(@PathVariable("foodType") int foodType,
							 @RequestParam(value = "currPage", defaultValue = "1") int currPage,
							 @RequestParam(value = "limit", defaultValue = "20") int limit,
							 Model model) {
		
		int listCount = 0;
		String foodTypeName = "";
		
		log.info("page : " + currPage);

		log.info("건강식 유형 레시피 목록 : " + foodType);
		List<FoodVO> foodList = new ArrayList<>();
		
		// 총 음식 수 
		listCount = foodService.countByFoodType(foodType);
		log.info("listCount 총 게시글 수 확인 ==> " + listCount);
		
		/**
		 * foodCateCode테이블에서 foodType에 해당하는 레시피의 id를 찾아서 
		 * 모든 정보를 가져옴. 
		 */
		foodList = foodService.findByFoodTypeAndFoodCateCode(currPage, limit, foodType );
		
		// 음식유형 이름만 가져옴 0508
		foodTypeName = foodService.findByFoodTypeName(foodType);
		
		// 총 페이지 수
		int maxPage = PageVO.getMaxPage(listCount, limit);
		
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
		int startPage = PageVO.getStartPage(currPage, limit/2);//0519 수정. 
		
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
		int endPage = PageVO.getEndPage(currPage, limit/2);//0519 수정.

		if (endPage > maxPage)
			endPage = maxPage;

		PageVO pageVO = new PageVO();
		pageVO.setEndPage(endPage);
		pageVO.setListCount(listCount);
		pageVO.setMaxPage(maxPage);
		pageVO.setCurrPage(currPage);
		pageVO.setStartPage(startPage);

		pageVO.setPrePage(pageVO.getCurrPage() - 1 < 1 ? 1 : pageVO.getCurrPage() - 1);
//		pageVO.setNextPage(pageVO.getCurrPage() + 1 > pageVO.getEndPage() ? pageVO.getEndPage() : pageVO.getCurrPage() + 1);
		//0519
		pageVO.setNextPage(pageVO.getCurrPage() + 1 > pageVO.getMaxPage() ? pageVO.getMaxPage() : pageVO.getCurrPage() + 1);

		model.addAttribute("pageVO", pageVO);
		model.addAttribute("foodList", foodList);
		model.addAttribute("listCount", listCount);// 추가 및 수정

		// 0404 leee 페이지네이션 위해서 현재 페이지에 보여줄 시작 페이지, 마지막 페이지 list.html에 보냄
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("foodType", foodType);
		
		/**
		 * 헤더의 제목이 전체조회일때와 유형별조회 일 때 다르게 나오도록 함. 
		 */
		// 0508
		if(foodType == 0) {
			// title 0430 레시피
			model.addAttribute("pageTitle", "건강식레시피");
		} else {
			// title 0430 레시피
			model.addAttribute("pageTitle", foodTypeName + "레시피");
		}
		model.addAttribute("bgImg", "food4.jpg");

		return "/food/food_list";
	} //
	
	
	
	
}