package com.javateam.healthyFoodProject.controller;

import java.io.IOException;
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
import com.javateam.healthyFoodProject.domain.MemberDTO;
import com.javateam.healthyFoodProject.domain.MemberUpdateDTO;
import com.javateam.healthyFoodProject.service.BoardService;
import com.javateam.healthyFoodProject.service.FileUploadService;
import com.javateam.healthyFoodProject.service.ImageService;
import com.javateam.healthyFoodProject.service.MemberService;
import com.javateam.healthyFoodProject.util.FileUploadUtil;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("sasang")
@Slf4j
public class SasangController { 
	
	@Autowired
	MemberService memberService;
	
	/**
	 * 0412 leee 사상체질 진단 페이지 이동 
	 * @param model
	 * @return
	 */

	@GetMapping("/sasang.do")
	public String sasangPage(Model model) {
		
		log.info("sasang 첫 페이지 ");
		model.addAttribute("arg", "인자");
		
		return "/sasang/sasang_list";
	}
	
	@GetMapping("/saveSasang.do/{memberEmail}/{sasang}")
	@ResponseBody
	public ResponseEntity<String> updateMemberSasang(@PathVariable("memberEmail") String memberEmail,
													@PathVariable("sasang") String sasang) {
		
		log.info("sasang 확인 >> " + sasang +" , memberEmail 확인 >> "+ memberEmail); 
		
		ResponseEntity<String> responseEntity = null;
		String msg = "";
		
		String sasangArray[] = {"소음인","소양인","태음인","태양인"};
		List<String> sasangList = Arrays.asList(sasangArray);
		
		try {
			// 사상체질이 4가지 중에서 잘못 입력되면 
			if(sasangList.contains(sasang) == false) {
				
				msg = "회원정보 체질 수정에 실패하셨습니다.";
				responseEntity = new ResponseEntity<>(msg, HttpStatus.NO_CONTENT);
			} else {
			
				MemberDTO memberDTO = new MemberDTO();
				memberDTO.setMemberEmail(memberEmail);
				memberDTO.setMemberSasang(sasang);
				log.info("memberDTO 확인 >> " + memberDTO );
				
				boolean result = memberService.updateMemberSasang(memberDTO);
				
				log.info("--- result : {}", result);
				
				if (result == true) {
					// 중복된 아이디가 있음 : 성공 코드(200)
					msg = "회원정보 체질 수정에 성공하셨습니다.";
					responseEntity = new ResponseEntity<>(msg, HttpStatus.OK); 
				} else {
					// 중복된 아이디가 없음 : 실패 코드(204)
					msg = "회원정보 체질 수정에 실패하셨습니다.";
					responseEntity = new ResponseEntity<>(msg, HttpStatus.NO_CONTENT);
				}
			}
		} catch (Exception e) {
			log.error("updateMemberSasang error : {}", e);
			e.printStackTrace();

			// 실패 코드(417) : 내부 서버 에러
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
//	@Autowired
//	BoardService boardService;
//	
//	@Autowired
//	FileUploadService fileUPloadService;
//	
//	@Value("${spring.servlet.multipart.max-file-size}") 
//	String uploadFileMaxSize;
//		
//	@GetMapping("/write.do")
//	public String write(Model model) {
//		
//		model.addAttribute("boardDTO", new BoardDTO());
//		return "/board/write";
//	} // 
//	
//	// DTO 대신 Map 형태의 인자 수신 
//	@PostMapping("/writeProc.do") 
//	public String writeProc(@RequestParam Map<String, Object> map, 
//							@RequestPart(value="boardFile") MultipartFile boardFile, 
//							Model model) {
//		
//		log.info("게시글 쓰기 인자 전송 현황 : ");
//		map.entrySet().forEach(arg -> {
//			log.info("{}", arg);
//		});
//		
//		BoardVO boardVO = new BoardVO(map, boardFile); // Map → VO
//		
//		// 첨부 파일이 있다면...				
//		String msg = ""; // 메시지
//		
//		if (boardFile.isEmpty() == false) {
//			
//			log.info("게시글 작성 처리(첨부 파일) : {}", boardFile.getOriginalFilename());
//			
//			String actualUploadFilename = FileUploadUtil.encodeFilename(boardFile.getOriginalFilename());
//			boardVO.setBoardFile(actualUploadFilename);
//
//			// 첨부 파일이 있을 때만 저장 // 됨.
//			msg = fileUPloadService.storeUploadFile(boardVO.getBoardCode(), boardFile, boardVO.getBoardFile());
//			log.info("msg : {}", msg);
//		} 
//		
//		log.info("BoardVO : {}", boardVO);
//		
//		boardVO = boardService.insertBoard(boardVO);
//		
//		log.info("----- 게시글 저장 BoardVO : {}", boardVO);
//		
//		if (boardVO != null) {
//			msg = "게시글 저장에 성공하였습니다.";
//		}
//			
//		// TODO
//		// /error/error
//		// errMsg, movePage = /board/list.do"
//		// 정상 : 파일이 업로드 되었습니다.
//		
//		model.addAttribute("errMsg", msg);
//		model.addAttribute("movePage", "/board/list.do"); 
//		
//		return "/error/error"; 
//	} //
//	
//	@GetMapping("/view.do/{boardCode}")
//	public String view(@PathVariable("boardCode") int boardCode, Model model) {
//		 
//		BoardVO boardVO =boardService.selectBoard(boardCode);
//		log.info("BoardVO : {}", boardVO);
//		
//		model.addAttribute("board", boardVO);
//		
//		// 조회할 때마다 조회수 갱신(+)
//		boardService.updateBoardReadcountByBoardCode(boardCode);
//		
//		return "/board/view";
//	}
	
}