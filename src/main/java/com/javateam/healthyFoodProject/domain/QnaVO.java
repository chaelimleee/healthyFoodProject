
package com.javateam.healthyFoodProject.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;
//import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javateam.healthyFoodProject.util.FileUploadUtil;

import lombok.extern.slf4j.Slf4j;

@Component //Component어노테이션 달았더니 홈피 실행됨 chaelimleee 0415
@Entity
@Table(name="qna_tbl")
@Slf4j
public class QnaVO implements Serializable { // 10.25 (sesssion으로 변환할 경우 에러 방지)
	
	// 각 필드들에 대한  @Column 및 컬럼명 추가
	
	/**
	 *
	qna_code '게시글 번호';
	qna_title '게시글 제목';
	qna_pass '게시글 비밀번호';
	qna_content '게시글 내용';
	qna_date '게시글 작성일자';
	qna_yesno '게시글 답변여부';
	qna_original_file '게시글 첨부 파일(원본)';
	qna_file '게시글 첨부 파일(암호화)';
	qna_re_ref '게시글 댓글의 원 게시글(관련글) 번호';
	qna_re_lev '게시글 댓글 레벨';
	qna_re_seq '게시글 댓글 순서';
	qna_readcount '게시글 조회수';
	qna_re_refref'게시글 답글의 원 게시글(관련글) 번호';
	qna_re_levlev '게시글 답글 레벨';
	qna_re_seqseq'게시글 답글 순서';
	member_nick '게시글 작성자 별명';
	member_email'게시글 작성자 이메일';
	qna_img '게시글 썸네일'; 
	qna_img_origin '게시글 썸네일 원본';

CREATE SEQUENCE qna_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 99999
NOCYCLE;
​
CREATE TABLE qna_tbl (
	qna_code NUMBER DEFAULT 0,
	qna_title VARCHAR2(100 CHAR) NOT NULL,
    qna_pass VARCHAR2(20) not null,
    qna_content CLOB NOT NULL,
    qna_date DATE NOT NULL,
    qna_yesno NUMBER DEFAULT 0,
	qna_original_file NVARCHAR2(200),
	qna_file NVARCHAR2(200),
	qna_re_ref NUMBER NOT NULL,
	qna_re_lev NUMBER,
	qna_re_seq NUMBER,
	qna_readcount NUMBER DEFAULT 0,
    qna_re_refref NUMBER NOT NULL,
    qna_re_levlev NUMBER NOT NULL,
    qna_re_seqseq NUMBER NOT NULL,
    member_nick VARCHAR(30) NOT NULL,
    member_email VARCHAR(100) NOT NULL,
    qna_img NVARCHAR2(200),
    qna_img_origin NVARCHAR2(200),
	PRIMARY KEY(qna_code)
);
	 */
	
	private static final long serialVersionUID = 1L;

	/** 게시글 번호 */
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
		    generator = "QNA_SEQ_GENERATOR")
			@SequenceGenerator(
			name = "QNA_SEQ_GENERATOR",
			sequenceName = "qna_seq",
			initialValue = 1,
			allocationSize = 1)
	
	@Column(name = "QNA_CODE") 
	private int qnaCode; 
	
	/** 게시글 제목*/
	@Column(name = "QNA_TITLE")
	private String qnaTitle; 
	
//	/** 게시글 비밀번호*/
//	@Column(name = "QNA_PASS")
//	private String qnaPass; 
	
	/** 게시글 내용*/
	@Column(name = "QNA_CONTENT")
	private String qnaContent; 
	
	/** 게시글 작성일자*/
	@CreationTimestamp // 작성 날짜(기본값) 생성
	@Column(name = "QNA_DATE")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") // JSON 변환시 "년월일 및 시분초"까지 모두 출력 
	private Date qnaDate; 	

	/** 게시글 답변여부*/
	@Column(name = "QNA_YESNO")
	private int qnaYesno = 0;
	
	/** 첨부 파일(원래 파일명) */
	@Column(name = "QNA_ORIGINAL_FILE") 
	private String qnaOriginalFile; 	

	/** 첨부 파일(인코딩된 파일명) */
	@Column(name = "QNA_FILE") 
	private String qnaFile; 
	
	/** 게시글 댓글의 원 게시글(관련글) 번호 */
	@Column(name = "QNA_RE_REF") 
	private int qnaReRef; 
	
	/** 게시글 댓글 레벨 */
	@Column(name = "QNA_RE_LEV") 
	private int qnaReLev; 
	
	/** 게시글 댓글 순서 */
	@Column(name = "QNA_RE_SEQ") 
	private int qnaReSeq; 
	
	/** 게시글 조회수 */
	@Column(name = "QNA_READCOUNT")
	private int qnaReadCount = 0;
	
	/** 게시글 작성자 별명*/
	@Column(name = "MEMBER_NICK")
	private String memberNick; 
	
	/** 게시글 작성자 이메일*/
	@Column(name = "MEMBER_EMAIL")
	private String memberEmail;
	
	/** 게시글 썸네일 */
	@Column(name = "QNA_IMG")
	private String qnaImg; 
	
	/** 게시글 썸네일 원본 */
	@Column(name = "QNA_IMG_ORIGIN")
	private String qnaImgOrigin; 
	
//	/** 활성화 여부 */
//	@Column(name = "ENABLED")
//	private int enabled; 
	
	//0415 song 추가
	public QnaVO() {
		
	}
	
	// BoardDTO -> BoardVO
    public QnaVO(QnaDTO qna) {
        
        this.qnaCode = qna.getQnaCode();
        this.qnaTitle = qna.getQnaTitle();
 //       this.qnaPass = qna.getQnaPass();
        this.qnaContent = qna.getQnaContent();
        this.qnaDate = qna.getQnaDate();
        this.qnaYesno = qna.getQnaYesno();
//        this.qnaOriginalFile = qna.getQnaOriginalFile();
        this.qnaFile = qna.getQnaFile().getOriginalFilename();
        this.qnaReRef = qna.getQnaReRef();
        this.qnaReLev = qna.getQnaReLev();
        this.qnaReSeq = qna.getQnaReSeq();
        this.qnaReadCount = qna.getQnaReadCount();
        this.memberEmail = qna.getMemberEmail();
        this.memberNick = qna.getMemberNick();
        this.qnaImg = qna.getQnaImg();
        this.qnaImgOrigin = qna.getQnaImgOrigin();
        
        // 첨부 파일 유무 : 없으면 => "", 있으면 => 암호화 
        this.qnaFile = qna.getQnaFile().getOriginalFilename().trim().equals("") ?
		"" : FileUploadUtil.encodeFilename(qna.getQnaFile().getOriginalFilename());
        		
//        this.enabled = board.getEnabled();
    }
    
    /**
     * @param map
     */
    // 게시글 수정시 : Map<String, Object> => QnaVO
    public QnaVO(Map<String, Object> map) {

    	log.info("QnaVO 오버로딩 생성자 : Map to VO");
    	//song:수정 0412 (qnaImgOrigin,qnaImg 부분 추가)	
    	//0416 song 수정 this->int 
    	//this.qnaCode = Integer.parseInt(map.get("qnaCode").toString());
    	this.qnaCode = Integer.parseInt(map.get("qnaCode").toString());
        this.memberEmail = (String)map.get("memberEmail");
        this.memberNick = map.get("memberNick").toString();
    	this.qnaTitle = (String)map.get("qnaTitle");
  //  	this.qnaPass = (String)map.get("qnaPass");
    	this.qnaContent =(String)map.get("qnaContent");
    	this.qnaDate = (Date)map.get("qnaDate");
    	this.qnaYesno = Integer.parseInt(map.get("qnaYesno").toString());//답변 미답변
    	this.qnaOriginalFile = (MultipartFile)map.get("qnaOriginalFile") == null ? "" : ((MultipartFile)map.get("qnaOriginalFile")).getOriginalFilename(); // 파일명 저장
    	this.qnaFile = (MultipartFile)map.get("qnaFile") == null ? "" : ((MultipartFile)map.get("qnaFile")).getOriginalFilename(); // 파일명 저장
    	this.qnaReRef = Integer.parseInt(map.get("qnaReRef").toString());
    	this.qnaReLev = Integer.parseInt(map.get("qnaReLev").toString());
    	this.qnaReSeq = Integer.parseInt(map.get("qnaReSeq").toString());
    	this.qnaReadCount = Integer.parseInt(map.get("qnaReadCount").toString());

//      this.memberImg = map.get("memberImg").toString();
//      this.boardOrigin = (int) map.get("boardOrigin");
        
//      this.qnaImg= (String) map.get("qnaImg");
//      this.qnaImgOrigin= (String) map.get("qnaImgOrigin");
        this.qnaImgOrigin = (String) map.get("qnaImgOrigin"); // 파일명 저장
    	this.qnaImg = (String) map.get("qnaImg"); // 파일명 저장
    }
    
    // 게시글 등록시 Map 형태로 인자를 받을 경우 : Map<String, Object> => BoardVO
    public QnaVO(Map<String, Object> map, MultipartFile qnaFile) {

    	log.info("QnaVO 오버로딩 생성자 : Map to VO");
    	
    	this.qnaCode = map.get("qnaCode") == null ? 0 : Integer.parseInt(map.get("qnaCode").toString());
        this.memberEmail = (String)map.get("memberEmail");
        this.qnaTitle = (String)map.get("qnaTitle");
        this.qnaContent = (String)map.get("qnaContent");
        log.info("memberNick qna확인:{}", memberNick);
        this.memberNick = map.get("memberNick").toString();
        
        log.info("map.get(\"qnaOriginalFile\") : " + map.get("qnaOriginalFile"));
        
        if (qnaFile.isEmpty() == false) {
        
	        this.qnaOriginalFile = qnaFile.getOriginalFilename(); // 파일명 저장
	        
	        // 암호화 파일 부분 추가
	        // 첨부 파일 유무 : 없으면 => "", 있으면 => 암호화 
	        this.qnaFile = FileUploadUtil.encodeFilename(qnaFile.getOriginalFilename());
	       
        }

        ////////////////////////////////////////////////////////
        
        this.qnaReSeq = map.get("qnaReSeq") == null ? 0 : Integer.parseInt(map.get("qnaReSeq").toString());
        this.qnaDate = (Date)map.get("qnaDate");
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder
		.append("QnaVO [qnaCode=").append(qnaCode)
		.append(", qnaTitle=").append(qnaTitle)
//		.append(", qnaPass=").append(qnaPass)
		.append(", qnaContent=").append(qnaContent)
		.append(", qnaDate=").append(qnaDate)
		.append(", qnaYesno=").append(qnaYesno)
		.append(", qnaOriginalFile=").append(qnaOriginalFile)
		.append(", qnaFile=").append(qnaFile)
		.append(", qnaReRef=").append(qnaReRef)
		.append(", qnaReLev=").append(qnaReLev)
		.append(", qnaReSeq=").append(qnaReSeq)
		.append(", qnaReadCount=").append(qnaReadCount)
		.append(", memberNick=").append(memberNick)
		.append(", memberEmail=").append(memberEmail)
		.append(", qnaImg=").append(qnaImg)
		.append(", qnaImgOrigin=").append(qnaImgOrigin)
		.append("]");
		return builder.toString();
	}
    
	// 게시글 수정시 기존 정보와 수정 정보 동일성 여부 점검시 활용
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((qnaContent == null) ? 0 : qnaContent.hashCode());
		result = prime * result + ((qnaFile == null) ? 0 : qnaFile.hashCode());
		result = prime * result + qnaCode;
		result = prime * result + ((qnaOriginalFile == null) ? 0 : qnaOriginalFile.hashCode());
		result = prime * result + ((qnaTitle == null) ? 0 : qnaTitle.hashCode());
		result = prime * result + ((memberEmail == null) ? 0 : memberEmail.hashCode());
		return result;
	}
    
	
	
	public int getQnaCode() {
		return qnaCode;
	}

	public void setQnaCode(int qnaCode) {
		this.qnaCode = qnaCode;
	}

	public String getQnaTitle() {
		return qnaTitle;
	}

	public void setQnaTitle(String qnaTitle) {
		this.qnaTitle = qnaTitle;
	}

//	public String getQnaPass() {
//		return qnaPass;
//	}
//
//	public void setQnaPass(String qnaPass) {
//		this.qnaPass = qnaPass;
//	}

	public String getQnaContent() {
		return qnaContent;
	}

	public void setQnaContent(String qnaContent) {
		this.qnaContent = qnaContent;
	}

	public Date getQnaDate() {
		return qnaDate;
	}

	public void setQnaDate(Date qnaDate) {
		this.qnaDate = qnaDate;
	}

	public int getQnaYesno() {
		return qnaYesno;
	}

	public void setQnaYesno(int qnaYesno) {
		this.qnaYesno = qnaYesno;
	}

	public String getQnaOriginalFile() {
		return qnaOriginalFile;
	}

	public void setQnaOriginalFile(String qnaOriginalFile) {
		this.qnaOriginalFile = qnaOriginalFile;
	}

	public String getQnaFile() {
		return qnaFile;
	}

	public void setQnaFile(String qnaFile) {
		this.qnaFile = qnaFile;
	}

	public int getQnaReRef() {
		return qnaReRef;
	}

	public void setQnaReRef(int qnaReRef) {
		this.qnaReRef = qnaReRef;
	}

	public int getQnaReLev() {
		return qnaReLev;
	}

	public void setQnaReLev(int qnaReLev) {
		this.qnaReLev = qnaReLev;
	}

	public int getQnaReSeq() {
		return qnaReSeq;
	}

	public void setQnaReSeq(int qnaReSeq) {
		this.qnaReSeq = qnaReSeq;
	}

	public int getQnaReadCount() {
		return qnaReadCount;
	}

	public void setQnaReadCount(int qnaReadCount) {
		this.qnaReadCount = qnaReadCount;
	}

	public String getMemberNick() {
		return memberNick;
	}

	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getQnaImg() {
		return qnaImg;
	}

	public void setQnaImg(String qnaImg) {
		this.qnaImg = qnaImg;
	}

	public String getQnaImgOrigin() {
		return qnaImgOrigin;
	}

	public void setQnaImgOrigin(String qnaImgOrigin) {
		this.qnaImgOrigin = qnaImgOrigin;
	}

	//게시글 수정시 기존 정보와 수정 정보 동일성 여부 점검시 활용
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof QnaVO)) {
			return false;
		}
		QnaVO other = (QnaVO) obj;
		
		if (qnaContent == null) {
			if (other.qnaContent != null) {
				return false;
			}
		} else if (!qnaContent.equals(other.qnaContent)) {
			return false;
		}
		if (qnaFile == null) {
			if (other.qnaFile != null) {
				return false;
			}
		} else if (!qnaFile.equals(other.qnaFile)) {
			return false;
		}
		if (qnaCode != other.qnaCode) {
			return false;
		}
		if (qnaOriginalFile == null) {
			if (other.qnaOriginalFile != null) {
				return false;
			}
		} else if (!qnaOriginalFile.equals(other.qnaOriginalFile)) {
			return false;
		} 
		
		if (qnaTitle == null) {
			if (other.qnaTitle != null) {
				return false;
			}
		} else if (!qnaTitle.equals(other.qnaTitle)) {
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
