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

import com.javateam.healthyFoodProject.domain.FoodVO;

// public interface FoodDAO extends JpaRepository<FoodVO, Integer>{
// 페이징 메서드 추출위해 Repository 교체
public interface FoodDAO extends PagingAndSortingRepository<FoodVO, Integer>{
	
	FoodVO save(FoodVO foodVO);
	
	List<FoodVO> findAllByOrderByFoodCodeDesc();
	
	long count();

	Page<FoodVO> findAll(Pageable pageable);
	Page<FoodVO> findAll();
	
	FoodVO findById(int foodCode);
	
	int countByfoodNameLike(String foodName); // Like
	int countByfoodNameContaining(String foodName); // Containing
//	int countByFoodContentContaining(String boardContent);
	int countByfoodIngredientMainViewContaining(String foodName); // 0415 leee 수정
	
	Page<FoodVO> findByfoodNameLike(String foodName, Pageable pageable); // Like
	Page<FoodVO> findByfoodNameContaining(String foodName, Pageable pageable); // Containing
//	Page<FoodVO> findByFoodContentContaining(String boardContent, Pageable pageable);
	Page<FoodVO> findByfoodIngredientMainViewContaining(String foodName, Pageable pageable); // 0415 leee 수정
	
	// 원글에 따른 소속 댓글들 가져오기
//	List<FoodVO> findByFoodCode(int foodCode); 
	
	// 댓글 제외한 원글들만의 게시글 수 : boardReSeq = 0
	long countBy();
	
	// 댓글 제외한 원글들만의 게시글들만 가져오기(페이징) : boardReSeq = 0
//	Page<FoodVO> findByFoodCode(Pageable pageable); 
	
	// 게시글 조회수 갱신
	@Modifying
	@Query(value = "UPDATE food_tbl SET " + 
				   "FOOD_READ_COUNT = FOOD_READ_COUNT + 1 " + 
				   "WHERE BOARD_CODE = :foodCode", nativeQuery = true)
	void updateFoodReadcountByFoodCode(@Param("foodCode") int foodCode);

	// 게시글 삭제
	void deleteById(int foodCode);
}