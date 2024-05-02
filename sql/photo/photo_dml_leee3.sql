--searchKey : (FOOD_NAME),(FOOD_INGREDIENT_MAIN_INSIDE)
--searchWord : (아귀찜),(아귀)
--searchWord : (콜리),(브로콜리)
select count(*)
from (
    SELECT distinct food_name
    FROM FOOD_CATE_CODE_TBL C, FOOD_TBL F
    WHERE C.FOOD_CODE = F.FOOD_CODE
    AND C.FOOD_CATE_CODE > 0 
    AND F.FOOD_NAME LIKE '%콜리%'
);


-- 과제 : 0501 DISTINCT 대신 ROW_NUMBER() 사용.
-- ROW_NUMBER() OVER(PARTITION BY F.FOOD_NAME order by F.FOOD_CODE desc) AS rn 
-- 검색 & 페이징
SELECT  FOOD_CODE, FOOD_NAME, FOOD_IMG, FOOD_INTRODUCE,   
        FOOD_DATE, FOOD_DISPLAY, FOOD_IMG_ORIGIN, FOOD_INGREDIENT_MAIN_INSIDE,   
        FOOD_INGREDIENT_MAIN_VIEW, FOOD_INGREDIENT_SUB_INSIDE, FOOD_INGREDIENT_SUB_VIEW, FOOD_RECIPE    
 FROM (   
    SELECT m.*, FLOOR((ROWNUM - 1) / 10 + 1) page   
    FROM (   
            SELECT *   
            FROM food_tbl f   
            WHERE f.FOOD_NAME LIKE '%콜리%'    
        ) m   
    )   
 WHERE page = 1 ;
 

SELECT *
FROM (
        SELECT m.*, floor((ROWNUM - 1) / 20 + 1) page
        FROM (
                SELECT C.FOOD_CODE, 
                       C.FOOD_CATE_CODE,
                       F.FOOD_NAME, 
                       F.FOOD_IMG, 
                       F.FOOD_INTRODUCE,
                       F.FOOD_DATE, 
                       F.FOOD_DISPLAY, 
                       F.FOOD_IMG_ORIGIN, F.FOOD_INGREDIENT_MAIN_INSIDE,
                       F.FOOD_INGREDIENT_MAIN_VIEW, F.FOOD_INGREDIENT_SUB_INSIDE, F.FOOD_INGREDIENT_SUB_VIEW, F.FOOD_RECIPE,
                       ROW_NUMBER() OVER(PARTITION BY F.FOOD_NAME order by F.FOOD_CODE desc) AS rn
                FROM FOOD_CATE_CODE_TBL C, FOOD_TBL F
                WHERE C.FOOD_CODE = F.FOOD_CODE
                AND C.FOOD_CATE_CODE > 0 
                AND F.FOOD_NAME LIKE '%콜리%'
        ) m
        WHERE rn = 1
    ) 
WHERE page = 1;


