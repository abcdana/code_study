<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CodeStudy::Board</title>

<%@include file="/WEB-INF/views/inc/asset.jsp" %>
<link rel="stylesheet" href="/codestudy/css/board.css">

<style>

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
				<h1>자유 게시판 <small>Board</small></h1>
                
                <table class="table view">
                    <tr>
                        <td>
                            <span class="seq">${dto.seq}.</span>
                            <span class="subject">${dto.subject}.</span>
                            <span class="readcount">읽음(${dto.readcount})</span>
                            <span class="date">${dto.regdate}</span>
                            <span class="name">${dto.name}{${dto.id})</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="content">
                        	
                        	<c:if test="${dto.filename.toLowerCase().endsWith('jpg') || dto.filename.toLowerCase().endsWith('gif') || dto.filename.toLowerCase().endsWith('png')}">
                        	<img src="/codestudy/files/${dto.filename }" style="display: block; margin: 20px auto;">
                        	</c:if>
                        	
                        	${dto.content}
                        
                        </td>
                    </tr>
                    
                    <c:if test="${not empty dto.orgfilename }">
                    <tr>
                        <td>
                            <span class="glyphicon glyphicon-floppy-disk"></span>
                            <a href="/codestudy/board/download.do?filename=${dto.filename }&orgfilename=${dto.orgfilename}&seq=${dto.seq}">${dto.orgfilename }</a>
                            [download: ${dto.downloadcount }회]
                        </td>
                    </tr>
                    </c:if>
                    
                    <!-- 
                    <tr>
                        <td>
                            <button type="button" class="btn btn-default">
                                <span class="glyphicon glyphicon-thumbs-up"></span>
                                추천
                            </button>
                        </td>
                    </tr>
                     -->
                </table>


                <div class="btns btn-group">
                    <button type="button" class="btn btn-default" onclick="location.href='/codestudy/board/list.do?search=${search}';">
                        <span class="glyphicon glyphicon-th-list"></span>
                        목록
                    </button>
                    
                    
                    <c:if test="${not empty id }">
                    
                    <c:if test="${dto.id.equals(id)}">
                    <button type="button" class="btn btn-default" onclick="location.href='/codestudy/board/edit.do?seq=${dto.seq}';">
                        <span class="glyphicon glyphicon-minus"></span>
                        수정
                    </button>
                    
                    <button type="button" class="btn btn-default" onclick="location.href='/codestudy/board/delete.do?seq=${dto.seq}';">
                        <span class="glyphicon glyphicon-remove"></span>
                        삭제
                    </button>
                    </c:if>
                    
                    <button type="button" class="btn btn-default" onclick="location.href='/codestudy/board/reply.do';">
                        <span class="glyphicon glyphicon-share-alt"></span>
                        답변
                    </button>
                    </c:if>
                    
                    
                </div>
                <div style="clear:both;"></div>
				

                <!-- 
                <table class="table comment">
                    <tr>
                        <td>
                            <span class="comment">제목입니다.</span>
                            <span class="date">2020-01-01 10:10:10</span>
                            <span class="name">홍길동(hong)</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="comment">제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.제목입니다.</span>
                            <span class="date">2020-01-01 10:10:10</span>
                            <span class="name">홍길동(hong)</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="comment">제목입니다.</span>
                            <span class="date">2020-01-01 10:10:10</span>
                            <span class="name">홍길동(hong)</span>
                            <span class="delete">[삭제]</span>
                        </td>
                    </tr>
                </table>
 				
 				
                <div class="commentbox panel panel-default">
                    <div class="panel-body">
                        <input type="text" class="form-control" placeholder="comment">
                    </div>
                </div>
                -->
                
                
			</div>
		</div>
		<!-- ########## 내용 끝 -->

	</div>
	<!-- ########## 본문 끝 -->


	<!-- ########## 하단 시작 -->
	<%@include file="/WEB-INF/views/inc/footer.jsp" %>
	<!-- ########## 하단 끝 -->
	
	<script>
    
		$(".content img").ready(function() {
			//alert($(".content img").width();
		
			if ($(".content img").width() > 600) {
				$(".content img").width(600);
			}
		});
		
	
    </script>
</body>

</html>