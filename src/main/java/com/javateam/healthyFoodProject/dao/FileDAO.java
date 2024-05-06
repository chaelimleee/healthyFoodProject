package com.javateam.healthyFoodProject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.javateam.healthyFoodProject.domain.UploadFile;

public interface FileDAO extends JpaRepository<UploadFile, Integer> {
	 
	public UploadFile findOneByFileName(String fileName);
	
	public UploadFile findOneById(int id);
	
	public void deleteById(int id);
	
	/**
	 * 0409 leee photo게시판 이미지 가져오는 쿼리. 
	 * @param boardNum
	 * @return 
	 */
	@Modifying
	@Query(value = "select u.filename " + 
				   "from photo_tbl p, upload_file_tbl u " + 
				   "WHERE p.board_num = u.id and p.board_num = :boardNum ", nativeQuery = true)
	List<String> selectBoardImg(@Param("boardNum") int boardNum);
	
	
}