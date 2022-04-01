<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/template/header.jsp" %>
    
<script type="text/javascript">
$(document).ready(function(){
	$("#loginSubmitBtn").click(function(){
		if(!$("#loginid").val()){
			alert("아이디를 입력하세요");
	    	return;
		}else if(!$("#loginpw").val()){
	    	alert("비밀번호를 입력하세요");
	    	return;
		} else {
	    	$("#loginform").attr("action", "${root}/member").submit();
		}
	})
 });
</script>

<div class="container">
	<h3>Login</h3>
  <form id="loginform" method="post" action="">
    <div class="form-group">
      <input type="hidden" name="action" value="login">
      <label for="loginid">Id:</label>
      <input
        type="text" class="form-control" id="loginid" name="id" placeholder="id.." value=""/>
    </div>
    <div class="form-group">
      <label for="loginPw">Password:</label>
      <input type="password" class="form-control" id="loginpw" name="pass" placeholder="password.."/>
    </div>
    <div class="checkbox">
      <label><input type="checkbox" /> Remember me</label>
    </div>
    <button type="button" class="btn btn-default btn-primary" id="loginSubmitBtn">Login</button>
</form>
</div>  

