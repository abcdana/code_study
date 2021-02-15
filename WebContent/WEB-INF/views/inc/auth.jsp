<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty id }">
<div class="login panel panel-default">
   <div class="panel-heading">
      인증 <small>Auth</small>
   </div>
   <div class="panel-body">
      <div class="pic"></div>
      <div class="itemlist">
         <div class="item">홍길동</div>
         <div class="item">2020-01-01 10:11</div>
         <div class="item">2020-01-01 10:11</div>
         <div class="item">2020-01-01 10:11</div>
      </div>
   </div>
</div>
</c:if>