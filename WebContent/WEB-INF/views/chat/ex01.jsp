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
               채팅 <small>Chat</small>
            </h1>
            <div style="min-height: 747px">
            
            	<!-- 
            	
            	웹 클라이언트(브라우저) <-> [통신] <-> 웹서버
            	1. <iframe> 사용 + 타이머(선택)
            	2. Ajax
            	3. WebSocket
            	
            	 -->

				<h2>WebSocket</h2>
				
				<input type="button" id="btn1" value="클릭">
				<div id="output1"></div>


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
   
   		//var, let, const(상수)
   
		const url = "ws://echo.websocket.org";
		let ws; 
		
		$("#btn1").click(function(){
			
			//웹 소켓 > 서버와 통신
			//1. 소켓 생성
			//2. 서버 접속
			//3. 통신(데이터 주고 받기)
			//4. 서버 접속 종료
			
			
			//1. + //2.
			ws = new WebSocket(url);
			
			
			//3. 비동기 사건 : 언제 일어날 지 모르는 사건 -> event로 구현
			
			//서버와 연결이 되는 순간 발생
			ws.onopen = function(evt) {
				console.log("서버와 연결되었습니다.");
				ws.send("Hello");  //나 -> 서버
			};
			
			//서버가 나에게 메세지를 보내는 순간 발생
			ws.onmessage = function(evt) {
				//서버 -> 나
				console.log("서버에서 클라이언트에게 메시지를 전송했습니다.");
				console.log(evt.data);
				
				//소켓 연결 종료
				ws.close();
			};
			
			//서버와 연결이 종료되는 순간 발생
			ws.onclose = function(evt) {
				console.log("서버와 연결이 끊겼습니다.");	
			};
			
			//에러가 발생할 때 발생
			ws.onerror = function(evt) {
				console.log("error", evt.data, "color: tomato");	
			};
			
			
			
			
		});
		
		
   
   </script>
   
   
</body>

</html>











