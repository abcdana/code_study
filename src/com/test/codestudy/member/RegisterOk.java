package com.test.codestudy.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/member/registerok.do")
public class RegisterOk extends HttpServlet{

   // doGet or doPost
   // 서블릿을 부를때 get방식으로 밖에 안불렀기 때문에 doGET을 사용했다
   // form 태그에서 POST 방식으로 호출할때에는 doPost 함수를 구현해야한다
   
   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
      //1. 데이터 가져오기
      //2. 파일 업로드 처리(pic)
      //3. DB 작업 > insert
      //4. 결과 반환 > 처리
      
      //1. + 2.
      req.setCharacterEncoding("UTF-8");
      
      String id = "";
      String name = "";
      String email = "";
      String pw = "";
      String pic = "";
      int result = 0; // 업무 결과

      
      try {
         
         // lib
         // cos.jar
         // jstl-1.2jar
         // ojdbc6.jar
         
         MultipartRequest multi = new MultipartRequest(
                                 req,
                                 req.getRealPath("/pic"),
                                 1024 * 1024 * 10,
                                 "UTF-8",
                                 new DefaultFileRenamePolicy()
                              );
         
         //첨부 파일명 -> 첨부파일을 안눌렀으면 ? -> null or "" 둘 중 뭘까??
         //System.out.println(multi.getFilesystemName("pic"));   -> null 로 뜬다.
         
         // C:\class\server\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\codestudy\pic
         // 탐색기에서 위의 주소로 이동한후 첨부한 파일이 있는지 확인
         
         //req.getParameter() -> multi.getParameter()
         id = multi.getParameter("id");
         name = multi.getParameter("name");
         email = multi.getParameter("email");
         pw = multi.getParameter("pw");
         pic = multi.getFilesystemName("pic") != null ? 
        		 multi.getFilesystemName("pic") : "nopic.png";
         
        System.out.println("");
        		 
        		 
        //DB 작업 -> 위임
        // - DAO + DTO
        MemberDAO dao = new MemberDAO();
        MemberDTO dto = new MemberDTO();
        
        dto.setId(id);
        dto.setName(name);
        dto.setEmail(email);
        dto.setPw(pw);
        dto.setPic(pic);
        
        result = dao.add(dto); //위임 -> 1(성공) 0(실패)
        
        
        
		//결과 : JSP 작업 X -> Servlet 작업 O
		if (result == 1) {
			//회원가입 성공
			resp.sendRedirect("/codestudy/index.do");
		} else {
			//회원가입 실패
			PrintWriter writer = resp.getWriter();
			
			writer.print("<html><body>");
			writer.print("<script>");
			writer.print("alert('failed');");
			writer.print("history.back();");
			writer.print("</script>");
			writer.print("</body></html>");
			
			writer.close();
			
		}
        
        		 
         
      } catch (Exception e) {
         System.out.println(e);
      }
   }
   
   
}