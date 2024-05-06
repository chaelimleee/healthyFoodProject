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

import com.javateam.healthyFoodProject.domain.BoardDTO;
import com.javateam.healthyFoodProject.domain.BoardVO;
import com.javateam.healthyFoodProject.domain.CustomUser;
import com.javateam.healthyFoodProject.domain.FoodVO;
import com.javateam.healthyFoodProject.domain.MemberDTO;
import com.javateam.healthyFoodProject.domain.MemberUpdateDTO;
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
public class SasangController { 
	
	@Autowired
	FoodService foodService;
	
	/**
	 * 0412 leee 사상체질 진단 페이지 이동 
	 * @param model
	 * @return
	 */

	@GetMapping("/sasang.do")
	public String sasangPage(Model model) {
		
		log.info("sasang 첫 페이지 ");
		model.addAttribute("arg", "인자");
		
		// title 0430 사상체질
		model.addAttribute("pageTitle", "사상체질");
		model.addAttribute("bgImg", "sasang2.jpg");
		
		return "sasang/sasang_home";
	}

	@GetMapping("/sasangTest.do")
	public String sasangPTestPage(Model model) {
		
		log.info("sasang 첫 페이지 ");
		model.addAttribute("arg", "인자");
		
		// title 0430 사상체질
		model.addAttribute("pageTitle", "사상체질");
		model.addAttribute("bgImg", "sasang2.jpg");
		
		return "sasang/sasang_list";
	}
	
	@GetMapping("/sasangResult.do/{sasang}")
	public String sasangResult(@PathVariable("sasang") String sasang, Model model) {
		
		log.info("sasang 결과 페이지");
		
		//0424 leee 추가 추천 레시피 뜨게 함. 
		List<FoodVO> sasangFoodListName = new ArrayList<>();
		sasangFoodListName = foodService.findSasangGoodIngredientMainBySasangName(sasang);
		
		//log.info("sasang 재료 잘 나오는지 확인 >> " + sasangFoodListName.get(0).toString());
		
		// 재료가 컬럼 안에 하나만 있는 것만 가져옴 
//		List<FoodVO> sasangFoodList = foodService.findByFoodIngredientMainInsideIn(sasangFoodListName);
		
		//재료를 잘 뺴왔으면 food_tbl 의 재료들에서 포함하는 레시피들을 모두 가져와야함.0424 
		//List<FoodVO> sasangFoodList = foodService.findByFoodIngredientMainInsideLike(sasang);
		//log.info("sasangFoodList 음식 잘 나오는지 확인 >> " + sasangFoodListName.get(0));
		
		model.addAttribute("sasangFoodList", sasangFoodListName);
		model.addAttribute("sasangName", sasang);
		
		// title 0430 사상체질
		model.addAttribute("pageTitle", "사상체질");
		model.addAttribute("bgImg", "sasang2.jpg");
		
		return "sasang/sasang_Result";
	}
	
	
	
}