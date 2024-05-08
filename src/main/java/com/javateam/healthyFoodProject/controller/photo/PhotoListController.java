package com.javateam.healthyFoodProject.controller.photo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.healthyFoodProject.domain.PageVO;
import com.javateam.healthyFoodProject.domain.PhotoVO;
import com.javateam.healthyFoodProject.domain.UploadFile;
import com.javateam.healthyFoodProject.service.PhotoService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/photo_board")
@Slf4j
public class PhotoListController {

	@Autowired
	PhotoService photoService;

	@GetMapping("/list.do")
	public String list(@RequestParam(value = "currPage", defaultValue = "1") int currPage,
			@RequestParam(value = "limit", defaultValue = "20") int limit,
			Model model) {

		PhotoVO photovo = new PhotoVO();
//		UploadFile filevo = new UploadFile();
		
		log.info("게시글 목록");
		List<PhotoVO> photoList = new ArrayList<>();
//		List<PhotoVO> selectImg = new ArrayList<>();

		// 총 게시글 수 (댓글들을 제외한)
		int listCount = photoService.selectBoardsCountWithoutReplies();
		
		// 댓글들 제외
		photoList = photoService.selectBoardsByPagingWithoutReplies(currPage, limit);
		
		if(photoList.size() > 0) {
			
			for(int i = 0 ; i < photoList.size() ; i++) {
				
				PhotoVO photoVO = photoList.get(i);
				
				// grid에 맞게 width : 300px으로 변경. 
				
				String temp = photoVO.getBoardContent();
				// before ==> style="width: 686.021px;" 
				// after ==> style="width: 300px;" 문자열 가공. 
				int index = temp.lastIndexOf("width");
				
				int endIndex = temp.lastIndexOf("px");
				
				String result = "";
				
				// case1 : 단위가 %일 경우. 
				if(endIndex == -1) {
					
					endIndex = temp.lastIndexOf("%");
					result = temp.substring(index, endIndex + 1);
					log.info("result ==> " + result);

				} else {

					// case2 : 단위가 px일 경우. 
					result = temp.substring(index, endIndex + 2);
					log.info("result ==> " + result);
				}

				temp = temp.replaceAll(result, "width:300px");
				
				log.info("temp ==> " + temp); //<img src="/healthyFoodProject/photo_board/image/4" style="width: 686.021px;"><br>
				log.info("index==> " + index );
				log.info("endIndex==> " + endIndex );
				
				//그림만 출력하도록 다른 컨텐츠 삭제 및 배제. 전제 조건 : 그림이 한개만 들어가는 상황.
				int contentBeginIdx = temp.indexOf("<img");
				//int contentEndIdx = temp.indexOf("\">");
				//temp = temp.substring(contentBeginIdx, contentEndIdx + 1); // 뒤에 endindex 없애야 함 .
				temp = temp.substring(contentBeginIdx);
				log.info("temp ==> " + temp); //<img src="/healthyFoodProject/photo_board/image/4" style="width: 686.021px;"><br>
				log.info("index==> " + index );
				log.info("endIndex==> " + endIndex );
				
				photoList.get(i).setBoardContent(temp); // temp가 width가 바뀐 상태 였기 때문에 result가 아닌 temp를 넣어야 한다 .
				
//				photoList.set(i, photoVO);
				log.info("photoVO == > " + photoList.get(i));
			}
			
			log.info("포토 보드 번호 >>>" + photovo.getBoardNum());
			log.info("photoList 이미지 o인덱스 >>>" + photoList.get(0).getBoardSubject() +  photoList.get(0).getBoardContent());
//			log.info("photoList 이미지 1인덱스>>>" + photoList.get(1).getBoardContent());
			
			//leee 0409 이미지 이름 가져오기 
//			selectImg = photoService.selectBoardsImg(filevo.getBoardNum());
//			selectImg = photoService.findSubjectAndFileNameByBoardNum(photoList.get(0).getBoardNum());
//			selectImg = photoService.selectBoardsImg(photovo.getBoardNum());
//			selectImg = photoService.selectPhotoAndFileName(photovo.getBoardNum()); // 0411 leee 수정
			
//			log.info("selectImg 포토 보드 번호 더 길게 >>>" + selectImg.toString());
//			selectImg 포토 보드 번호 더 길게 >>> [PhotoVO [boardNum=1, boardWriter=user1234@naver.com, boardPass=users112!, 
//						boardSubject=바게트빵, boardContent=<img style="width: 685.903px;" 
//						src="/healthyFoodProject/photo_board/image/1"><br>, boardReRef=0, 
//						boardReLev=0, boardReSeq=0, boardReadCount=0, boardDate=2024-04-09]]

		} // if(photoList.size() > 0) {
				
		// 총 페이지 수
		// int maxPage=(int)((double)listCount/limit+0.95); //0.95를 더해서 올림 처리
		int maxPage = PageVO.getMaxPage(listCount, limit);
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
		// int startPage = (((int) ((double)currPage / 10 + 0.9)) - 1) * 10 + 1;
		int startPage = PageVO.getStartPage(currPage, limit);
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
		int endPage = startPage + 10;
		log.info("maxPage ==> "+ maxPage);

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
		model.addAttribute("photoList", photoList);
		model.addAttribute("listCount", listCount);
//		model.addAttribute("selectImg", selectImg);
//		log.info("포토 보드 번호 >>>" + selectImg);
		
		// 0404 leee 페이지네이션 위해서 현재 페이지에 보여줄 시작 페이지, 마지막 페이지 list.html에 보냄
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		// title 0430 포토 커뮤니티
		model.addAttribute("pageTitle", "커뮤니티");
		model.addAttribute("bgImg", "bg_strawberry_1.png");

		return "/photo_board/photo_list";
	} //

}