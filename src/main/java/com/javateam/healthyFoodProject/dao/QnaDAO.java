package com.javateam.healthyFoodProject.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.javateam.healthyFoodProject.domain.QnaVO;

// public interface QnaDAO extends JpaRepository<QnaVO, Integer>{
// 페이징 메서드 추출위해 Repository 교체
public interface QnaDAO extends PagingAndSortingRepository<QnaVO, Integer>{
	
	//0422 song QnaVO-->Optional<QnaVO> (empty체크위해)
	Optional<QnaVO> save(QnaVO qnaVO);
	
	long count();

	Page<QnaVO> findAll(Pageable pageable);
	
	QnaVO findById(int qnaCode);
	
	int countByQnaTitle(String qnaTitle);
	int countByQnaTitleContaining(String qnaTitle); // Containing
	int countByQnaContentContaining(String qnaContent);
	int countByMemberNickContaining(String memberNick);
	
	//Page<QnaVO> findByQnaTitleLike(String qnaTitle, Pageable pageable); // Like
	Page<QnaVO> findByQnaTitleContaining(String qnaTitle, Pageable pageable); // Containing
	Page<QnaVO> findByQnaContentContaining(String qnaContent, Pageable pageable);
	Page<QnaVO> findByMemberNickContaining(String memberNick, Pageable pageable);
	
	// 원글에 따른 소속 댓글들 가져오기
	List<QnaVO> findByQnaReRef(int qnaReRef); //0422 song findByQnaReSeq-->findByQnaReRef
	
	// 댓글 제외한 원글들만의 게시글 수 : qnaReRef = 0 //0422 song countByQnaReSeq(int qnaReSeq)-->countByQnaReRef(int qnaReRef)
	long countByQnaReRef(int qnaReRef);
	
	//  0508 관리자 문의 게시판
	long countByQnaReRefAndQnaReLev(int qnaReRef, int qnaReLev);
	
	// 댓글 제외한 원글들만의 게시글들만 가져오기(페이징) : qnaReSeq = 0
	Page<QnaVO> findByQnaReRef(int qnaReRef, Pageable pageable); //0422 song Seq -> Ref 
	
	// 댓글 제외한 원글들만의 게시글들만 가져오기(페이징) : qnaReSeq = 0
	Page<QnaVO> findByQnaReRefAndQnaReLev(int qnaReRef, Pageable pageable, int qnaReLev); //0422 song Seq -> Ref 
	
//	// 게시글 조회수 갱신
	@Modifying
	@Query(value = "UPDATE qna_tbl SET " + 
				   "QNA_READCOUNT = QNA_READCOUNT + 1 " + 
				   "WHERE QNA_CODE = :qnaCode", nativeQuery = true)
	void updateQnaReadCountByQnaCode(@Param("qnaCode") int qnaCode);

//	// 0508 답변 완료로 바꿈 .
	@Modifying
	@Query(value = "update qna_tbl set qna_re_lev = 1 where qna_code = :qnaCode ", nativeQuery = true)
	void uadateQnaReLev(@Param("qnaCode") int qnaCode);
	
	// 게시글 삭제
	void deleteById(int qnaCode);

//	List<QnaVO> findByQnaReRefOrderByQnaDateAsc(int qnaCode);
//
//	int countByQnaReRef(int boardReRef);
//
//	Page<QnaVO> findByQnaReRef(int i, Pageable pageable);

//	void updateQnaReadcountByQnaCode(int qnaCode);

}