package com.javateam.healthyFoodProject.controller.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javateam.healthyFoodProject.domain.CustomUser;
import com.javateam.healthyFoodProject.domain.MemberDTO;
import com.javateam.healthyFoodProject.domain.MemberUpdateDTO;
import com.javateam.healthyFoodProject.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/join.do")
	public String join(Model model) {
		
		log.info("join.do");		
		
		model.addAttribute("memberDTO", new MemberDTO());
		
		// title 0430
		model.addAttribute("pageTitle", "회원가입");
		
		return "/member/member_join";
	}
	
//    @ResponseBody // 값 변환을 위해 꼭 필요함
//	@GetMapping("idCheck") // 아이디 중복확인을 위한 값으로 따로 매핑
//	public int overlappedID(MemberDTO memberDTO) throws Exception{
//		int result = memberService.overlappedID(memberDTO); // 중복확인한 값을 int로 받음
//		return result;
//	}
	
	@PostMapping("/joinProc.do")
	public String joinProc(@ModelAttribute("memberDTO") MemberDTO memberDTO, 
						RedirectAttributes ra) {
		
		log.info("joinProc.do : {}", memberDTO);
		
		// TODO
		// 패쓰워드 암호화
		bCryptPasswordEncoder = new BCryptPasswordEncoder();
		memberDTO.setMemberPw(bCryptPasswordEncoder.encode(memberDTO.getMemberPw()));
		log.info("joinProc.do : {}", memberDTO.getMemberPw());
		boolean result = memberService.insertMemberRole(memberDTO);
		
		String msg = "";		
		String movePath = "";
		
		if (result == true) {
			
			msg = "회원정보 저장에 성공하였습니다.";
			// 로그인 경로로 이동
			movePath = "redirect:/login";
			
		} else {
			
			msg = "회원정보 저장에 실패하였습니다.";
			ra.addFlashAttribute("msg", msg);
			
			movePath = "redirect:/member/join.do";
		}
		
		log.info("result : {}", msg);
				
		return movePath; 
	}	
	
//	@GetMapping("/view.do")
//	@ResponseBody
//	public String view(@RequestParam("id") String id, Model model) {
//		
//		return memberService.selectMember(id).toString();
//	}
	
	@GetMapping("/view.do")
	// public String view(@RequestParam("id") String id, Model model) {
	public String view(Model model) {
		
		// Spring Security Pricipal(Session) 조회
		// 0411 leee 세션에서 가져옴. 아이디를. 
		Object principal = SecurityContextHolder.getContext()
											.getAuthentication()
											.getPrincipal();
		
		CustomUser customUser = (CustomUser)principal;
		log.info("principal : {}", principal);
		log.info("id : {}", customUser.getUsername()); // 로그인 아이디
		
		String id = customUser.getUsername();
		
		MemberDTO memberDTO = memberService.selectMember(id);
		log.info("memberDTO 확인>> "+memberDTO);
		
		if (memberDTO == null) {
			// 에러 처리
			model.addAttribute("errMsg", "회원 정보가 존재하지 않습니다.");
			return "/error/error";
		} else {
			model.addAttribute("memberDTO", memberDTO);
		}
		
		// title 0430
		model.addAttribute("pageTitle", "마이페이지");
		
		return "/member/member_view";	
	}
	
	@GetMapping("/update.do")
	public String update(Model model) {
		
		log.info("회원정보 수정");
		
		// Spring Security Pricipal(Session) 조회
		Object principal = SecurityContextHolder.getContext()
											.getAuthentication()
											.getPrincipal();
		
		CustomUser customUser = (CustomUser)principal;
		log.info("principal : {}", principal);
		log.info("id : {}", customUser.getUsername()); // 로그인 아이디
		
		String id = customUser.getUsername();
		
		MemberDTO memberDTO = memberService.selectMember(id);
		
		if (memberDTO == null) {
			// 에러 처리
			model.addAttribute("errMsg", "회원 정보가 존재하지 않습니다.");
			return "/error/error";
			
		} else {
			
			// 주의) ClassCastException 발생 가능성 있음
			// MemberUpdateDTO memberUpdateDTO = (MemberUpdateDTO)memberDTO;
			MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO(memberDTO);
			// model.addAttribute("memberDTO", memberDTO);
			model.addAttribute("memberUpdateDTO", memberUpdateDTO);
			
			log.info("기존 회원 정보 : {}", memberUpdateDTO);
		}
		
		// title 0430
		model.addAttribute("pageTitle", "회원정보수정");
		
		return "/member/member_update";	
	}
	
	@PostMapping("/updateProc.do")
	public String updateProc(@ModelAttribute("memberUpdateDTO") MemberUpdateDTO memberUpdateDTO, 
						RedirectAttributes ra) {
		
		log.info("회원정보 수정 처리 : {}", memberUpdateDTO);
		
		// 신규 패쓰워드가 공백이 아니라면 패쓰워드 변경
		// 공백이면 패쓰워드 변경 의사가 없는 것으로 간주하여 기존 패쓰워드 그대로 사용
		if (memberUpdateDTO.getPassword1().trim().equals("") != true) {
			// 패쓰워드 암호화		
			// 주의) 변경된 패쓰워드(password1 혹은 password2) => 암호화 => 기존 패쓰워드(password)에 대입
			bCryptPasswordEncoder = new BCryptPasswordEncoder();
			memberUpdateDTO.setMemberPw(bCryptPasswordEncoder.encode(memberUpdateDTO.getPassword1()));				
		}
		
		log.info("updateProc.do-1(암호화 이후) : {}", memberUpdateDTO);
		
		String msg = "";
		String movePath = "";
				
		boolean result = memberService.updateMember(memberUpdateDTO);
		log.info("result Update ==> " + result); // false 나옴
		
		if (result == true) {
			
			msg = "회원정보 정보 수정에 성공하였습니다.";
			movePath = "redirect:/member/view.do";
		
		} else {
			
			msg = "회원정보 정보 수정에 실패하였습니다.";		
			movePath = "redirect:/member/update.do";
		}
		
		log.info("result : {}", msg);
		ra.addFlashAttribute("msg", msg);
			
		return movePath; 
	}	
	
//	@GetMapping("/delete.do")
//	public String memberDelete(Model model) {		
//		log.info("join.do");		
//		
//		model.addAttribute("memberDTO", new MemberDTO());
//		
//		return "/member/delete";
//	}
	
	//회원 탈퇴
	@GetMapping("/delete.do")
	// public String memberDelete(@ModelAttribute("memberDTO") MemberDTO memberDTO)
	// {
	public String memberDelete(Model model) {
		log.info("delete.do");

		// Spring Security Pricipal(Session) 조회
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		CustomUser customUser = (CustomUser) principal;
		log.info("principal : {}", principal);
		log.info("id : {}", customUser.getUsername()); // 로그인 아이디
		
		String id = customUser.getUsername(); // email
		log.info("id 확인 : {}",id); // 로그인 아이디

		String msg = "";
		String movePage = "/";
		
		// 삭제할 아이디 전송. 0418 leee
		if(memberService.deleteMember(id) == true ) {
			msg = "회원 탈퇴가 완료되었습니다.";
			
			// 로그아웃 처리
			movePage= "/logout";
		}else {
			msg = "회원 탈퇴가 실패했습니다.";
			movePage = "/member/member_view";
		}
		model.addAttribute("errMsg", msg);
		model.addAttribute("movePage", movePage);
		
		// title 0430
		model.addAttribute("pageTitle", "탈퇴");

		return "/error/error";
	}
	
}