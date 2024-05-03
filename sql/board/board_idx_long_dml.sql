-- LONG데이터 타입 like검색 불능 패치
-- 0425 아래 추가
create index idx_board_tbl on board_tbl(board_content)
indextype is ctxsys.context;

--'내용' 추가
select board_content from board_tbl where contains(board_content, '%오징어%') > 0;

--'갯수' 추가
select count(*) from board_tbl where contains(board_content, '%오징어%') > 0;

--'글내용'검색
--WHERE contains(board_content, '%오징어%') > 0

SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT *
			 FROM board_tbl
			 WHERE contains(board_content, '%오징어%') > 0
             ORDER BY board_code DESC
           ) m  
      )  
WHERE page = 1;

SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT *
			 FROM board_tbl
			 WHERE board_title like '%오징어%'    
             ORDER BY board_code DESC
           ) m  
      )  
WHERE page = 1;