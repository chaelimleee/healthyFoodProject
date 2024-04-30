package com.javateam.healthyFoodProject.controller.photo;

import java.util.ArrayList;
import java.sql.Date; 
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.javateam.healthyFoodProject.domain.PhotoVO;
import com.javateam.healthyFoodProject.domain.UploadFile;
import com.javateam.healthyFoodProject.service.FileUploadService;
import com.javateam.healthyFoodProject.service.ImageService;
import com.javateam.healthyFoodProject.service.ImageStoreService;
import com.javateam.healthyFoodProject.service.PhotoService;

import lombok.extern.slf4j.Slf4j;

//@SessionAttributes("photoBoardUpdateDTO")
@Controller
@RequestMapping("photo_board")
@Slf4j
public class PhotoUpdateController {

	@Autowired
	PhotoService photoService;

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	ImageService imageService;

	@Autowired
	ImageStoreService imageStoreService;

	@GetMapping("/update.do")
	public String update(@RequestParam("boardNum") int boardNum, Model model, HttpSession session) {

		PhotoVO photoVO = photoService.selectBoard(boardNum);
		// BoardDTO boardDTO = new BoardDTO(boardVO);
		// model.addAttribute("boardDTO", boardDTO);

		log.info("photoVO(update) : {}", photoVO);

		// 기존 정보 세션 생성
		// if (session.getAttribute("boardUpdateSess") == null) {
		session.setAttribute("boardUpdateSess", photoVO);
		// }

		log.info("PhotoVO : {}", photoVO);
		model.addAttribute("board", photoVO);
		return "/photo_board/photo_update"; // 0430
	} //

	@PostMapping("/updateProc.do")
	public String updateProc(@RequestParam Map<String, Object> map, 
							 Model model,
							 HttpSession session) {

		log.info("------ updateProc.do");
		
		log.info("인자 현황 :");
		
		map.entrySet().forEach(x->{ log.info("{}", x);});
		
		// 수정에 실패했을 때는  글수정 화면으로 이동하고, 성공하였을 때는 개별 게시글 보기로 이동하도록 변경 
		// 성공/실패에 따라 선택적으로 화면 이동하기 위해 변수 활용(movePage)
		// 초기 기본값 변경
		// String returnPath = "redirect:/board/view.do/" + map.get("boardNum").toString(); // 리턴(이동) 페이지
		
		String returnPath; // 글수정 "성공/실패" 모두 "/error/error"로 가도록 재설정
		String movePage = "/photo_board/update.do?boardNum=" + map.get("boardNum").toString(); // 리턴(이동) 페이지
		
		String msg = ""; // 메시지

		map.entrySet().forEach(x -> {
			log.info(x + "");
		});

		PhotoVO defaultBoardVO = (PhotoVO) session.getAttribute("boardUpdateSess");
		PhotoVO updateBoardVO = new PhotoVO(map);

		log.info("boardUpdateSession(기존 정보) : {}", defaultBoardVO);
		log.info("수정 정보 : {}", updateBoardVO);

		// 게시글 패쓰워드 검증
		if (defaultBoardVO.getBoardPass().equals(updateBoardVO.getBoardPass().trim())) {


			// 글내용(boardContent) 비교 : 변경시에는 기존 삽입 이미지 삭제 등 처리
			log.info("기존 글내용 : {}", defaultBoardVO.getBoardContent());
			log.info("수정 글내용 : {}", updateBoardVO.getBoardContent());

			/*
			 * *****************************************************************************
			 */

			// 글내용이 실제로 변경되었다면... (서로 내용이 다른 경우)
			if (defaultBoardVO.getBoardContent().trim().equals(updateBoardVO.getBoardContent().trim()) == false) {

				// 기존 데이터의 삽입 이미지 목록(삽입 이미지 테이블(upload_file_tbl)의 PK(기본키)) 확보
				//
				// ex) 글내용중 이미지가 들어간 내용 ex) <img src="/healthyFoodProject/board/image/18" .....

				List<Integer> defaultImgList = photoService.getImageList(defaultBoardVO.getBoardContent().trim(),
						"/board/image/");
				List<Integer> updateImgList = photoService.getImageList(updateBoardVO.getBoardContent().trim(),
						"/board/image/");

				log.info("----------------------------------");

				for (int s : defaultImgList) {
					log.info("--- 기존 업로드 이미지 : " + s);
				} //

				for (int s : updateImgList) {
					log.info("--- 신규 업로드 이미지 : " + s);
				} //

				log.info("----------------------------------");

				// 삭제할 글내용에 삽입 이미지 목록
				List<Integer> deleteExpectedImgList = new ArrayList<>();

				/* ----------------------------------------------------------------------- */

				// 기존에 이미지가 되어 있지만
				// 신규에는 이미지가 없을 때는 기존 이미지 모두 삭제
				
				if (updateImgList.size() == 0) {
					
					log.info("기존 글내용의 모든 이미지 삭제");
					deleteExpectedImgList.addAll(defaultImgList);
					
				} else { // 신규에 이미지 포함시 선택 삭제
					
					log.info("기존글의 이미지들의 선별적 삭제");
					
					if  (defaultImgList.size() > 0) {
					
						for (int s : defaultImgList) {
							
							if (updateImgList.contains(s) == false) { //
								
								log.info("실제 삭제할 기존 이미지 기본키(PK) : " + s);
								deleteExpectedImgList.add(s);
							} //
							
						} // for
					
					} // if 
					
					// 삭제할 이미지들 출력
					deleteExpectedImgList.forEach(x -> {
						log.info("삭제할 이미지 아이디 : {}", x);
					});
					
					log.info("--------------- 삭제할 이미지들 실제 삭제 -------------------");

					// 대상 삽입 이미지 파일 삭제 : 삭제할 이미지 있으면 삭제
				
					if (deleteExpectedImgList.size() > 0) {
					
						for (int imageId : deleteExpectedImgList) {
							
							// 삽입 이미지 테이블(upload_file_tbl)에서 저장경로/파일명 가져옴(file_path 필드)
							// D:/lsh/works/spring_member/healthyFoodProject/upload/image/2024/03/12/a92e1a28f7e746b39afe7e83eb97a5d2.jpg
							UploadFile uploadFile = imageService.load(imageId); // 삭제할 이미지 파일 경로 확보 :
																				// uploadFile.getFilePath()
							// 삽입 이미지 삭제 삭제
							log.info("삭제 메시지 : {}", fileUploadService.deleteImageFile(uploadFile.getFilePath()));
							// 삽입 이미지 테이블(upload_file_tbl)에서도 해당 이미지 수록 내용 삭제
							imageStoreService.deleteById(imageId);
							
							log.info("이미지를 삭제하였습니다.");
						} // for
						
					} // if (deleteExpectedImgList.size() > 0) {
					

				} // if (updateImgList.size() == 0) {

				/* ----------------------------------------------------------------------- */

			} // 글내용이 실제로 변경되었다면... (서로 내용이 다른 경우)

			else { // 변경 내용이 없다면...

				msg = "게시글 수정(변경) 내용이 없습니다.";

			} // if

			// 등록일 => 최근 수정일로 변경
			updateBoardVO.setBoardDate(new Date(System.currentTimeMillis()));

			//0430
			updateBoardVO.setBoardNum(defaultBoardVO.getBoardNum());
			
			log.info("최종 게시글 수정 내용 : {}", updateBoardVO);

			// 게시글 수정			
			PhotoVO resultVO = photoService.updateBoard(updateBoardVO);

			if (resultVO == null) {

				msg = "게시글 수정에 실패하였습니다.";

			} else {

				log.info("최종 저장 결과 : " + resultVO);
				msg = "게시글 수정에 성공하였습니다.";
				
				// 수정에 실패했을 때는  글수정 화면으로 이동하고, 성공하였을 때는 개별 게시글 보기로 이동하도록 변경 
				// 게시글 수정 성공후 개별 게시글 보기로 이동
				movePage = "/photo_board/photo_view.do/" + map.get("boardNum").toString();
			} //

		} else { // 패쓰워드 틀렸을 때

			msg = "게시글 패쓰워드가 틀렸습니다. 다시 입력하십시오.";

		} //

		model.addAttribute("errMsg", msg);

		// 수정에 실패했을 때는  글수정 화면으로 이동하고, 성공하였을 때는 개별 게시글 보기로 이동하도록 변경 
		// 초기값은 메서드 초기에 언급된 지역 변수에서 변경(movePage)
		// model.addAttribute("movePage", "/board/update.do?boardNum=" + map.get("boardNum").toString());
		model.addAttribute("movePage", movePage);
		returnPath = "/error/error"; // 에러 페이지로 이동

		return returnPath;
	}

} //