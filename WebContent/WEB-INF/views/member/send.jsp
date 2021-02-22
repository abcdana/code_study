<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CodeStudy::Auth</title>

<%@include file="/WEB-INF/views/inc/asset.jsp" %>
<link rel="stylesheet" href="/codestudy/css/auth.css">

<style>

	.message th:nth-child(1) { width: 60px; }
	.message th:nth-child(2) { width: 120px; }
	.message th:nth-child(3) { width: auto; }
	.message th:nth-child(4) { width: 170px; }
	
	.message th, .message td { text-align: center; }
	.message td:nth-child(3) { text-align: left; } 

</style>
</head>

<body>

   <!-- ########## 상단 헤더 시작 -->
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
            <h1>쪽지 <small>Message</small></h1>
                
			<!-- 
			받는 사람 : 누구? -> 얘만 문제
			보낸 사람 : 나
			내용 : 입력받기 
			-->
			
			${mlist.size() }
            
            <div class="btns">
            	<button type="button" class="btn btn-default" onclick="location.href='/codestudy/member/send.do';">쪽지 보내기</button>
            	<button type="button" class="btn btn-default" onclick="">쪽지 삭제하기</button>
            </div>
            
            <div style="clear:both;"></div>

         </div>
      </div>
      <!-- ########## 내용 끝 -->

   </div>
   <!-- ########## 본문 끝 -->


   <!-- ########## 하단 시작 -->
   <%@include file="/WEB-INF/views/inc/footer.jsp" %>
   <!-- ########## 하단 끝 -->
   
   <script>
    

   
    </script>
</body>

</html>