package com.test.codestudy.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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

			String sql = "insert into tblBoard (seq, subject, content, regdate, readcount, mseq) values (seqBoard.nextVal, ?, ?, default, default, ?)";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getSubject());
			pstat.setString(2, dto.getContent());
			pstat.setString(3, dto.getMseq());

			return pstat.executeUpdate(); // 1 or 0

		} catch (Exception e) {
			System.out.println(e);
		}

		return 0;

	}

	
	//List 서블릿 -> 글 목록 달라고 위임
	public ArrayList<BoardDTO> list() {

		try {
			
			String sql = "select * from vwBoard order by seq desc";
			
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
			
			String sql = "select * from tblBoard where seq = ?";
			
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


}
