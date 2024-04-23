package com.javateam.healthyFoodProject.dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.javateam.healthyFoodProject.domain.FoodVO;

import lombok.experimental.PackagePrivate;

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
	
	// 0423 leee 추가함.
	@Query(value = "SELECT FOOD_CODE, FOOD_NAME, FOOD_IMG, FOOD_INTRODUCE, FOOD_RECIPE, " + 
			   "FOOD_DATE, FOOD_DISPLAY, FOOD_IMG_ORIGIN, FOOD_INGREDIENT_MAIN_INSIDE," + 
			   "FOOD_INGREDIENT_MAIN_VIEW, FOOD_INGREDIENT_SUB_INSIDE, FOOD_INGREDIENT_SUB_VIEW " + 
			   "FROM FOOD_TBL " + 
			   "WHERE FOOD_INGREDIENT_MAIN_INSIDE IN ( " +
								   "SELECT SASANG_GOOD_INGREDIENT_MAIN "+
								   "FROM SASANG_GOOD_MAIN_TBL " +
								   "WHERE SASANG_NAME = :sasang )"
			   , nativeQuery = true)
	List<FoodVO> findAllByFoodIngredientMainInside(@Param("sasang") String sasang);
	
	
//	@Modifying
	@Query(value = "SELECT FOOD_CODE, FOOD_NAME, FOOD_IMG, FOOD_INTRODUCE, " + 
				   "FOOD_DATE, FOOD_DISPLAY, FOOD_IMG_ORIGIN, FOOD_INGREDIENT_MAIN_INSIDE," + 
				   "FOOD_INGREDIENT_MAIN_VIEW, FOOD_INGREDIENT_SUB_INSIDE, FOOD_INGREDIENT_SUB_VIEW " + 
				   "FROM FOOD_TBL " + 
				   "WHERE FOOD_INGREDIENT_MAIN_INSIDE IN ( " +
									   "SELECT SASANG_GOOD_INGREDIENT_MAIN "+
									   "FROM SASANG_GOOD_MAIN_TBL " +
									   "WHERE SASANG_NAME = :sasang )"
				   , nativeQuery = true)
	List<FoodVO> findBySasangName(@Param("sasang") String sasang);
	
//	List<FoodVO> findByFoodIngredientMainInsideIn(Collection<String> foodIngredientMainInside);
	List<FoodVO> findByFoodIngredientMainInsideIn(List<String> foodIngredientMainInside);
	
	@Query(value= "SELECT SASANG_GOOD_INGREDIENT_MAIN "	+ 
				  "FROM SASANG_GOOD_MAIN_TBL WHERE SASANG_NAME = :sasangName", nativeQuery = true)
	List<String> findSasangGoodIngredientMainBySasangName(@Param("sasangName") String sasangName);
	
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