--사상체질별 전체 레시피
SELECT  FOOD_CODE, FOOD_NAME, FOOD_IMG, FOOD_INTRODUCE,
		FOOD_DATE, FOOD_DISPLAY, FOOD_IMG_ORIGIN, FOOD_INGREDIENT_MAIN_INSIDE,
		FOOD_INGREDIENT_MAIN_VIEW, FOOD_INGREDIENT_SUB_INSIDE, FOOD_INGREDIENT_SUB_VIEW, FOOD_RECIPE 
FROM food_tbl f 
WHERE EXISTS (
    SELECT s.SASANG_GOOD_INGREDIENT_MAIN 
    FROM SASANG_GOOD_MAIN_TBL s 
    WHERE f.food_ingredient_main_inside LIKE '%' || s.SASANG_GOOD_INGREDIENT_MAIN || '%'  
    AND s.sasang_name = '태음인'
) AND f.food_ingredient_main_inside IS NOT NULL;


--사상체질별 레시피 전체 개수. 
SELECT count(*)
FROM food_tbl f 
WHERE EXISTS (
    SELECT s.SASANG_GOOD_INGREDIENT_MAIN 
    FROM SASANG_GOOD_MAIN_TBL s 
    WHERE f.food_ingredient_main_inside LIKE '%' || s.SASANG_GOOD_INGREDIENT_MAIN || '%'  
    AND s.sasang_name = '태음인'
) AND f.food_ingredient_main_inside IS NOT NULL;
			

-- 체질별 레시피 추천 페이징. 
SELECT FOOD_CODE, FOOD_NAME, FOOD_IMG, FOOD_INTRODUCE,
       FOOD_DATE, FOOD_DISPLAY, FOOD_IMG_ORIGIN, FOOD_INGREDIENT_MAIN_INSIDE,
       FOOD_INGREDIENT_MAIN_VIEW, FOOD_INGREDIENT_SUB_INSIDE, FOOD_INGREDIENT_SUB_VIEW, FOOD_RECIPE 
FROM (
		SELECT m.*, FLOOR((ROWNUM - 1) / 20 + 1) page
		FROM (
				SELECT *
		        FROM food_tbl f 
				WHERE EXISTS (
						    SELECT s.SASANG_GOOD_INGREDIENT_MAIN 
						    FROM SASANG_GOOD_MAIN_TBL s 
						    WHERE f.food_ingredient_main_inside LIKE '%' || s.SASANG_GOOD_INGREDIENT_MAIN || '%'  
						    AND s.sasang_name = '태음인'
					 ) AND f.food_ingredient_main_inside IS NOT NULL
		) m
	)
WHERE page = 1;


-- 검색 
--SELECT *
--FROM food_tbl f 
--WHERE EXISTS (
--		    SELECT s.SASANG_GOOD_INGREDIENT_MAIN 
--		    FROM SASANG_GOOD_MAIN_TBL s 
--		    WHERE f.food_name LIKE '%' || '야끼소바' || '%'  
--	 );
	 

-- 검색
SELECT FOOD_CODE, FOOD_NAME, FOOD_IMG, FOOD_INTRODUCE,
       FOOD_DATE, FOOD_DISPLAY, FOOD_IMG_ORIGIN, FOOD_INGREDIENT_MAIN_INSIDE,
       FOOD_INGREDIENT_MAIN_VIEW, FOOD_INGREDIENT_SUB_INSIDE, FOOD_INGREDIENT_SUB_VIEW, FOOD_RECIPE 
FROM (
	SELECT m.*, FLOOR((ROWNUM - 1) / 20 + 1) page
	FROM (
			SELECT *
			FROM food_tbl f
			WHERE f.food_name LIKE '%' || '야끼소바' || '%' 
		) m
	)
WHERE page = 1;			
				
				
				
				

	 