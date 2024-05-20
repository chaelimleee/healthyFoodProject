--가장 최근 등록된 파일 현황(qna게시판)
select * from qna_tbl where qna_date = (select max(qna_date) from qna_tbl);