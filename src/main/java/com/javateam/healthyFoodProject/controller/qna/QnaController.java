package com.javateam.healthyFoodProject.controller.qna;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.javateam.healthyFoodProject.domain.QnaDTO;
import com.javateam.healthyFoodProject.domain.QnaVO;
import com.javateam.healthyFoodProject.service.FileUploadService;
import com.javateam.healthyFoodProject.service.QnaService;
import com.javateam.healthyFoodProject.util.FileUploadUtil;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("qna")
@Slf4j
public class QnaController { 
	
	@Autowired
	QnaService qnaService;
	
	@Autowired
	FileUploadService fileUPloadService;
	
	@Value("${spring.servlet.multipart.max-file-size}") 
	String uploadFileMaxSize;
	
	@GetMapping("/write.do")
	public String write(Model model) {
		
		model.addAttribute("qnaDTO", new QnaDTO());
		return "/qna/qna_write";
	} //
	
	// DTO 대신 Map 형태의 인자 수신 
	@PostMapping("/writeProc.do")
	public String writeProc(@RequestParam Map<String, Object> map,
							@RequestPart(value="qnaFile") MultipartFile qnaFile,
							Model model) {
		
		log.info("게시글 쓰기 인자 전송 현황 qna: ");
		map.entrySet().forEach(arg -> {
			log.info("{}", arg);
		});
		
		QnaVO qnaVO = new QnaVO(map, qnaFile); // Map → VO

		// 첨부 파일이 있다면...				
		String msg = ""; // 메시지
		
		if (qnaFile.isEmpty() == false) {
			
			log.info("게시글 작성 처리(첨부 파일) : {}", qnaFile.getOriginalFilename());
			
			String actualUploadFilename = FileUploadUtil.encodeFilename(qnaFile.getOriginalFilename());
			qnaVO.setQnaFile(actualUploadFilename);

			// 첨부 파일이 있을 때만 저장
			msg = fileUPloadService.storeUploadFile(qnaVO.getQnaCode(), qnaFile, qnaVO.getQnaFile());
			log.info("msg : {}", msg);
		} 
		
		log.info("BoardVO : {}", qnaVO);
		
		qnaVO = qnaService.insertQna(qnaVO);
		
		log.info("----- 게시글 저장 QnaVO : {}", qnaVO);
		
		if (qnaVO != null) {
			msg = "게시글 저장에 성공하였습니다.";
		}
		model.addAttribute("errMsg", msg);
		model.addAttribute("movePage", "/qna/list.do"); 
		
		return "/error/error"; 
	} //
	
	@GetMapping("/view.do/{qnaCode}")
	public String view(@PathVariable("qnaCode") int qnaCode, Model model) {
		
		QnaVO qnaVO =qnaService.selectQna(qnaCode);
		log.info("QnaVO : {}", qnaVO);
		
		model.addAttribute("qna", qnaVO);
		
		// 조회할 때마다 조회수 갱신(+)
		qnaService.updateQnaReadcountByQnaCode(qnaCode);
		
		return "/qna/qna_view";
	}
	
}