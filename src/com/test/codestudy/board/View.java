package com.test.codestudy.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/board/view.do")
public class View extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//1. 데이터 가져오기(seq)
		//2. DB 작업 -> select
		//3. 결과 반환 -> JSP 호출하기 + 전달
		
		
		HttpSession session = request.getSession();
		
		
		//1.
		String seq = request.getParameter("seq");
		
		
		
		//2.
		BoardDAO dao = new BoardDAO();
		
		//여기서 조회수 증가를 하면 내가 지금 읽은 것 까지 반영
		//2.3. 조회수 증가 + 글 view에서 f5(새로고침)눌렀을 때 조회수 증가하는것을 방지 + 북마크로 오는사람들
		if (session.getAttribute("read") == null || (boolean)session.getAttribute("read") == false) {
			dao.updateReadcount(seq);
			session.setAttribute("read", true);
		}
		
		BoardDTO dto = dao.get(seq);
		
		dao.close(); //******빼먹으면 db가 죽는다.
		
		//여기서 조회수 증가를 하면 내가 읽은 것은 빼고 반영

		
		//2.5. 데이터 조작
		//개행 문자 출력하기
		dto.setContent(dto.getContent().replace("\r\n", "<br>"));
		
		
		
		
		//3. 
		request.setAttribute("dto", dto);
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/board/view.jsp");
		dispatcher.forward(request, response);
		
	}

}

