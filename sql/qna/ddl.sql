CREATE SEQUENCE qna_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 99999
NOCYCLE;
​
CREATE TABLE qna_tbl (
	qna_code NUMBER DEFAULT 0,
	member_email VARCHAR2(50) NOT NULL,-- song 0416 member_email 추가
    member_nick VARCHAR2(50) NOT NULL, -- song 0416 member_nick 추가
	qna_title VARCHAR2(100 CHAR) NOT NULL,
  --  qna_pass varchar2(20) not null,-- song 0419 삭제
    qna_content VARCHAR2(4000) NOT NULL,
    qna_date DATE NOT NULL,
    qna_yesno NUMBER DEFAULT 0,
	qna_original_file NVARCHAR2(200),
	qna_file NVARCHAR2(200),
	qna_re_ref NUMBER NOT NULL,
	qna_re_lev NUMBER NOT NULL,
	qna_re_seq NUMBER NOT NULL,
	qna_readcount NUMBER DEFAULT 0,
--    qna_re_ref_ref NUMBER,
--    qna_re_lev_lev NUMBER,
--    qna_re_seq_seq NUMBER,
	qna_img NVARCHAR2(200),
    qna_img_origin NVARCHAR2(200),
	PRIMARY KEY(qna_code) -- song 0416 boardNum->qna_code로 변경  
);


comment ON COLUMN qna_tbl.QNA_CODE IS '게시글 번호';
comment ON COLUMN qna_tbl.MEMBER_EMAIL IS '작성자 이메일';
comment ON COLUMN qna_tbl.MEMBER_NICK IS '게시글 작성자 별명';
comment ON COLUMN qna_tbl.QNA_TITLE	IS '게시글 제목';
--comment ON COLUMN qna_tbl.QNA_PASS IS '게시글 비밀번호'; --song 0419 삭제
comment ON COLUMN qna_tbl.QNA_CONTENT IS '게시글 내용';
comment ON COLUMN qna_tbl.QNA_DATE IS '게시글 작성일자';
comment ON COLUMN qna_tbl.QNA_YESNO	IS '게시글 답변여부';
comment ON COLUMN qna_tbl.QNA_ORIGINAL_FILE	IS '게시글 첨부 파일(원본)';
comment ON COLUMN qna_tbl.QNA_FILE IS '게시글 첨부 파일(암호화)';
comment ON COLUMN qna_tbl.QNA_RE_REF IS '게시글 댓글의 원 게시글(관련글) 번호';
comment ON COLUMN qna_tbl.QNA_RE_LEV IS	'게시글 댓글 레벨';
comment ON COLUMN qna_tbl.QNA_RE_SEQ IS	'게시글 댓글 순서';
comment ON COLUMN qna_tbl.QNA_READCOUNT	IS '게시글 조회수';
--comment ON COLUMN qna_tbl.QNA_RE_REFREF	IS '게시글 답글의 원 게시글(관련글) 번호';
--comment ON COLUMN qna_tbl.QNA_RE_LEVLEV	IS '게시글 답글 레벨';
--comment ON COLUMN qna_tbl.QNA_RE_SEQSEQ	IS '게시글 답글 순서';
comment ON COLUMN qna_tbl.QNA_IMG IS '게시글 썸네일';
comment ON COLUMN qna_tbl.QNA_IMG_ORIGIN IS '게시글 썸네일 원본';


