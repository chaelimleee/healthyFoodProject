/**
 * 
 */
package com.javateam.healthyFoodProject.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;
//import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javateam.healthyFoodProject.util.FileUploadUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author oracle
 *
 */
@Entity
@Table(name="board_tbl")
@Slf4j
public class BoardVO implements Serializable { // 10.25 (sesssion으로 변환할 경우 에러 방지)
	
	// 각 필드들에 대한  @Column 및 컬럼명 추가
	
	/**
	 * 
	 * 	BOARD_CODE	       게시글 번호
		MEMBER_EMAIL	   회원 이메일
		MEMBER_NICK	       회원 별명
		MERBER_IMG	       회원 이미지
		BOARD_ORIGIN	   원게시글 번호
		BOARD_RE_SEQ	   답글 순서
		BOARD_TITLE	       게시글 제목
		BOARD_CONTENT	   게시글 내용
		BOARD_IMG	       게시글 썸네일
		BOARD_IMG_ORIGIN   게시글 썸네일 원본
		BOARD_FILE	       게시글 파일
		BOARD_FILE_ORIGIIN 게시글 파일 원본
		BOARD_DATE	       게시글 등록일
		BOARD_READ_COUNT   게시글 조회수
		BOARD_DISPLAY	   게시글 활성화
	 */
	private static final long serialVersionUID = 1L;

	/** 게시글 번호 */
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
		    generator = "BOARD_SEQ_GENERATOR")
			@SequenceGenerator(
			name = "BOARD_SEQ_GENERATOR",
			sequenceName = "board_seq",
			initialValue = 1,
			allocationSize = 1)
	@Column(name = "BOARD_CODE") 
	private int boardCode; 
	
	/** 게시글 작성자 이메일 아이디*/
	@Column(name = "MEMBER_EMAIL")
	private String memberEmail; 
	
	/** 게시글 작성자 별명*/
	@Column(name = "MEMBER_NICK")
	private String memberNick; 

	/** 게시글 작성자 이미지*/
	@Column(name = "MERBER_IMG")
	private String memberImg; 
	
	/** 원게시글 번호 */
	@Column(name = "BOARD_ORIGIN")
	private int boardOrigin; 
	
	/** 게시글 답글 순서 */
	@Column(name = "BOARD_RE_SEQ")
	private int boardReSeq; 
	
	/** 게시글 제목 */
	@Column(name = "BOARD_TITLE")
	private String boardTitle; 
	
	/** 게시글 내용 */
	@Column(name = "BOARD_CONTENT")
	private String boardContent; 
	
	/** 게시글 썸네일 */
	@Column(name = "BOARD_IMG")
	private String boardImg; 
	
	/** 게시글 썸네일 원본 */
	@Column(name = "BOARD_IMG_ORIGIN")
	private String boardImgOrigin; 
	
	/** 첨부 파일(인코딩된 파일명) */
	@Column(name = "BOARD_FILE") 
	private String boardFile; 
	
	/** 첨부 파일(원래 파일명) */
	@Column(name = "BOARD_FILE_ORIGINNAL") 
	private String boardFileOriginal; 
	
	/** 게시글 등록일 */
	@CreationTimestamp // 작성 날짜(기본값) 생성
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") // JSON 변환시 "년월일 및 시분초"까지 모두 출력 
	@Column(name = "BOARD_DATE")
	private Date boardDate; 
	
	/** 게시글 조회수 */
	@Column(name = "BOARD_READ_COUNT")
	private int boardReadCount = 0; 

	/** 활성화 여부 */
	@Column(name = "BOARD_DISPLAY")
	private int boardDisplay; 
	
	public BoardVO() {}
	
	/**
	 * 수정 완. 0328 lee
	 * 멤버 닉네임, 이미지는 굳이 외래키 해야하는지 의문. 
	 * 그냥 이메일만 받아오면 닉네임이랑 이미지 둘 다 알 수 있는 거 아닌가?
	 * 
	 * -->0401 강사님 말씀. 제약사항에 너무 얽매이지 말고 일단 하고 나중에 add하셈.
	 * @param board
	 */
	// BoardDTO -> BoardVO
    public BoardVO(BoardDTO board) {
        
        this.boardCode = board.getBoardCode();
        this.memberEmail = board.getMemberEmail();
        this.memberNick = board.getMemberNick();
        this.memberImg = board.getMemberImg();
        this.boardOrigin = board.getBoardOrigin();
        this.boardReSeq = board.getBoardReSeq(); 
        this.boardTitle = board.getBoardTitle();
        this.boardContent = board.getBoardContent();
        this.boardImg= board.getBoardImg();
        this.boardImgOrigin= board.getBoardImgOrigin();
        this.boardFileOriginal = board.getBoardFile().getOriginalFilename(); // 파일명 저장
        this.boardFile = board.getBoardFile().getOriginalFilename(); // 파일명 저장
        
        // 첨부 파일 유무 : 없으면 => "", 있으면 => 암호화 
        this.boardFile = board.getBoardFile().getOriginalFilename().trim().equals("") ?
        		"" : FileUploadUtil.encodeFilename(board.getBoardFile().getOriginalFilename());
        		
        this.boardDate = board.getBoardDate();
        this.boardReadCount = board.getBoardReadCount();
        this.boardDisplay = board.getBoardDisplay();
    }
    
    /**
     * 수정 완. 0328 lee
     * @param map
     */
    // 게시글 수정시 : Map<String, Object> => BoardVO
    public BoardVO(Map<String, Object> map) {

    	log.info("BoardVO 오버로딩 생성자 : Map to VO");
    	
    	this.boardCode = Integer.parseInt(map.get("boardCode").toString());
        this.memberEmail = (String)map.get("memberEmail");
        this.memberNick = map.get("memberNick").toString();
        this.memberImg = map.get("memberImg").toString();
        this.boardOrigin = (int) map.get("boardOrigin");
        this.boardImg= (String) map.get("boardImg");
        this.boardImgOrigin= (String) map.get("boardImgOrigin");
        this.boardTitle = (String)map.get("boardTitle");
        this.boardContent = (String)map.get("boardContent");
        this.boardFileOriginal = (MultipartFile)map.get("boardOriginal") == null ? "" : ((MultipartFile)map.get("boardOriginal")).getOriginalFilename(); // 파일명 저장
        // this.boardFile = (MultipartFile)map.get("boardFile") == null ? "" : ((MultipartFile)map.get("boardFile")).getOriginalFilename(); // 파일명 저장
        this.boardReSeq = Integer.parseInt(map.get("boardReSeq").toString());
        // this.boardReadCount = Integer.parseInt(map.get("boardReadCount").toString()); // 조회수 제외
        this.boardDate = (Date)map.get("boardDate");
    }
    
    // 추가 : 2024.3
    // 게시글 등록시 Map 형태로 인자를 받을 경우 : Map<String, Object> => BoardVO
    public BoardVO(Map<String, Object> map, MultipartFile boardFile) {

    	log.info("BoardVO 오버로딩 생성자 : Map to VO");
    	
    	// 교정 : 2024.3
    	// this.boardCode = Integer.parseInt(map.get("boardCode").toString());
    	this.boardCode = map.get("boardCode") == null ? 0 : Integer.parseInt(map.get("boardCode").toString());
    	
        this.memberEmail = (String)map.get("memberEmail");
        this.boardTitle = (String)map.get("boardTitle");
        this.boardContent = (String)map.get("boardContent");
        
        ////////////////////////////////////////////////////////
        //
        // 교정 : 2024.3 
        log.info("map.get(\"boardOriginal\") : " + map.get("boardOriginal"));
        
        if (boardFile.isEmpty() == false) {
        
	        this.boardFileOriginal = boardFile.getOriginalFilename(); // 파일명 저장
	        
	        // 암호화 파일 부분 추가
	        // 첨부 파일 유무 : 없으면 => "", 있으면 => 암호화 
	        this.boardFile = FileUploadUtil.encodeFilename(boardFile.getOriginalFilename());
        }

        ////////////////////////////////////////////////////////
        
        this.boardReSeq = map.get("boardReSeq") == null ? 0 : Integer.parseInt(map.get("boardReSeq").toString());
        this.boardDate = (Date)map.get("boardDate");
    }
    
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardVO [boardCode=").append(boardCode)
				.append(", memberEmail=").append(memberEmail)
				.append(", boardTitle=").append(boardTitle)
				.append(", boardContent=").append(boardContent)
				.append(", boardFileOriginal=").append(boardFileOriginal)
				.append(", boardFile=").append(boardFile)
				.append(", boardReSeq=").append(boardReSeq)
				.append(", boardReadCount=").append(boardReadCount)
				.append(", boardDate=").append(boardDate)
				.append("]");
		return builder.toString();
	}

	
	// 게시글 수정시 기존 정보와 수정 정보 동일성 여부 점검시 활용
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardContent == null) ? 0 : boardContent.hashCode());
		result = prime * result + ((boardFile == null) ? 0 : boardFile.hashCode());
		result = prime * result + boardCode;
		result = prime * result + ((boardFileOriginal == null) ? 0 : boardFileOriginal.hashCode());
		result = prime * result + ((boardTitle == null) ? 0 : boardTitle.hashCode());
		result = prime * result + ((memberEmail == null) ? 0 : memberEmail.hashCode());
		return result;
	}
	
	// 게시글 수정시 기존 정보와 수정 정보 동일성 여부 점검시 활용
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BoardVO)) {
			return false;
		}
		BoardVO other = (BoardVO) obj;
		
		if (boardContent == null) {
			if (other.boardContent != null) {
				return false;
			}
		} else if (!boardContent.equals(other.boardContent)) {
			return false;
		}
		if (boardFile == null) {
			if (other.boardFile != null) {
				return false;
			}
		} else if (!boardFile.equals(other.boardFile)) {
			return false;
		}
		if (boardCode != other.boardCode) {
			return false;
		}
		if (boardFileOriginal == null) {
			if (other.boardFileOriginal != null) {
				return false;
			}
		} else if (!boardFileOriginal.equals(other.boardFileOriginal)) {
			return false;
		} 
		
		if (boardTitle == null) {
			if (other.boardTitle != null) {
				return false;
			}
		} else if (!boardTitle.equals(other.boardTitle)) {
			return false;
		}
		if (memberEmail == null) {
			if (other.memberEmail != null) {
				return false;
			}
		} else if (!memberEmail.equals(other.memberEmail)) {
			return false;
		}
		return true;
	}
    
}