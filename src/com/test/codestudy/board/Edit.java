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

@WebServlet("/board/edit.do")
public class Edit extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		//view.do -> Edit.do?seq=글번호
		
		//1. 데이터 가져오기(seq)
		//2. DB 작업 -> select
		//3. 결과 반환 -> JSP 호출하기
		
		
		//1.
		String seq = req.getParameter("seq");
		
		
		//2.
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = dao.get(seq);  //글보기(view.do에서 사용하던 메소드)
		
		
		//2.5.
		//글쓴이 본인이 맞는지 확인
		//시점이 왜 여기냐? 글쓴 사람 번호를 알아야 하기 때문에 DB를 먼저 들렸다 와야한다.
		HttpSession session = req.getSession();
		
		if (!dto.getId().equals((String)session.getAttribute("id"))) {
			
			//권한 없음 -> 쫓아내기
			PrintWriter writer = resp.getWriter();
			
			writer.print("<html><body>");
			writer.print("<script>");
			writer.print("alert('failed');");
			writer.print("history.back();");
			writer.print("</script>");
			writer.print("</body></html>");
			
			writer.close();
			
			return; //********** 리턴 안시키면 큰일나요~
		}
		
		
		//3.
		req.setAttribute("dto", dto);
	
	

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/edit.jsp");
		dispatcher.forward(req, resp);

	}

}
