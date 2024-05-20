package com.javateam.healthyFoodProject.controller.qna;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.javateam.healthyFoodProject.domain.CustomUser;
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
		
		// title 0506 qna 문의게시판
		model.addAttribute("pageTitle", "1:1 문의게시판");
		model.addAttribute("bgImg", "");
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
		
		//게시글 패스워드(로그인 인증 패스워드) 설정
		// Spring Security Pricipal(Session) 조회
		Object principal = SecurityContextHolder.getContext()
											.getAuthentication()
											.getPrincipal();
		
		CustomUser customUser = (CustomUser)principal;
		log.info("principal : {}", principal);
		
		String pw = customUser.getPassword();
				
		qnaVO.setQnaPass(pw);
		
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
		
		Optional<QnaVO> resultVO = qnaService.insertQna(qnaVO);//0422 song Optional 추가
		
		log.info("----- 게시글 저장 QnaVO : {}", resultVO);
		
		//if (resultVO != null) {
		//0422 song qna->result, isEmpty ==false추가
		if (resultVO.isEmpty() == false) {
			msg = "게시글 저장에 성공하였습니다.";
		}
		model.addAttribute("errMsg", msg);
		model.addAttribute("movePage", "/qna/list.do"); 
		
		// title 0506 qna 문의게시판
		model.addAttribute("pageTitle", "1:1 문의게시판");
		model.addAttribute("bgImg", "");
		
		return "/error/error"; 
	} //
	
	@GetMapping("/qna_view.do/{qnaCode}/{lockYesno}")//0423,0430 song view.do -> qna_view.do, lockYesno 추가
	public String view(@PathVariable("qnaCode") int qnaCode, @PathVariable("lockYesno") String lockYesno, Model model) {
		
		QnaVO qnaVO =qnaService.selectQna(qnaCode);
		log.info("QnaVO : {}", qnaVO);
		String page = "/qna/qna_view";
		
		//잠금글 여부 점검(qnaLockYesno)
		//잠금글인데 아직 인증 안 했을 때
		if (qnaVO.getQnaLockYesno() == 1 && lockYesno.equals("no")) {
			page = "/qna/qna_pass";// 잠금글 패스워드 인증 페이지
		//잠금글인데 인증되었을 때
		//} else if (qnaVO.getQnaLockYesno() == 1 && lockYesno.equals("yes")) {
			
		} else {
			// 조회할 때마다 조회수 갱신(+)
			qnaService.updateQnaReadCountByQnaCode(qnaCode);
		}
		
		model.addAttribute("qna", qnaVO);
		// title 0506 qna 문의게시판
		model.addAttribute("pageTitle", "1:1 문의게시판");
		model.addAttribute("bgImg", "");
		
		return page;
	}
	
}