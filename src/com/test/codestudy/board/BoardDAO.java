package com.test.codestudy.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.test.codestudy.DBUtil;

public class BoardDAO {

	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;

	public BoardDAO() {
		// DB 연결
		conn = DBUtil.open();
	}

	public void close() {
		
		try {
			
			conn.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	
	
	// WriteOk 서블릿이 글쓰기를 위임
	public int write(BoardDTO dto) {

		try {

			String sql = "insert into tblBoard (seq, subject, content, regdate, readcount, mseq, filename, orgfilename) values (seqBoard.nextVal, ?, ?, default, default, ?, ?, ?)";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getSubject());
			pstat.setString(2, dto.getContent());
			pstat.setString(3, dto.getMseq());

			pstat.setString(4, dto.getFilename());
			pstat.setString(5, dto.getOrgfilename());

			return pstat.executeUpdate(); // 1 or 0

		} catch (Exception e) {
			System.out.println(e);
		}

		return 0;

	}

	
	//List 서블릿 -> 글 목록 달라고 위임
	public ArrayList<BoardDTO> list(HashMap<String, String> map) {

		try {
			
			String where = "";
			
			if (map.get("search")!= null) {
				//검색중...
				where = String.format("where name like '%%%s%%' or subject like '%%%s%%' or content like '%%%s%%'", map.get("search"), map.get("search"), map.get("search"));
			}
			
			//String sql = String.format("select * from vwBoard %s order by seq desc", where);
			
			String sql = String.format("select * from (select a.*, rownum as rnum from (select * from vwBoard %s order by seq desc) a) where rnum between %s and %s"
					, where
					, map.get("begin")
					, map.get("end"));
			
			//System.out.println(sql);
			
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			
			//ResultSet -> ArrayList<DTO>
			ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
			
			while (rs.next()) {
				
				//레코드 1줄 -> DTO 1개
				BoardDTO dto = new BoardDTO();
			
				dto.setSeq(rs.getString("seq"));
				dto.setSubject(rs.getString("subject"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setMseq(rs.getString("mseq"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setName(rs.getString("name"));
				
				dto.setGap(rs.getInt("gap"));
				
				dto.setFilename(rs.getString("filename")); //파일명
				
				list.add(dto); //***잘 빼먹는 부분 : 에러메세지 안뜨니 주의할 것
				
			}
			
			return list; //***잘 빼먹는 부분 : 에러메세지 안뜨니 주의할 것
			
		} catch (Exception e) {
			System.out.println(e);
		}
		

		return null;
	}

	
	//View 서블릿 -> 글 1개 반환해달라고 요청 : 글 번호에 해당하는 글 정보 가져오기 
	public BoardDTO get(String seq) {

		try {
			
			String sql = "select b.*, (select name from tblMember where seq = b.mseq) as name, (select id from tblMember where seq = b.mseq) as id from tblBoard b where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			
			rs = pstat.executeQuery();
			
			if (rs.next()) {
				
				BoardDTO dto = new BoardDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setMseq(rs.getString("mseq"));
				
				dto.setName(rs.getString("name"));
				dto.setId(rs.getString("id"));
				
				dto.setFilename(rs.getString("filename"));
				dto.setOrgfilename(rs.getString("orgfilename"));
				dto.setDownloadcount(rs.getInt("downloadcount"));

				
				return dto;
			
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		return null;
	}

	
	
	//조회수 증가시키기
	public void updateReadcount(String seq) {
		
		try {
			
			String sql = "update tblBoard set readcount = readcount + 1 where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			rs = pstat.executeQuery();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
	}

	
	
	//EditOk 서블릿 -> 글 수정해달라고 요청
	public int edit(BoardDTO dto) {
		
		try {

			String sql = "update tblBoard set subject = ?, content = ? where seq = ?";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getSubject());
			pstat.setString(2, dto.getContent());
			pstat.setString(3, dto.getSeq());	//글 번호

			return pstat.executeUpdate(); // 1 or 0

		} catch (Exception e) {
			System.out.println(e);
		}

		return 0;

	}

	
	
	//DeleteOk 서블릿 -> 글 삭제하기
	public int del(String seq) {
		
		try {

			String sql = "delete from tblBoard where seq = ?";

			pstat = conn.prepareStatement(sql);	
			pstat.setString(1, seq);	//글 번호

			return pstat.executeUpdate(); // 1 or 0

		} catch (Exception e) {
			System.out.println(e);
		}
		
		return 0;
	}

	
	
	//다운로드 횟수 증가 (반환값 없음) = 조회수 증가시키는것과 비슷
	public void updateDownloadcount(String seq) {
		
		try {
			
			String sql = "update tblBoard set downloadcount = downloadcount + 1 where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			rs = pstat.executeQuery();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
	}

	
	
	//총 게시물 수 를 세는 메서드
	//List 서블릿 -> 총 게시물 수 반환
	public int getTotalCount(HashMap<String, String> map) {

		try {

			String where = "";

			if (map.get("search") != null) {
				// 검색중...
				where = String.format("where name like '%%%s%%' or subject like '%%%s%%' or content like '%%%s%%'",
						map.get("search"), map.get("search"), map.get("search"));
			}
			
			
			String sql = String.format("select count(*) as cnt from tblBoard %s", where);
			
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			
			
			if (rs.next()) {
				return rs.getInt("cnt");
			}
			

		} catch (Exception e) {
			System.out.println(e);
		}

		return 0;
	}


}
