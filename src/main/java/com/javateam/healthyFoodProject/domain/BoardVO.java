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
	
	/** 게시글 작성자 아이디*/
	@Column(name = "MEMBER_EMAIL")
	private String memberEmail; 
	
	/** 원게시글 번호 */
	@Column(name = "BOARD_ORIGIN")
	private String boardOrigin; 
	
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
	
	/** 첨부 파일(원래 파일명) */
	@Column(name = "BOARD_ORIGINNAL_FILE") //----DB 추가해야함 ----/0326_LEE
	private String boardOriginalFile; 
	
	/** 첨부 파일(인코딩된 파일명) */
	@Column(name = "BOARD_FILE") //----DB 추가해야함 ----/0326_LEE
	private String boardFile; 
	
	/** 게시글 등록일 */
	@CreationTimestamp // 작성 날짜(기본값) 생성
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") // JSON 변환시 "년월일 및 시분초"까지 모두 출력 
	@Column(name = "BOARD_DATE")
	private int boardDate; 
	
	/** 활성화 여부 */
	@Column(name = "BOARD_DISPLAY")
	private int boardDisplay; 
	
	//---- 추가 할 것인지. ----/0326_LEE
	/** 게시글 답글 순서 */
//	@Column(name = "board_re_seq")
	private int boardReSeq; 
	
	//---- 추가 할 것인지. ----/0326_LEE
	/** 게시글 조회수 */
//	@Column(name = "board_readcount")
	private int boardReadCount = 0; 
	
	public BoardVO() {}
	
	// BoardDTO -> BoardVO
    public BoardVO(BoardDTO board) {
        
        this.boardCode = board.getboardCode();
        this.memberEmail = board.getmemberEmail();
        this.boardTitle = board.getboardTitle();
        this.boardContent = board.getBoardContent();
        this.boardOriginalFile = board.getBoardFile().getOriginalFilename(); // 파일명 저장
        this.boardFile = board.getBoardFile().getOriginalFilename(); // 파일명 저장
        
        // 첨부 파일 유무 : 없으면 => "", 있으면 => 암호화 
        this.boardFile = board.getBoardFile().getOriginalFilename().trim().equals("") ?
        		"" : FileUploadUtil.encodeFilename(board.getBoardFile().getOriginalFilename());
        		
        this.boardReRef = board.getBoardReRef();
        this.boardReLev = board.getBoardReLev();
        this.boardReSeq = board.getBoardReSeq();
        this.boardReadCount = board.getBoardReadCount();
        this.boardDate = board.getBoardDate();
    }
    
    // 게시글 수정시 : Map<String, Object> => BoardVO
    public BoardVO(Map<String, Object> map) {

    	log.info("BoardVO 오버로딩 생성자 : Map to VO");
    	
    	this.boardCode = Integer.parseInt(map.get("boardCode").toString());
        this.memberEmail = (String)map.get("memberEmail");
        this.boardTitle = (String)map.get("boardTitle");
        this.boardContent = (String)map.get("boardContent");
        this.boardOriginalFile = (MultipartFile)map.get("boardOriginal") == null ? "" : ((MultipartFile)map.get("boardOriginal")).getOriginalFilename(); // 파일명 저장
        // this.boardFile = (MultipartFile)map.get("boardFile") == null ? "" : ((MultipartFile)map.get("boardFile")).getOriginalFilename(); // 파일명 저장
        this.boardReRef = Integer.parseInt(map.get("boardReRef").toString());
        this.boardReLev = Integer.parseInt(map.get("boardReLev").toString());
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
        
	        this.boardOriginalFile = boardFile.getOriginalFilename(); // 파일명 저장
	        
	        // 암호화 파일 부분 추가
	        // 첨부 파일 유무 : 없으면 => "", 있으면 => 암호화 
	        this.boardFile = FileUploadUtil.encodeFilename(boardFile.getOriginalFilename());
        }

        ////////////////////////////////////////////////////////
        
        this.boardReRef = map.get("boardReRef") == null ? 0 : Integer.parseInt(map.get("boardReRef").toString());
        this.boardReLev = map.get("boardReLev") == null ? 0 : Integer.parseInt(map.get("boardReLev").toString());
        this.boardReSeq = map.get("boardReSeq") == null ? 0 : Integer.parseInt(map.get("boardReSeq").toString());
        this.boardDate = (Date)map.get("boardDate");
    }
    
	public int getboardCode() {
		return boardCode;
	}

	public void setboardCode(int boardCode) {
		this.boardCode = boardCode;
	}

	public String getmemberEmail() {
		return memberEmail;
	}

	public void setmemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getboardTitle() {
		return boardTitle;
	}

	public void setboardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getBoardOriginalFile() {
		return boardOriginalFile;
	}

	public void setBoardOriginalFile(String boardOriginalFile) {
		this.boardOriginalFile = boardOriginalFile;
	}

	public String getBoardFile() {
		return boardFile;
	}

	public void setBoardFile(String boardFile) {
		this.boardFile = boardFile;
	}

	public int getBoardReRef() {
		return boardReRef;
	}

	public void setBoardReRef(int boardReRef) {
		this.boardReRef = boardReRef;
	}

	public int getBoardReLev() {
		return boardReLev;
	}

	public void setBoardReLev(int boardReLev) {
		this.boardReLev = boardReLev;
	}

	public int getBoardReSeq() {
		return boardReSeq;
	}

	public void setBoardReSeq(int boardReSeq) {
		this.boardReSeq = boardReSeq;
	}

	public int getBoardReadCount() {
		return boardReadCount;
	}

	public void setBoardReadCount(int boardReadCount) {
		this.boardReadCount = boardReadCount;
	}

	public Date getBoardDate() {
		return boardDate;
	}

	public void setBoardDate(Date boardDate) {
		this.boardDate = boardDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardVO [boardCode=").append(boardCode).append(", memberEmail=").append(memberEmail)
				.append(", boardPass=").append(boardPass).append(", boardTitle=").append(boardTitle)
				.append(", boardContent=").append(boardContent).append(", boardOriginalFile=").append(boardOriginalFile)
				.append(", boardFile=").append(boardFile).append(", boardReRef=").append(boardReRef)
				.append(", boardReLev=").append(boardReLev).append(", boardReSeq=").append(boardReSeq)
				.append(", boardReadCount=").append(boardReadCount).append(", boardDate=").append(boardDate)
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
		result = prime * result + ((boardOriginalFile == null) ? 0 : boardOriginalFile.hashCode());
		result = prime * result + ((boardPass == null) ? 0 : boardPass.hashCode());
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
		if (boardOriginalFile == null) {
			if (other.boardOriginalFile != null) {
				return false;
			}
		} else if (!boardOriginalFile.equals(other.boardOriginalFile)) {
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