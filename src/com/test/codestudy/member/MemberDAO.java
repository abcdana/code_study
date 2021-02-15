package com.test.codestudy.member;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
	
}
