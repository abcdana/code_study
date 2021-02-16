-- 게시판 테이블
drop table tblBoard cascade constraints;
drop table tblComment cascade constraints;

drop sequence seqBoard;
drop sequence seqComment;

create table tblBoard(
    seq number primary key,                 -- 글번호(pk)
    subject varchar2(1000) not null,        -- 제목
    content varchar2(2000) not null,        -- 내용
    regdate date default sysdate not null,  -- 날짜
    readcount number default 0 not null,    -- 조회수
    mseq number not null references tblMember(seq) -- 회원번호(fk)
);

create sequence seqBoard;

insert into tblBoard (seq, subject, content, regdate, readcount, mseq) 
    values (seqBoard.nextVal, '게시판 테스트입니다.', '내용입니다.', default, default, 1);


select * from tblBoard;
delete from tblBoard;
drop sequence seqBoard;
create sequence seqBoard;



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
    (sysdate - regdate) * 24 as gap,
    content -- 검색 때문에 추가
from tblBoard;

select * from vwBoard where name like '%검색어%' or subject like '%검색어%' or content like '%검색어%';




