package com.javateam.healthyFoodProject.controller.photo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.healthyFoodProject.domain.PhotoVO;
import com.javateam.healthyFoodProject.domain.UploadFile;
import com.javateam.healthyFoodProject.service.FileUploadService;
import com.javateam.healthyFoodProject.service.ImageService;
import com.javateam.healthyFoodProject.service.ImageStoreService;
import com.javateam.healthyFoodProject.service.PhotoService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("photo_board")
@Slf4j
public class PhotoDeleteController {

	@Autowired
	PhotoService photoService;
	
	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	ImageService imageService;

	@Autowired
	ImageStoreService imageStoreService;

	@GetMapping("/deleteProc.do")
	public String updateProc(@RequestParam("boardNum") int boardNum, 
							 @RequestParam("boardWriter") String boardWriter,
							 Model model) {

		log.info("------ deleteProc.do : boardNum : {}, boardWriter : {} ", boardNum, boardWriter);// 0501 song boardPass삭제

		// 개별 게시글 보기로 이동(movePage)
		// 게시글 삭제 성공시에는 게시글 목록으로 이동(이미 삭제되었으므로 이동할 게시글이 없음)
		// 게시글 삭제 실패시에는 게시글 보기로 이동
		String returnPath; // 글삭제 "성공/실패" 모두 "/error/error"로 가도록 재설정
		String movePage = "/photo_board/photo_view.do/" + boardNum; // 리턴(이동) 페이지
		
		String msg = ""; // 메시지
		
		PhotoVO photoVO = photoService.selectBoard(boardNum); // 기존 정보 읽어오기
		
		log.info("기존 정보 : photoVO : {}", photoVO);
		
		// 댓글들 현황 점검
		// 댓글들이 있다면 삭제 못하도록 차단 !
		// 댓글이 없을 경우에 삭제 허용 !
		if (photoService.selectBoardsCountWithReplies(boardNum) > 0) { // 댓글(들)이 있을 경우
			
			msg = "댓글이 있는 원글은 삭제할 수 없습니다.";
			
		} else { // 댓글이 없을 경우 (삭제 가능)
			
			log.info("댓글 없는 원글(삭제 가능한 글)");
			
			log.info("게시글 실제 작성자 : {}", photoVO.getBoardWriter());
			log.info("게시글 작성자(인자) : {}", boardWriter);
			
			// 0501 게시글 작성자 일치 여부 점검 
			if (boardWriter.trim().equals(photoVO.getBoardWriter())) {
				
				log.info("게시글 작성자입니다.");
			
				// 삭제할 삽입 이미지 점검
				List<Integer> deleteImgList = photoService.getImageList(photoVO.getBoardContent().trim(),
						"/photo_board/image/");
	
				for (int s : deleteImgList) {
					log.info("--- 삭제할 업로드  이미지 : " + s);
				} //for
				
				// 삽입 이미지들 삭제
				if (deleteImgList.size() > 0) { // 삭제할 이미지들이 있다면...
					
					for (int imageId : deleteImgList) {
						
						UploadFile uploadFile = imageService.load(imageId); // 삭제할 이미지 파일 경로 확보 :
						// 삽입 이미지 삭제 삭제
						log.info("삭제 메시지 : {}", fileUploadService.deleteImageFile(uploadFile.getFilePath()));
						// 삽입 이미지 테이블(upload_file_tbl)에서도 해당 이미지 수록 내용 삭제
						imageStoreService.deleteById(imageId);
						
					} // for
					
				} // if (deleteImgList.size() > 0)
				
				// 게시글 테이블(board_tbl)에서 게시글 자체를 삭제
				if (photoService.deleteById(boardNum) == true) {
					
					msg = "게시글 삭제에 성공하였습니다.";
					movePage = "/photo_board/list.do"; // 게시글 목록으로 인동
					
				} else {
					
					msg = "게시글 삭제에 실패하였습니다.";
					
				} //

			}//if // 0501 song 주석 추가
			else { //게시글 작성자 일치하지 않을 때 
				
				msg = "게시글 작성자만 글을 삭제할 수 있습니다.";
				
			} // if (boardWriter.trim().equals(photoVO.getBoardWriter())) //0501	
			
		
		} // (boardService.selectBoardsCountWithReplies(boardNum) > 0) 
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		model.addAttribute("errMsg", msg);

		// 초기값은 메서드 초기에 언급된 지역 변수에서 변경(movePage)
		model.addAttribute("movePage", movePage);
		returnPath = "/error/error"; // 에러 페이지로 이동

		return returnPath;
	} //

} //
