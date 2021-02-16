package com.test.codestudy.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/board/write.do")
public class Write extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//로그인 안한 사람이 접근할 때 내 쫓기
		HttpSession session = request.getSession();
		
		if (session.getAttribute("id") == null) {
			
			//1. 내쫓기
//			response.sendRedirect("/codestudy/board/list.do");
//			return;
			
			//2. 경고(JavaScript) 후 내쫓기
			PrintWriter writer = response.getWriter();
			
			writer.print("<html><body>");
			writer.print("<script>");
			writer.print("alert('failed');");
			writer.print("location.href='/codestudy/board/list.do';");
			writer.print("</script>");
			writer.print("</body></html>");
			
			writer.close();
			
			
		}
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/board/write.jsp");
		dispatcher.forward(request, response);
		
	}

}

