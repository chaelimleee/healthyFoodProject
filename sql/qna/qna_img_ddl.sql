CREATE SEQUENCE image_upload_file_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 99999
NOCYCLE;


CREATE TABLE upload_file_tbl (
	id number(10,0) primary key,
	filename nvarchar2(300) not null,
	save_filename nvarchar2(500) not null,
	file_path nvarchar2(500) not null,
	content_type nvarchar2(10),
	file_size number(20,0) default 0,
	regdate date default sysdate
);