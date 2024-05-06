package com.javateam.healthyFoodProject.controller.sasang;

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
@RequestMapping("sasang")
@Slf4j
public class SasangListController { 
	
	@Autowired
	FoodService foodService;
	
	@Autowired
	FoodDAO foodDao; 

	//사상별 추천 레시피
	@GetMapping("/list.do/{sasangType}")
	public String SasangList(@PathVariable("sasangType") String sasangType,
							 @RequestParam(value = "currPage", defaultValue = "1") int currPage,
							 @RequestParam(value = "limit", defaultValue = "20") int limit,
							 Model model) {
		
		int listCount = 0;
		
		log.info("page : " + currPage);

		log.info("사상별 레시피 목록 : " + sasangType);
		List<FoodVO> foodList = new ArrayList<>();
		
		//0501 태음인 추천 레시피 추가. 
		foodList = foodService.findSasangGoodIngredientMainBySasangNameAndPaging(currPage, limit, sasangType);
		listCount = foodService.countSasangFoodByTaeumin(sasangType);
		
		log.info("listCount 사상별 음식 개수 ==>" + listCount);
		
		// 총 페이지 수
		// int maxPage=(int)((double)listCount/limit+0.95); //0.95를 더해서 올림 처리
		int maxPage = PageVO.getMaxPage(listCount, limit);
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
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
		
		//0502
		String pageTitle = "<span style='color:#fff;'>" + sasangType + " 추천 레시피</span>";
		
		// 0501
		model.addAttribute("sasangType", sasangType);
		
		// title 0430 사상체질
		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("bgImg", "sasang2.jpg");

		return "/sasang/sasang_one";
	} //
	
	
	
	
}