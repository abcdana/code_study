package com.test.codestudy.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/board/list.do")
public class List extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. DB 작업 > select 
		//2. 목록 반환 + jsp 전달 & 호출하기
		
		
		HttpSession session = request.getSession();
		
		//view.do -> 새로고침 조회수 증가 방지 -> 플래그 생성
		session.setAttribute("read", false);
		
		
		
		
		//1.
		BoardDAO dao = new BoardDAO();
		
		ArrayList<BoardDTO> list = dao.list();
		
		
		//데이터 조작 -> 서블릿 담당
		//데이터 출력 -> JSP 담당
		
		//1.5. 데이터 조작
		for (BoardDTO dto : list) {
			
			//날짜에서 시간 잘라내기 yyyy-mm-dd로 표기 
			dto.setRegdate(dto.getRegdate().substring(0, 10));
			
			
			//글 제목이 너무 길면 자르기
			if (dto.getSubject().length() > 34) {
				
				dto.setSubject(dto.getSubject().substring(0, 34) + " ...");
			}
			
		}
		
		
		
		//2. 
		request.setAttribute("list", list);
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		dispatcher.forward(request, response);
		
	}

}

