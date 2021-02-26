<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CodeStudy::Hello</title>

<%@include file="/WEB-INF/views/inc/asset.jsp" %>

<style>

</style>
</head>

<body>

   <!-- ########## 상단 헤더 시작 -->
   <!-- 서블릿을 include 하는 방법 
   include file="/WEB-INF/views/inc/asset.jsp" 과 동일
   -->
   <%
      out.flush();
       RequestDispatcher dheader = request.getRequestDispatcher("/inc/header.do");
       dheader.include(request, response);
   %>
   <!-- ########## 상단 헤더 끝 -->


   <!-- ########## 본문 시작 -->
   <div class="main">

      <!-- ########## 좌측 시작 -->
      <div class="menu col-sm-12 col-md-3">
         <!-- ########## 메뉴 시작 -->
         <%
            out.flush();
             RequestDispatcher dmenu = request.getRequestDispatcher("/inc/menu.do");
            dmenu.include(request, response);
         %>
         <!-- ########## 메뉴 끝 -->

         <!-- ########## 인증 시작 -->
         <%
            out.flush();
             RequestDispatcher dauth = request.getRequestDispatcher("/inc/auth.do");
             dauth.include(request, response);
         %>
         <!-- ########## 인증 끝 -->

         <!-- ########## 채팅 시작 -->
         <%
            out.flush();
             RequestDispatcher dchat = request.getRequestDispatcher("/inc/chat.do");
            dchat.include(request, response);
         %>
         <!-- ########## 채팅 끝 -->
      </div>
      <!-- ########## 좌측 끝 -->



      <!-- ########## 내용 시작 -->
      <div class="content col-sm-12 col-md-9">
         <div>
            <h1>
               Ajax
            </h1>
            <div style="min-height: 747px">
            
            	
				<input type="button" value="확인" id="btn1" class="btn btn-default">
				
				<hr>
				<div id="result"></div>
				
				<hr>
				<input type="text" class="form-control">
            
            </div>
         </div>
         
      </div>
      <!-- ########## 내용 끝 -->

   </div>
   <!-- ########## 본문 끝 -->


   <!-- ########## 하단 시작 -->
   <%@include file="/WEB-INF/views/inc/footer.jsp" %>
   <!-- ########## 하단 끝 -->
   
   <script>
   
   		//Ajax <-> Server
   		//1. Ajax -> (데이터) -> Server
   		//2. Ajax -> Server -> (데이터) -> Ajax
   		//3. Ajax -> (데이터) -> Server -> (데이터) -> Ajax
   
   
		$("#btn1").click(function(){
			
			$.ajax({
				
				type: "GET",	//필수:: 요청 메서드(GET/ POST -> 크다 / 작다 : 잘 모르면 POST로 보내자)
				url: "/codestudy/ajax/ex04data.do",		//필수:: 응답 프로그램 주소(action)
				//data: "" 	//선택:: QueryString(key=value&key=value&key=value)
				async: true, 		//필수지만 생략가능 :: true(비동기), or false(동기)
				dataType: "text",	//필수지만 생략가능, 돌려받는 데이터의 형식 : (생략하면 text) 가능한 값 : text / xml / json
				success: function(result) {	//선택:: 돌려받을 데이터가 있을 때 만 구현
				
					$("#result").text(result);
					
				},
				error: function(a, b, c) {	//선택:: 예외 처리
					console.log(a,b,c);
				}
				
			});
			
		});
   
   </script>
   
   
</body>

</html>











