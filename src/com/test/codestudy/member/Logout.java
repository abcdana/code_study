package com.test.codestudy.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//http://localhost:8090/codestudy/index.do

@WebServlet("/member/logout.do")
public class Logout extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. 로그아웃 하기 위해 옴
		//	: 로그 아웃 = 인증 티켓을 제거
		//2. 시작 페이지로 이동
		
		
		//1.
		HttpSession session = request.getSession();
		
		//session.removeAttribute("id");  //로그아웃
		session.invalidate(); //세션 초기화 -> 로그아웃 (더 확실하게 삭제) 세션 id까지 바뀌어버림
		
		//2.
		response.sendRedirect("/codestudy/index.do");
		
	}

}
