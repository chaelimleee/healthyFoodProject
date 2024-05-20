

--- foodDAO 사상체질 재료만 검색. // 전체를 추가해야함.
SELECT FOOD_CODE, FOOD_NAME, FOOD_IMG, FOOD_INTRODUCE, 
	   FOOD_DATE, FOOD_DISPLAY, FOOD_IMG_ORIGIN, FOOD_INGREDIENT_MAIN_INSIDE, 
	   FOOD_INGREDIENT_MAIN_VIEW, FOOD_INGREDIENT_SUB_INSIDE, FOOD_INGREDIENT_SUB_VIEW, FOOD_RECIPE
FROM (  
        SELECT m.*, FLOOR((ROWNUM - 1) / 10 + 1) page  
        FROM (   
            SELECT F.FOOD_CODE, F.FOOD_NAME, F.FOOD_IMG, F.FOOD_INTRODUCE, 
			       F.FOOD_DATE, F.FOOD_DISPLAY, F.FOOD_IMG_ORIGIN, F.FOOD_INGREDIENT_MAIN_INSIDE, 
	               F.FOOD_INGREDIENT_MAIN_VIEW, F.FOOD_INGREDIENT_SUB_INSIDE, F.FOOD_INGREDIENT_SUB_VIEW, F.FOOD_RECIPE
            FROM FOOD_CATE_CODE_TBL C, 
                    (
                    SELECT *  
                    FROM food_tbl f  
                    WHERE EXISTS (  
                                 SELECT s.SASANG_GOOD_INGREDIENT_MAIN  
                                 FROM SASANG_GOOD_MAIN_TBL s  
                                 WHERE f.food_ingredient_main_inside LIKE '%' || s.SASANG_GOOD_INGREDIENT_MAIN || '%'   
                                 and s.sasang_name = '태음인'  
                	) and f.food_ingredient_main_inside is not null 
                    AND F.FOOD_INGREDIENT_MAIN_INSIDE LIKE '%김치%'
            		OR  F.FOOD_INGREDIENT_SUB_INSIDE LIKE '%김치%'
            )F
            WHERE C.FOOD_CODE = F.FOOD_CODE  
       ) m     
)  
WHERE page = 1 ;



--- foodDAO 사상체질 유형 + 재료 검색. 
SELECT FOOD_CODE, FOOD_NAME, FOOD_IMG, FOOD_INTRODUCE, 
	   FOOD_DATE, FOOD_DISPLAY, FOOD_IMG_ORIGIN, FOOD_INGREDIENT_MAIN_INSIDE, 
	   FOOD_INGREDIENT_MAIN_VIEW, FOOD_INGREDIENT_SUB_INSIDE, FOOD_INGREDIENT_SUB_VIEW, FOOD_RECIPE
FROM (  
        SELECT m.*, FLOOR((ROWNUM - 1) / 10 + 1) page  
        FROM (   
            SELECT F.FOOD_CODE, F.FOOD_NAME, F.FOOD_IMG, F.FOOD_INTRODUCE, 
			       F.FOOD_DATE, F.FOOD_DISPLAY, F.FOOD_IMG_ORIGIN, F.FOOD_INGREDIENT_MAIN_INSIDE, 
	               F.FOOD_INGREDIENT_MAIN_VIEW, F.FOOD_INGREDIENT_SUB_INSIDE, F.FOOD_INGREDIENT_SUB_VIEW, F.FOOD_RECIPE
            FROM FOOD_CATE_CODE_TBL C, 
                    (
                    SELECT *  
                    FROM food_tbl f  
                    WHERE EXISTS (  
                                 SELECT s.SASANG_GOOD_INGREDIENT_MAIN  
                                 FROM SASANG_GOOD_MAIN_TBL s  
                                 WHERE f.food_ingredient_main_inside LIKE '%' || s.SASANG_GOOD_INGREDIENT_MAIN || '%'   
                                 and s.sasang_name = '태음인'  
                	) and f.food_ingredient_main_inside is not null 
                    AND F.FOOD_INGREDIENT_MAIN_INSIDE LIKE '%새우%'
            		OR  F.FOOD_INGREDIENT_SUB_INSIDE LIKE '%새우%'
            )F
            WHERE C.FOOD_CODE = F.FOOD_CODE  
            AND C.FOOD_CATE_CODE = 7 
       ) m     
)  
WHERE page = 1 ;


--- foodDAO 사상체질 유형 + 음식명 검색. 
SELECT FOOD_CODE, FOOD_NAME, FOOD_IMG, FOOD_INTRODUCE, 
	   FOOD_DATE, FOOD_DISPLAY, FOOD_IMG_ORIGIN, FOOD_INGREDIENT_MAIN_INSIDE, 
	   FOOD_INGREDIENT_MAIN_VIEW, FOOD_INGREDIENT_SUB_INSIDE, FOOD_INGREDIENT_SUB_VIEW, FOOD_RECIPE
FROM (  
        SELECT m.*, FLOOR((ROWNUM - 1) / 10 + 1) page  
        FROM (   
            SELECT F.FOOD_CODE, F.FOOD_NAME, F.FOOD_IMG, F.FOOD_INTRODUCE, 
			       F.FOOD_DATE, F.FOOD_DISPLAY, F.FOOD_IMG_ORIGIN, F.FOOD_INGREDIENT_MAIN_INSIDE, 
	               F.FOOD_INGREDIENT_MAIN_VIEW, F.FOOD_INGREDIENT_SUB_INSIDE, F.FOOD_INGREDIENT_SUB_VIEW, F.FOOD_RECIPE
            FROM FOOD_CATE_CODE_TBL C, 
                    (
                    SELECT *  
                    FROM food_tbl f  
                    WHERE EXISTS (  
                                 SELECT s.SASANG_GOOD_INGREDIENT_MAIN  
                                 FROM SASANG_GOOD_MAIN_TBL s  
                                 WHERE f.food_ingredient_main_inside LIKE '%' || s.SASANG_GOOD_INGREDIENT_MAIN || '%'   
                                 and s.sasang_name = '태음인'  
                	) and f.food_ingredient_main_inside is not null 
                    AND F.FOOD_NAME LIKE '%새우%'
            )F
            WHERE C.FOOD_CODE = F.FOOD_CODE  
            AND C.FOOD_CATE_CODE = 7 
       ) m     
)  
WHERE page = 1 ;