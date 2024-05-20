package com.javateam.healthyFoodProject.controller.food;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.javateam.healthyFoodProject.domain.FoodDTO;
import com.javateam.healthyFoodProject.domain.FoodVO;
import com.javateam.healthyFoodProject.service.FoodService;
import com.javateam.healthyFoodProject.service.FileUploadService;
import com.javateam.healthyFoodProject.service.ImageService;
import com.javateam.healthyFoodProject.util.FileUploadUtil;

import lombok.extern.slf4j.Slf4j;
/**
 * 건강식 레시피 
 * 게시글보기
 * @author cofla
 *
 */
@Controller
@RequestMapping("food")
@Slf4j
public class FoodController { 

	@Autowired
	FoodService foodService;
	
	@Autowired
	FileUploadService fileUPloadService;
	
	@Value("${spring.servlet.multipart.max-file-size}") 
	String uploadFileMaxSize;
	
	/**
	 * @param model 해당하는 페이지로 타이틀, 사진을 보냄
	 */
	@GetMapping("/write.do")
	public String write(Model model) {
		
		model.addAttribute("foodDTO", new FoodDTO());
		
		model.addAttribute("pageTitle", "건강식 레시피");
		model.addAttribute("bgImg", "food4.jpg");
		return "/food/food_write";
	} // 
	
	/**
	 * 해당하는 레시피의 번호를 foodsevice의 selectFood에 보내서 
	 * 해당 레시피의 모든 정보를 model을 사용해서 food_view.html로 전송.
	 * @param foodCode 레시피 번호
	 * @param model
	 * @return
	 */
	@GetMapping("/view.do/{foodCode}")
	public String view(@PathVariable("foodCode") int foodCode, Model model) {
		
		log.info("FoodCode >> {}", foodCode);
		 
		FoodVO foodVO =foodService.selectFood(foodCode);
		log.info("FoodVO : {}", foodVO);
		
		model.addAttribute("food", foodVO);
		
		model.addAttribute("pageTitle", "건강식 레시피");
		model.addAttribute("bgImg", "food4.jpg");
		
		return "/food/food_view";
	}
	
}