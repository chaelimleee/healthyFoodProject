/**
 * 
 */
package com.javateam.healthyFoodProject.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;

/**
 * @author oracle
 *
 */
@Entity
@Table(name="photo_tbl")
@Slf4j
public class PhotoVO implements Serializable { // 10.25 (sesssion으로 변환할 경우 에러 방지)
	
	// 각 필드들에 대한  @Column 및 컬럼명 추가
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 게시글 번호 */
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
		    generator = "PHOTO_SEQ_GENERATOR")
			@SequenceGenerator(
			name = "PHOTO_SEQ_GENERATOR",
			sequenceName = "photo_seq",
			initialValue = 1,
			allocationSize = 1)
	@Column(name = "board_num") 
	private int boardNum;  	
	
	// 0411 leee 파일 이름을 가져오기 위해 조인을 사용하기 위해 관계 설정 필드. 
//	@OneToOne // 엔티티 간의 일대일 관계 매핑. 
//    @JoinColumn(name = "") // 외부 키 지정.  id 컬럼을 외부 키로 사용하여 업로드파일 tbl과 연결. 
	@OneToOne(fetch = FetchType.LAZY) // 지연 로딩 설정
	    @JoinColumn(name = "board_num", referencedColumnName = "id")
	    private UploadFile uploadFile;
	
//	@Transient
//    private UploadFile uploadFile;
	
	/** 게시글 작성자 */
	@Column(name = "board_writer")
	private String boardWriter; 
	
	/** 게시글 제목 */
	@Column(name = "board_subject")
	private String boardSubject; 
	
	/** 게시글 내용 */
	@Column(name = "board_content")
	private String boardContent; 
	
	/** 게시글 답글의 원 게시글(관련글) 번호 */
	@Column(name = "board_re_ref")
	private int boardReRef; 
	
	/** 게시글 답글 레벨 */
	@Column(name = "board_re_lev")
	private int boardReLev; 
	
	/** 게시글 답글 순서 */
	@Column(name = "board_re_seq")
	private int boardReSeq; 
	
	/** 게시글 조회수 */
	@Column(name = "board_readcount")
	private int boardReadCount = 0; 
	
	/** 게시글 작성일자 */
	@Column(name = "board_date")
	@CreationTimestamp // 작성 날짜(기본값) 생성
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") // JSON 변환시 "년월일 및 시분초"까지 모두 출력 
	private Date boardDate;
	
	public PhotoVO() {}
	
    // 게시글 수정시 : Map<String, Object> => PhotoVO
    public PhotoVO(Map<String, Object> map) {
    	
    	log.info("PhotoVO 오버로딩 생성자 : Map to VO");
    	//시퀀스로 생성-->초기값이 null
    	
    	this.boardWriter = (String)map.get("boardWriter");
    	this.boardSubject = (String)map.get("boardSubject");
    	this.boardContent = (String)map.get("boardContent");
    	this.boardReRef = map.get("boardReRef") == null ? 0: Integer.parseInt(map.get("boardReRef").toString());
    	this.boardReLev = map.get("boardReLev") == null ? 0: Integer.parseInt(map.get("boardReLev").toString());
    	this.boardReSeq = map.get("boardReSeq") == null ? 0: Integer.parseInt(map.get("boardReSeq").toString());
    	this.boardDate = map.get("boardReSeq") == null ? new Date(System.currentTimeMillis()) : (Date)map.get("boardDate");
    }
    
	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	
	public String getBoardWriter() {
		return boardWriter;
	}

	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}

	public String getBoardSubject() {
		return boardSubject;
	}

	public void setBoardSubject(String boardSubject) {
		this.boardSubject = boardSubject;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
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
		builder.append("PhotoVO [boardNum=").append(boardNum)
				.append(", uploadFile").append(uploadFile)
				.append(", boardWriter=").append(boardWriter)
				.append(", boardSubject=").append(boardSubject)
				.append(", boardContent=").append(boardContent)
				.append(", boardReRef=").append(boardReRef)
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
		result = prime * result + boardNum;
		result = prime * result + ((boardSubject == null) ? 0 : boardSubject.hashCode());
		result = prime * result + ((boardWriter == null) ? 0 : boardWriter.hashCode());
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
		if (!(obj instanceof PhotoVO)) {
			return false;
		}
		PhotoVO other = (PhotoVO) obj;
		
		if (boardContent == null) {
			if (other.boardContent != null) {
				return false;
			}
		} else if (!boardContent.equals(other.boardContent)) {
			return false;
		}
		if (boardNum != other.boardNum) {
			return false;
		}
		
		if (boardSubject == null) {
			if (other.boardSubject != null) {
				return false;
			}
		} else if (!boardSubject.equals(other.boardSubject)) {
			return false;
		}
		if (boardWriter == null) {
			if (other.boardWriter != null) {
				return false;
			}
		} else if (!boardWriter.equals(other.boardWriter)) {
			return false;
		}
		return true;
	}
    
}