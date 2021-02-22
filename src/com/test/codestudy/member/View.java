package com.test.codestudy.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/view.do")
public class View extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//1.
		//2.DB 작업 -> select
		//3. 결과 + jsp 호출하기
		
		//1. 
		String seq = req.getParameter("seq");
		
		//2.
		MemberDAO dao = new MemberDAO();
		
		MessageDTO dto = dao.getMessage(seq);
		
		//읽은 쪽지 상태를 1 -> 2 바꾸기
		dao.updateRead(seq); 
		
		//3.
		req.setAttribute("dto", dto);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/view.jsp");
		dispatcher.forward(req, resp);

	}

}
