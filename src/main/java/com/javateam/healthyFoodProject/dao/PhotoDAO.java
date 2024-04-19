package com.javateam.healthyFoodProject.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.javateam.healthyFoodProject.domain.PhotoVO;

public interface PhotoDAO extends PagingAndSortingRepository<PhotoVO, Integer> {

	PhotoVO save(PhotoVO boardVO);

	long count();

	Page<PhotoVO> findAll(Pageable pageable);

	PhotoVO findById(int boardNum);

	int countByBoardSubjectLike(String boardSubject); // Like

	int countByBoardSubjectContaining(String boardSubject); // Containing

	int countByBoardContentContaining(String boardContent);

	int countByBoardWriterContaining(String boardWriter);

	Page<PhotoVO> findByBoardSubjectLike(String boardSubject, Pageable pageable); // Like

	Page<PhotoVO> findByBoardSubjectContaining(String boardSubject, Pageable pageable); // Containing

	Page<PhotoVO> findByBoardContentContaining(String boardContent, Pageable pageable);

	Page<PhotoVO> findByBoardWriterContaining(String boardWriter, Pageable pageable);

	// 원글에 따른 소속 댓글들 가져오기
	// findByAgeOrderByLastnameDesc
	// List<QnaBoardVO> findByBoardReRef(int boardReRef);
	List<PhotoVO> findByBoardReRefOrderByBoardDateAsc(int boardReRef);

	// 댓글 제외한 원글들만의 게시글 수 : boardReRef = 0
	long countByBoardReRef(int boardReRef);

	// 댓글 제외한 원글들만의 게시글들만 가져오기(페이징) : boardReRef = 0
	Page<PhotoVO> findByBoardReRef(int boardReRef, Pageable pageable);

	// 게시글 조회수 갱신
	@Modifying
	@Query(value = "UPDATE photo_tbl SET " +
			"board_readcount = board_readcount + 1 " +
			"WHERE board_num = :boardNum", nativeQuery = true)
	void updateBoardReadcountByBoardNum(@Param("boardNum") int boardNum);

	/**
	 * 0409 leee photo게시판 이미지 가져오는 쿼리.
	 * 
	 * @param boardNum
	 * @return
	 */
	
	// @Query(value = "select p.board_num, p.board_writer, p.board_subject,
	// p.board_content, p.board_date, u.filename " +
	@Modifying
	@Query(value = "select u.filename " + // 0411 leee 수정함. 이렇게 하면 파일이름만 가져옴..
					"from photo_tbl p, upload_file_tbl u " +
					"WHERE p.board_num = u.id and p.board_num = :boardNum ", nativeQuery = true)
	List<String> selectBoardImg(@Param("boardNum") int boardNum);
	
	
	// 0411 leee 이미지랑 제목, 내용 가져오는 쿼리 . 이너 조인. 
	@Modifying
	@Query(value = "select p.board_num, u.filename ,p.board_writer, p.board_pass, p.board_subject,"+
					"p.board_content,p.board_re_ref, p.board_re_lev, p.board_re_seq,p.board_readcount, p.board_date " +
					"from photo_tbl p join upload_file_tbl u " +
					"on p.board_num = u.id " +
					"WHERE p.board_num = :boardNum ", nativeQuery = true)
	List<PhotoVO> selectPhotoAndFileName(@Param("boardNum") int boardNum);
	
	@Query("SELECT p.boardSubject, u.fileName FROM PhotoVO p JOIN p.uploadFile u WHERE p.boardNum = :boardNum")
    List<PhotoVO> findSubjectAndFileNameByBoardNum(@Param("boardNum") int boardNum);
	

	// 게시글 삭제
	void deleteById(int boardNum);
}