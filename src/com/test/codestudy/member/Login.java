package com.test.codestudy.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/member/login.do")
public class Login extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//1. 데이터 가져오기 (id, pw)
		//2. DB 작업 > select(등록된 회원이 맞는지 확인)
		//3. 결과 처리
		
		
		//1.
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		
		//2.
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = new MemberDTO();
		
		dto.setId(id);
		dto.setPw(pw);
	
		
		int result = dao.login(dto); //1 or 0
		
		
		//3.
		if (result == 1) {
			//로그인 성공
			//인증(Authentication), 허가(Authorization)
			
			//회원 인증 -> 영구 보관(적어도 사이트에 머무르는 동안...)
			// -> 저장 공간 + 상태 유지 -> 전역 (사이트 전체)이면서 개인적인 공간이어야 한다. -> 세션
			
			//*** 로그인을 성공했다는 사실을 세션에다가 저장
			
			//서블릿에서 세션을 접근하는 방법
			HttpSession session = req.getSession();
			
			//session.setAttribute("login", true);
			session.setAttribute("id", dto.getId()); //가장 유용하게 쓰이는 값을 넣는것이 이득
			// -> "인증티켓"이라고 부름
			
			//시작 페이지로 이동
			resp.sendRedirect("/codestudy/index.do");
			
		} else {
			//로그인 실패
			PrintWriter writer = resp.getWriter();
			
			writer.print("<html><body>");
			writer.print("<script>");
			writer.print("alert('failed')");
			writer.print("history.back();");
			writer.print("</script>");
			writer.print("</body></html>");
			
			writer.close();
		}
		
	}

}
