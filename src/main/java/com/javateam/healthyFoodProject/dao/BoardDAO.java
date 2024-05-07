package com.javateam.healthyFoodProject.dao;

import java.util.List;
import java.util.Optional;

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
	
	BoardVO findByBoardCodeAndBoardOrigin(int boardCode, int boardOrigin);
	
	List<BoardVO> findByBoardOrigin(int boardOrigin);

	BoardVO findById(int boardCode);

	int countByMemberEmailLike(String memberEmail); // Like
	int countByboardTitleLike(String boardTitle); // Like
	int countByboardTitleContaining(String boardTitle); // Containing
	//0425 song boardContent (LONG데이터 타입) like검색 불능 패치
	//int countByBoardContentContaining(String boardContent);
//	int countBymemberEmailContaining(String memberEmail);
	
	@Query(value= "select count(*) from board_tbl where contains(board_content, '%' || :boardContent || '%') > 0"
			,nativeQuery = true)
	int countByBoardContentContaining(@Param("boardContent") String boardContent);
	
	int countByMemberNickContaining(String memberNick);
	
	Page<BoardVO> findByboardTitleLike(String boardTitle, Pageable pageable); // Like
	Page<BoardVO> findByboardTitleContaining(String boardTitle, Pageable pageable); // Containing
	
	//0425 song boardContent (LONG데이터 타입) like검색 불능 패치
	//Page<BoardVO> findByBoardContentContaining(String boardContent, Pageable pageable);
	@Query(value = "SELECT *  "
				 + "FROM (SELECT m.*,  "
				 + "             FLOOR((ROWNUM - 1) / :limit + 1) page  "
				 + "      FROM ("
				 + "             SELECT * "
				 + "			 FROM board_tbl "
				 + "			 WHERE contains(board_content, '%' || :boardContent || '%') > 0 "
				 + "             AND board_origin = 0 "
				 + "             ORDER BY board_code DESC "
				 + "           ) m  "
				 + "      )  "
				 + "WHERE page = :page ", nativeQuery = true)
//	0425 song List<BoardVO> findByBoardContentContaining(@Param("boardContent") String boardContent, @Param("page")int page, @Param("limit") int limit);
	List<BoardVO> findByBoardContentContaining(@Param("boardContent") String boardContent, 
											   @Param("page") int page, 
											   @Param("limit") int limit);
	
	Page<BoardVO> findByMemberNickContaining(String memberNick, Pageable pageable);//0424 song memberEmail->memberNick
	
	// 댓글 제외한 원글들만의 게시글 수 : boardReSeq = 0
	long countByBoardOrigin(int boardOrigin);
	
	// 댓글 제외한 원글들만의 게시글 수 : boardReSeq = 0
	//  0507 내가쓴 글 카운트
	long countByBoardOriginAndMemberEmail(int boardOrigin, String memberEmail);
	
	// 댓글 제외한 원글들만의 게시글들만 가져오기(페이징) : boardReSeq = 0
	Page<BoardVO> findByBoardOrigin(int boardOrigin, Pageable pageable); 
	
	// 댓글 제외한 원글들만의 게시글들만 가져오기(페이징) : boardReSeq = 0
	//0507 내가 쓴 글 만 보이도록
	Page<BoardVO> findByBoardOriginAndMemberEmailLike(int boardOrigin, Pageable pageable, String memberEmail); 
	
	// 게시글 조회수 갱신
	@Modifying
	@Query(value = "UPDATE board_tbl SET " + 
				   "BOARD_READ_COUNT = BOARD_READ_COUNT + 1 " + 
				   "WHERE BOARD_CODE = :boardCode", nativeQuery = true)
	void updateBoardReadcountByBoardCode(@Param("boardCode") int boardCode);

	// 게시글 삭제
	void deleteById(int boardCode);
}