<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<c:set value="${pageContext.request.contextPath }" var="root"></c:set>
    

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Commerce Around</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>

<!-- body Start -->
<body>

<!-- nav start -->
<nav class="navbar navbar-expand-sm bg-primary navbar-dark fixed-top shadow">
<div class="container">
  <a class="navbar-brand" href="${root }?acton=brand">Commerce Around</a>
  <ul class="navbar-nav">
    <li class="nav-item">
      <a class="nav-link" href="${root}/notice?action=movenotice">공지사항</a>	
    </li>
<!--     <li class="nav-item"> -->
<!--       <a class="nav-link" href="#">menu2</a> -->
<!--     </li> -->
<!--     <li class="nav-item"> -->
<!--       <a class="nav-link" href="#">menu3</a> -->
<!--     </li> -->
<!--     <li class="nav-item"> -->
<!--       <a class="nav-link" href="#">menu4</a> -->
<!--     </li> -->
  </ul>
  <div class="collapse navbar-collapse justify-content-end" id="collapsibleNavbar">
    <ul class="navbar-nav">

<c:if test="${empty memberInfo }">
      <li class="nav-item">
        <a class="nav-link" href="${root}/member?action=movelogin">Login</a>
     </li>
      <li class="nav-item">
        <a class="nav-link" href="${root}/member?action=movesignup">Sign Up</a>
     </li>
</c:if>
<c:if test="${!empty memberInfo }">
      <li class="nav-item">
        <a class="nav-link" href="${root}/member?action=logout">Logout</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${root}/member?action=showinfo">Info</a>
      </li>
</c:if>
    </ul>
  </div>
</div>
</nav>
<div style="height:100px;"></div>
<!-- nav end -->