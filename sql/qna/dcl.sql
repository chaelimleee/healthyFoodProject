-- 프로젝트 전용 계정 생성
create user FOODTEAM2 identified by 1234;

grant connect, resource, create view to FOODTEAM2;