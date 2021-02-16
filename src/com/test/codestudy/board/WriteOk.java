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

@WebServlet("/board/writeok.do")
public class WriteOk extends HttpServlet {

	//GET -> doGet // O
	//POST -> doPost // O
	
	//GET -> doPost // X  -> 405 Error
	//POST -> doGet // X  -> 405 Error
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//1. 데이터 가져오기 (subject, content)
		//2. DB 작업 > insert
		//3. 결과 처리
		
		HttpSession session = req.getSession();
		
		//1.
		req.setCharacterEncoding("UTF-8");
		
		
		//insert into tblBoard (seq, subject, content, regdate, readcount, mseq) 
	    //	values (seqBoard.nextVal, '게시판 테스트입니다.', '내용입니다.', default, default, 1);
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		String mseq = (String)session.getAttribute("seq"); //로그인한 회원 번호 & 글쓴이 번호
		
		
		//2.
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = new BoardDTO();
		
		
		dto.setSubject(subject);
		dto.setContent(content);
		dto.setMseq(mseq);
		
		int result = dao.write(dto); // 글쓰기
		
		
		if (result == 1) {
			//글쓰기 성공 -> 게시판 목록으로 이동
			resp.sendRedirect("/codestudy/board/list.do");
		} else {
			//글쓰기 실패 -> 경고 + 뒤로가기
			PrintWriter writer = resp.getWriter();
			
			writer.print("<html><body>");
			writer.print("<script>");
			writer.print("alert('failed');");
			writer.print("history.back();");
			writer.print("</script>");
			writer.print("</body></html>");
			
			writer.close();
			
		}
		
		

	}

}
