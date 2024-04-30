package com.javateam.healthyFoodProject.domain;

import java.sql.Date;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class QnaDTO {
	
	private int qnaCode; // 게시글 번호
	private String memberNick;//게시글 작성자 별명
	private String memberEmail;//게시글 작성자 이메일
	private String qnaTitle; // 게시글 제목
	private String qnaPass;// 게시글/댓글 비밀번호
	private String qnaContent;//게시글 내용
	private Date qnaDate;//게시글 작성일자
	private int qnaLockYesno; // 게시글 잠금여부
//	private String qnaOriginalFile;// 첨부 파일(원래 파일명)
	private MultipartFile qnaFile; //첨부 파일(인코딩된 파일명)
	private int qnaReRef;//게시글 댓글의 원 게시글(관련글) 번호
	private int qnaReLev;//게시글 댓글 레벨
	private int qnaReSeq;//게시글 댓글 순서
	private int qnaReadCount = 0;//게시글 조회수
//  private int qnaReRefRef;//게시글 답글의 원 게시글(관련글) 번호 //0423 song 주석처리
//  private int qnaReLevLev;//게시글 답글 레벨 //0423 song 주석처리
//  private int qnaReSeqSeq;//게시글 답글 순서 //0423 song 주석처리

	private String qnaImg; // 게시글 썸네일
	private String qnaImgOrigin; // 게시글 썸네일 원본
	
	//private String memberImg; // 게시글 작성자 이미지
	//private int enabled; // 활성화 여부
	//private String textMulti = "text"; // 텍스트 모드(text:기본값) / 멀티미디어 모드(multi)
	
	// 업로드 파일(파일명을 확인할 수 있도록 파일명 인쇄) : boardFile.getOriginalFilename()
	
	@Override
//	public String toString() {
//		return "QnaDTO [qnaCode=" + qnaCode + ", memberEmail=" + memberEmail + ", memberNick=" + memberNick
//				+ ", MERBER_IMG=" + memberImg + ", qnaOrigin=" + qnaOrigin + ", qnaReSeq=" + qnaReSeq
//				+ ", qnaTitle=" + qnaTitle + ", qnaContent=" + qnaContent + ", qnaImg=" + qnaImg
//				+ ", qnaImgOrigin=" + qnaImgOrigin + ", qnaFile=" + qnaFile + ", qnaDate=" + qnaDate
//				+ ", qnaReadCount=" + qnaReadCount + ", textMulti=" + textMulti
//				+ "]";
//	}

	public String toString() {
		return "QnaDTO [qnaCode=" + qnaCode 
				+ ", qnaTitle=" + qnaTitle
				+ ", qnaPass=" + qnaPass
				+ ", qnaContent=" + qnaContent
				+ ", qnaDate=" + qnaDate
				+ ", qnaLockYesno=" + qnaLockYesno
//				+ ", qnaOriginalFile=" + qnaOriginalFile
				+ ", qnaFile=" + qnaFile 
				+ ", qnaReRef=" + qnaReRef
				+ ", qnaReLev=" + qnaReLev
				+ ", qnaReSeq=" + qnaReSeq
				+ ", qnaReadCount=" + qnaReadCount
				+ ", memberEmail=" + memberEmail 
				+ ", memberNick=" + memberNick
				+ ", qnaImg=" + qnaImg
				+ ", qnaImgOrigin=" + qnaImgOrigin 
				+ "]";//textMulti 지움
		
	}
	

}