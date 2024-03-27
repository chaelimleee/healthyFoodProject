package com.javateam.healthyFoodProject.domain;

import java.sql.Date;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardDTO {
	
	private int boardCode; // 게시글 번호
	private String memberEmail; // 게시글 작성자 아이디
	private String memberNick; // 게시글 작성자 별명
	private String boardOrigin; // 원게시글 번호
	private int boardReSeq; //게시글 답글 순서
	private String boardTitle; // 게시글 제목
	private String boardContent; // 게시글 내용
	private MultipartFile boardImg; // 게시글 썸네일
	private String boardImgOrigin; // 게시글 썸네일 원본
	private MultipartFile boardFile; // 첨부 파일(인코딩된 파일명)
	private Date boardDate; // 게시글 등록일
	private int boardReadCount = 0 ; // 게시글 조회수
	private int boardDisplay; // 활성화 여부
//	private String textMulti = "text"; // 텍스트 모드(text:기본값) / 멀티미디어 모드(multi)
	
	// 업로드 파일(파일명을 확인할 수 있도록 파일명 인쇄) : boardFile.getOriginalFilename()

}