

select * from upload_file_tbl where filename='삼계탕.jpg';
select max(regdate) from upload_file_tbl;

--가장 최근 등록된 파일 현황(업로드 테이블)
select * from upload_file_tbl where regdate = (select max(regdate) from upload_file_tbl);
--