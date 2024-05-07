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
	
//	select count(*)
//	from (
//	    SELECT distinct food_name
//	    FROM FOOD_CATE_CODE_TBL C, FOOD_TBL F
//	    WHERE C.FOOD_CODE = F.FOOD_CODE
//	    AND C.FOOD_CATE_CODE > 0 
//	    AND F.FOOD_NAME LIKE '%콜리%'
//	);
	// 0501 leee 전체 검색. 
	@Query(value= "SELECT count(*) "
				+ "FROM ("
				+ "      SELECT DISTINCT FOOD_NAME "
				+ "		 FROM FOOD_CATE_CODE_TBL C, FOOD_TBL F "
				+ "		 WHERE C.FOOD_CODE = F.FOOD_CODE "
				+ "		 AND C.FOOD_CATE_CODE > 0  "
				+ "		 AND F.FOOD_NAME LIKE '%' || :foodName || '%'"
				+ ")", nativeQuery = true)
	int countByFoodNameContainingFoodTypeAll(@Param("foodName") String foodName); 

	// 0501 leee 전체 검색. 
	@Query(value= "SELECT count(*) "
				+ "FROM ("
				+ "      SELECT DISTINCT FOOD_NAME "
				+ "		 FROM FOOD_CATE_CODE_TBL C, FOOD_TBL F "
				+ "		 WHERE C.FOOD_CODE = F.FOOD_CODE "
				+ "		 AND C.FOOD_CATE_CODE > 0  "
				+ "		 AND F.FOOD_INGREDIENT_MAIN_INSIDE LIKE '%' || :foodIngredient || '%'"
				+ ")", nativeQuery = true)
	int countByFoodIngredientContainingFoodTypeAll(@Param("foodIngredient") String foodIngredient); 
	
	// 0501 leee 수정 0이 아닐 때 // 레시피명으로 검색할 때
	@Query(value= "SELECT count(*) "
				+ "FROM FOOD_CATE_CODE_TBL C, FOOD_TBL F "
				+ "WHERE C.FOOD_CODE = F.FOOD_CODE "
				+ "AND C.FOOD_CATE_CODE = :foodType  "
				+ "AND F.FOOD_NAME LIKE '%' || :foodName || '%'", nativeQuery = true)
	int countByFoodNameContainingAndFoodType(@Param("foodName") String foodName, 
											 @Param("foodType")int foodType); 

	// 0501 leee 수정 0이 아닐 때 // 재료명으로 검색할 때
	@Query(value= "SELECT count(*) "
			+ "FROM FOOD_CATE_CODE_TBL C, FOOD_TBL F "
			+ "WHERE C.FOOD_CODE = F.FOOD_CODE "
			+ "AND C.FOOD_CATE_CODE = :foodType  "
			+ "AND F.FOOD_INGREDIENT_MAIN_INSIDE LIKE '%' || :foodIngredient || '%'", nativeQuery = true)
	int countByFoodIngredientContainingAndFoodType(@Param("foodIngredient") String foodIngredient,
												   @Param("foodType") int foodType); 
	
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
	
//	@Query(value = "SELECT n.FOOD_CATE_NAME, c.FOOD_CODE "
//				+ "FROM FOOD_CATE_NAME_TBL N , FOOD_CATE_CODE_TBL C "
//				+ "WHERE N.FOOD_CATE_CODE = c.FOOD_CATE_CODE and c.FOOD_CODE = :sasangName :"
//				, nativeQuery = true)
//	List<FoodVO> findAllByFoodType(@Param("sasangName") String sasangName);
	
	
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
	
	//0423 leee 추가함. 해당 체질의 주재료들 가져옴. //   . 
//	@Query(value= "SELECT SASANG_GOOD_INGREDIENT_MAIN "	+ 
//				  "FROM SASANG_GOOD_MAIN_TBL "  + 
//				  "WHERE SASANG_NAME = :sasangName", nativeQuery = true)
	
	//0424 leee 한개의 데이터만 있는 컬럼 말고 여러개 있는거에서도 가져오는 쿼리 // 부적합한 열 유형 나와서 컬럼 다 써줌. 된다된다
	//0424 leee 스트림 에러 :: 레시피를 맨 마지막에 넣으니까 바로 해결됨 
	@Query(value= "SELECT FOOD_CODE, FOOD_NAME, FOOD_IMG, FOOD_INTRODUCE,"
				+ "FOOD_DATE, FOOD_DISPLAY, FOOD_IMG_ORIGIN, FOOD_INGREDIENT_MAIN_INSIDE,"
				+ "FOOD_INGREDIENT_MAIN_VIEW, FOOD_INGREDIENT_SUB_INSIDE, FOOD_INGREDIENT_SUB_VIEW, FOOD_RECIPE "
				+ "FROM food_tbl f "
				+ "WHERE EXISTS ( "
				+ "    SELECT s.SASANG_GOOD_INGREDIENT_MAIN "
				+ "    FROM SASANG_GOOD_MAIN_TBL s "
				+ "    WHERE f.food_ingredient_main_inside LIKE '%' || s.SASANG_GOOD_INGREDIENT_MAIN || '%'  "
				+ "    and s.sasang_name = :sasangName "
				+ ") and f.food_ingredient_main_inside is not null",nativeQuery = true)
	List<FoodVO> findSasangGoodIngredientMainBySasangName(@Param("sasangName") String sasangName);
	
	@Query(value= "SELECT count(*) "
				+ "FROM food_tbl f "
				+ "WHERE EXISTS ( "
				+ "    SELECT s.SASANG_GOOD_INGREDIENT_MAIN "
				+ "    FROM SASANG_GOOD_MAIN_TBL s "
				+ "    WHERE f.food_ingredient_main_inside LIKE '%' || s.SASANG_GOOD_INGREDIENT_MAIN || '%'  "
				+ "    and s.sasang_name = :sasangName "
				+ ") and f.food_ingredient_main_inside is not null",nativeQuery = true)
	int countSasangGoodIngredientMainBySasangName(@Param("sasangName") String sasangName);
	
	//0501 푸드 유형 별 레시피 보여주기 추가 
	@Query(value= "SELECT F.FOOD_CODE, F.FOOD_NAME, F.FOOD_IMG, F.FOOD_INTRODUCE, "
				+ "       F.FOOD_DATE, F.FOOD_DISPLAY, F.FOOD_IMG_ORIGIN, F.FOOD_INGREDIENT_MAIN_INSIDE, "
				+ "       F.FOOD_INGREDIENT_MAIN_VIEW, F.FOOD_INGREDIENT_SUB_INSIDE, F.FOOD_INGREDIENT_SUB_VIEW, F.FOOD_RECIPE "
				+ "FROM( "
				+ "    SELECT M.*, floor((ROWNUM - 1) / :limit + 1) PAGE "
				+ "    FROM ( "
				+ "        SELECT F.FOOD_CODE, F.FOOD_NAME, F.FOOD_IMG, F.FOOD_INTRODUCE, "
				+ "               F.FOOD_DATE, F.FOOD_DISPLAY, F.FOOD_IMG_ORIGIN, F.FOOD_INGREDIENT_MAIN_INSIDE, "
				+ "               F.FOOD_INGREDIENT_MAIN_VIEW, F.FOOD_INGREDIENT_SUB_INSIDE, F.FOOD_INGREDIENT_SUB_VIEW, F.FOOD_RECIPE "
				+ "        FROM food_cate_code_tbl C  "
				+ "        JOIN food_tbl F ON C.food_code = F.food_code "
				+ "        WHERE C.food_cate_code = :foodCateCode  "
				+ "    ) M "
				+ ")F "
				+ "WHERE page = :page", nativeQuery = true)
	List<FoodVO> findByFoodTypeAndFoodCateCode(@Param("page") int page,
			   								   @Param("limit") int limit,
			   								   @Param("foodCateCode") int foodCateCode);
	
	@Query(value ="SELECT count(*) "
				+ "FROM FOOD_CATE_CODE_TBL C, FOOD_TBL F "
				+ "WHERE C.FOOD_CODE = F.FOOD_CODE  AND C.FOOD_CATE_CODE = :foodType", nativeQuery = true )
	int countByFoodType(@Param("foodType") int foodType);
	
	@Query(value= "SELECT FOOD_CODE, FOOD_NAME, FOOD_IMG, FOOD_INTRODUCE, "
				+ "       FOOD_DATE, FOOD_DISPLAY, FOOD_IMG_ORIGIN, FOOD_INGREDIENT_MAIN_INSIDE, "
				+ "       FOOD_INGREDIENT_MAIN_VIEW, FOOD_INGREDIENT_SUB_INSIDE, FOOD_INGREDIENT_SUB_VIEW, FOOD_RECIPE  "
				+ "FROM ( "
				+ "		SELECT m.*, FLOOR((ROWNUM - 1) / :limit + 1) page "
				+ "		FROM ( "
				+ "				SELECT * "
				+ "		        FROM food_tbl f  "
				+ "				WHERE EXISTS ( "
				+ "						    SELECT s.SASANG_GOOD_INGREDIENT_MAIN  "
				+ "						    FROM SASANG_GOOD_MAIN_TBL s  "
				+ "						    WHERE f.food_ingredient_main_inside LIKE '%' || s.SASANG_GOOD_INGREDIENT_MAIN || '%' "
				+ "						    AND s.sasang_name = :sasangName "
				+ "					 ) AND f.food_ingredient_main_inside IS NOT NULL "
				+ "		) m "
				+ "	) "
				+ "WHERE page = :page",nativeQuery = true)
	List<FoodVO> findSasangGoodIngredientMainBySasangNameAndPaging(@Param("page") int page,
																   @Param("limit") int limit,
																   @Param("sasangName") String sasangName);
	
//	@Query(value="SELECT * "
//				+ "FROM food_tbl f "
//				+ "WHERE EXISTS ("
//				+ "    SELECT s.SASANG_GOOD_INGREDIENT_MAIN "
//				+ "    FROM SASANG_GOOD_MAIN_TBL s "
//				+ "    WHERE f.food_ingredient_main_inside LIKE '%' || s.SASANG_GOOD_INGREDIENT_MAIN || '%' "
//				+ "    and s.sasang_name = :sasangName )", nativeQuery = true)
	@Query(value="SELECT f.food_ingredient_main_inside "
				+ "FROM food_tbl f "
				+ "WHERE EXISTS ( "
				+ "    SELECT s.SASANG_GOOD_INGREDIENT_MAIN "
				+ "    FROM SASANG_GOOD_MAIN_TBL s "
				+ "    WHERE f.food_ingredient_main_inside LIKE '%' || s.SASANG_GOOD_INGREDIENT_MAIN || '%'  "
				+ "    and s.sasang_name = :sasangName "
				+ ") and f.food_ingredient_main_inside is not null",nativeQuery = true)
	List<FoodVO> findByFoodName(@Param("sasangName") String sasangName);

	//0501 건강식 레시피 검색 페이징. 
	@Query(value="SELECT  FOOD_CODE, FOOD_NAME, FOOD_IMG, FOOD_INTRODUCE, "
				+ "       FOOD_DATE, FOOD_DISPLAY, FOOD_IMG_ORIGIN, FOOD_INGREDIENT_MAIN_INSIDE, "
				+ "       FOOD_INGREDIENT_MAIN_VIEW, FOOD_INGREDIENT_SUB_INSIDE, FOOD_INGREDIENT_SUB_VIEW, FOOD_RECIPE  "
				+ "FROM ( "
				+ "	SELECT m.*, FLOOR((ROWNUM - 1) / :limit + 1) page "
				+ "	FROM ( "
				+ "			SELECT * "
				+ "			FROM food_tbl f "
				+ "			WHERE f.FOOD_NAME LIKE '%' || :searchWord || '%'  "
				+ "		) m "
				+ "	) "
				+ "WHERE page = :page", nativeQuery = true)
	List<FoodVO> findBySeachingFoodNameAndPaging(@Param("page") int page,
					   							 @Param("limit") int limit,
					   							 @Param("searchWord") String searchWord);
	//0501 
	@Query(value="SELECT  FOOD_CODE, FOOD_NAME, FOOD_IMG, FOOD_INTRODUCE, "
				+ "       FOOD_DATE, FOOD_DISPLAY, FOOD_IMG_ORIGIN, FOOD_INGREDIENT_MAIN_INSIDE, "
				+ "       FOOD_INGREDIENT_MAIN_VIEW, FOOD_INGREDIENT_SUB_INSIDE, FOOD_INGREDIENT_SUB_VIEW, FOOD_RECIPE  "
				+ "FROM ( "
				+ "	SELECT m.*, FLOOR((ROWNUM - 1) / :limit + 1) page "
				+ "	FROM ( "
				+ "			SELECT * "
				+ "			FROM food_tbl f "
				+ "			WHERE f.FOOD_INGREDIENT_MAIN_INSIDE LIKE '%' || :searchWord || '%'  "
				+ "		) m "
				+ "	) "
				+ "WHERE page = :page", nativeQuery = true)
	List<FoodVO> findBySeachingFoodIngredientAndPaging(@Param("page") int page,
						   							   @Param("limit") int limit,
						   							   @Param("searchWord") String searchWord);
		
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
