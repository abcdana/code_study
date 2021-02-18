-- D > class > server > codestudy > db.sql

-- 회원 테이블
select * from tblMember;
drop table tblMember cascade constraints;
drop sequence seqMember;

create table tblMember (
    seq number primary key,                             --번호(PK)
    id varchar2(30) not null unique,                   -- 아이디
    name varchar2(30) not null,                         -- 이름
    email varchar2(100) not null,                       -- 이메일
    pw varchar2(30) not null,                             -- 암호
    pic varchar2(50) not null,                            -- 프로필 사진
    regdate date default sysdate not null           -- 가입일
);

create sequence seqMember;

-- 1	hong	홍길동	hong@test.com	111	nopic.png	21/02/15
select * from tblMember;







-- 게시판 테이블
drop table tblBoard cascade constraints;
drop table tblComment cascade constraints;

drop sequence seqBoard;
drop sequence seqComment;

create table tblBoard (
    seq number primary key,                                     --글번호(PK)
    subject varchar2(1000) not null,                            --제목
    content varchar2(2000) not null,                            --내용
    regdate date default sysdate not null,                  --날짜
    readcount number default 0 not null,                    --조회수
    mseq number not null references tblMember(seq)  --회원번호(FK)
);

create sequence seqBoard;

insert into tblBoard (seq, subject, content, regdate, readcount, mseq) 
    values (seqBoard.nextVal, '게시판 테스트입니다.', '내용입니다.', default, default, 1);

select * from tblBoard;

commit;





-- 게시판 뷰
create or replace view vwBoard
as
select 
    seq, 
    subject, 
    (select name from tblMember where seq = tblBoard.mseq) as name, 
    regdate, 
    readcount, 
    mseq,
    (sysdate - regdate)  * 24 as gap,
    content, --검색 때문에 추가
    filename
from tblBoard;

select * from vwBoard where name like '%검색어%' or subject like '%검색어%' or content like '%검색어%';




select b.*, (select name from tblMember where seq = b.mseq) as name, (select id from tblMember where seq = b.mseq) as id from tblBoard b;














drop table tblBoard cascade constraints;
drop sequence seqBoard;

create table tblBoard (
    seq number primary key,                                     --글번호(PK)
    subject varchar2(1000) not null,                            --제목
    content varchar2(2000) not null,                            --내용
    regdate date default sysdate not null,                  --날짜
    readcount number default 0 not null,                    --조회수
    mseq number not null references tblMember(seq),  --회원번호(FK)
    filename varchar2(100) null,                                 -- 물리적 파일명
    orgfilename varchar2(100) null,                             -- 원본 파일명
    downloadcount number default 0                          -- 다운로드 횟수
);

create sequence seqBoard;


select * from tblBoard;



-- 페이징, Paging
-- 한페이지 : 20개씩
select * from vwBoard order by seq desc;

select rownum from vwBoard order by seq desc;


select b.*, rownum from vwBoard b order by seq desc;

-- 1번글 ~ 20번글
select * from (select a.*, rownum as rnum from (select * from vwBoard order by seq desc) a) where rnum between 1 and 20;

select * from
(select a.*, rownum as rnum from (select * from vwBoard order by seq desc) a)
where rnum between 21 and 40;

select * from
(select a.*, rownum as rnum from (select * from vwBoard order by seq desc) a)
where rnum between 41 and 60;





























