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
		
	@GetMapping("/write.do")
	public String write(Model model) {
		
		model.addAttribute("foodDTO", new FoodDTO());
		
		// title 0430 레시피
		model.addAttribute("pageTitle", "건강식 레시피");
		model.addAttribute("bgImg", "food4.jpg");
		return "/food/food_write";
	} // 
	
//	// DTO 대신 Map 형태의 인자 수신 
//	@PostMapping("/writeProc.do") 
//	public String writeProc(@RequestParam Map<String, Object> map, 
//							@RequestPart(value="foodFile") MultipartFile foodFile, 
//							Model model) {
//		
//		log.info("게시글 쓰기 인자 전송 현황 : ");
//		map.entrySet().forEach(arg -> {
//			log.info("{}", arg);
//		});
//		
//		FoodVO foodVO = new FoodVO(map, foodFile); // Map → VO
//		
//		// 첨부 파일이 있다면...				
//		String msg = ""; // 메시지
//		
//		if (foodFile.isEmpty() == false) {
//			
//			log.info("게시글 작성 처리(첨부 파일) : {}", foodFile.getOriginalFilename());
//			
//			String actualUploadFilename = FileUploadUtil.encodeFilename(foodFile.getOriginalFilename());
//			foodVO.setFoodFile(actualUploadFilename);
//
//			// 첨부 파일이 있을 때만 저장 // 됨.
//			msg = fileUPloadService.storeUploadFile(foodVO.getFoodCode(), foodFile, foodVO.getFoodFile());
//			log.info("msg : {}", msg);
//		} 
//		
//		log.info("FoodVO : {}", foodVO);
//		
//		foodVO = foodService.insertFood(foodVO);
//		
//		log.info("----- 게시글 저장 FoodVO : {}", foodVO);
//		
//		if (foodVO != null) {
//			msg = "게시글 저장에 성공하였습니다.";
//		}
//			
//		// TODO
//		// /error/error
//		// errMsg, movePage = /food/list.do"
//		// 정상 : 파일이 업로드 되었습니다.
//		
//		model.addAttribute("errMsg", msg);
//		model.addAttribute("movePage", "/food/list.do"); 
//		
//		return "/error/error"; 
//	} //
	
	@GetMapping("/view.do/{foodCode}")
	public String view(@PathVariable("foodCode") int foodCode, Model model) {
		log.info("FoodCode >> {}", foodCode);
		 
		FoodVO foodVO =foodService.selectFood(foodCode);
		log.info("FoodVO : {}", foodVO);
		
		model.addAttribute("food", foodVO);
		
		// title 0430 레시피
		model.addAttribute("pageTitle", "건강식 레시피");
		model.addAttribute("bgImg", "food4.jpg");
		
		// 조회할 때마다 조회수 갱신(+)
//		foodService.updateFoodReadcountByFoodCode(foodCode);
		
		return "/food/food_view";
	}
	
}