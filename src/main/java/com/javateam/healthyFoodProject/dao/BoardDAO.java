package com.javateam.healthyFoodProject.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.javateam.healthyFoodProject.domain.BoardVO;

// public interface BoardDAO extends JpaRepository<BoardVO, Integer>{
// 페이징 메서드 추출위해 Repository 교체
public interface BoardDAO extends PagingAndSortingRepository<BoardVO, Integer>{
	
	BoardVO save(BoardVO boardVO);
	
	long count();

	Page<BoardVO> findAll(Pageable pageable);
	
	BoardVO findById(int boardCode);
	
	int countByboardTitleLike(String boardTitle); // Like
	int countByboardTitleContaining(String boardTitle); // Containing
	int countByBoardContentContaining(String boardContent);
	int countBymemberEmailContaining(String memberEmail);
	
	Page<BoardVO> findByboardTitleLike(String boardTitle, Pageable pageable); // Like
	Page<BoardVO> findByboardTitleContaining(String boardTitle, Pageable pageable); // Containing
	Page<BoardVO> findByBoardContentContaining(String boardContent, Pageable pageable);
	Page<BoardVO> findBymemberEmailContaining(String memberEmail, Pageable pageable);
	
	// 원글에 따른 소속 댓글들 가져오기
	List<BoardVO> findByBoardReSeq(int boardReSeq); 
	
	// 댓글 제외한 원글들만의 게시글 수 : boardReSeq = 0
	long countByBoardReSeq(int boardReSeq);
	
	// 댓글 제외한 원글들만의 게시글들만 가져오기(페이징) : boardReSeq = 0
	Page<BoardVO> findByBoardReSeq(int boardReSeq, Pageable pageable); 
	
	// 게시글 조회수 갱신
	@Modifying
	@Query(value = "UPDATE board_tbl SET " + 
				   "BOARD_READ_COUNT = BOARD_READ_COUNT + 1 " + 
				   "WHERE BOARD_CODE = :boardCode", nativeQuery = true)
	void updateBoardReadcountByBoardCode(@Param("boardCode") int boardCode);

	// 게시글 삭제
	void deleteById(int boardCode);
}