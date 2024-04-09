package com.javateam.healthyFoodProject.domain;

import java.sql.Date;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
/**
 * 수정 완. 0401
 * 0403 leee enabled 없앰. > DB컬럼에 없고, 없애도 될듯.
 * @author leee
 *
 */
@Data
public class BoardDTO {
	
	private int boardCode; // 게시글 번호
	private String memberEmail; // 게시글 작성자 이메일아이디
	private String memberNick; // 게시글 작성자 별명
	private String memberImg; // 게시글 작성자 이미지
	private int boardOrigin; // 원게시글 번호
	private int boardReSeq; //게시글 답글 순서
	private String boardTitle; // 게시글 제목
	private String boardContent; // 게시글 내용
	private String boardImg; // 게시글 썸네일
	private String boardImgOrigin; // 게시글 썸네일 원본
	private MultipartFile boardFile; // 첨부 파일(인코딩된 파일명)
	private Date boardDate; // 게시글 등록일
	private int boardReadCount = 0 ; // 게시글 조회수
//	private int enabled; // 활성화 여부
	private String textMulti = "text"; // 텍스트 모드(text:기본값) / 멀티미디어 모드(multi)
	
	// 업로드 파일(파일명을 확인할 수 있도록 파일명 인쇄) : boardFile.getOriginalFilename()
	
	@Override
	public String toString() {
		return "BoardDTO [boardCode=" + boardCode + ", memberEmail=" + memberEmail + ", memberNick=" + memberNick
				+ ", MERBER_IMG=" + memberImg + ", boardOrigin=" + boardOrigin + ", boardReSeq=" + boardReSeq
				+ ", boardTitle=" + boardTitle + ", boardContent=" + boardContent + ", boardImg=" + boardImg
				+ ", boardImgOrigin=" + boardImgOrigin + ", boardFile=" + boardFile + ", boardDate=" + boardDate
				+ ", boardReadCount=" + boardReadCount + ", textMulti=" + textMulti
				+ "]";
	}
	

}