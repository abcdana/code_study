package com.test.codestudy.member;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.test.codestudy.DBUtil;

//서블릿 X -> 일반 클래스 O
public class MemberDAO {

	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
		
	public MemberDAO() {
		//DB 연결
		conn = DBUtil.open();
	}
	
	
	
	public void close() {
		
		try {
			
			conn.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	//RegisterOk 서블릿이 MemberDTO를 주면서 회원가입 시켜주세요.. 위임
	public int add(MemberDTO dto) {
		
		try {
			
			String sql = "insert into tblMember (seq, id, name, email, pw, pic, regdate) values (seqMember.nextVal, ?, ?, ?, ?, ?, default)";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, dto.getId());
			pstat.setString(2, dto.getName());
			pstat.setString(3, dto.getEmail());
			pstat.setString(4, dto.getPw());
			pstat.setString(5, dto.getPic());
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
		return 0;
	}


	//Login 서블릿이 id, pw을 주면서 회원이 맞는지 검사.. 위임
	public int login(MemberDTO dto) {
		
		try {
			
			String sql = "select count(*) as cnt from tblMember where id = ? and pw = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getId());
			pstat.setString(2, dto.getPw());
			
			rs = pstat.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("cnt"); //1 or 0
			} 
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		return 0;
	}


	//Login 서블릿이 id를 건네주면서 회원 정보를 달라고 위임
	public MemberDTO getMember(String id) {
		
		try {
			
			String sql = "select * from tblMember where id = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setNString(1, id);
			
			rs = pstat.executeQuery();
			
			if (rs.next()) {
				
				MemberDTO dto = new MemberDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				dto.setPic(rs.getString("pic"));
				dto.setRegdate(rs.getString("regdate"));
				
				return dto;
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return null;
	}


	
	
	//Send 서블릿 -> 나 빼고 다른 사람(회원)들을 모두 반환
	public ArrayList<MemberDTO> listMember(String seq) {

		try {
			
			String sql = "select seq, name, id from tblMember where seq <> ? order by name asc";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			rs = pstat.executeQuery();
			
			
			ArrayList<MemberDTO> mlist = new ArrayList<MemberDTO>();
			
			while (rs.next()) {
				//레코드 1줄 -> DTO 1개
				MemberDTO dto = new MemberDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setId(rs.getString("id"));

				mlist.add(dto);
			}
			
			return mlist;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		
		return null;
	}
	
}
