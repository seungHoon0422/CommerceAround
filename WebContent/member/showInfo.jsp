<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/template/header.jsp" %>
   
<script>
  $(document).ready(function () {
    $("#fixInfoBtn").click(function () {
    /*
      if (!$("#userId").val()) {
        alert("아이디를 입력하세요");
        return;
      } else if (!$("#userName").val()) {
        alert("이름을 입력하세요");
        return;
      } else if (!$("#userPassword").val()) {
        alert("비밀번호를 입력하세요");
        return;
      } else if (!$("#userAddress").val()) {
        alert("주소를 입력하세요");
        return;
      } else if (!$("#userTelephone").val()) {
        alert("전화번호를 입력하세요");
        return;
      } else if (!$("#userQuestion").val()) {
        alert("질문를 입력하세요");
        return;
      } else if (!$("#userAnswer").val()) {
        alert("질문에 해당하는 답을 입력하세요");
        return;
      } else { */
        $("#registerform").attr("action", "${root}/member").submit();
      //}
    });
  });
</script>

   	
<div class="container">
	<h3>User Information</h3>
  <form id="registerform">
    <input type="hidden" name="action" value="fixinfo">
    <div class="form-group">
      <label for="formGroupExampleInput">Id</label>
      <input type="text" class="form-control" id="id" name="id" value="${memberInfo.id}">
    </div>
    <div class="form-group">
      <label for="formGroupExampleInput">Name</label>
      <input type="text" class="form-control" id="name" name="name" value="${memberInfo.name}">
    </div>
    <div class="form-group">
      <label for="formGroupExampleInput">Password</label>
      <input type="text" class="form-control" id="pass" name="pass" value="${memberInfo.pass}">
    </div>
    <div class="form-group">
      <label for="formGroupExampleInput">Address</label>
      <input type="text" class="form-control" id="address" name="address" value="${memberInfo.address}">
    </div>
    <div class="form-group">
      <label for="formGroupExampleInput">Phone Number</label>
      <input type="text" class="form-control" id="phoneNum" name="phoneNum" value="${memberInfo.phoneNum}">
    </div>
    <div class="form-group">
      <label for="formGroupExampleInput">Question</label>	
      <input type="text" class="form-control" id="question" name="question" value="${memberInfo.question}">
    </div>
    <div class="form-group">
      <label for="formGroupExampleInput">Answer</label>
      <input type="text" class="form-control" id="answer" name="answer" value="${memberInfo.answer}">
    </div>
	<button type="button" class="btn btn-default btn-primary" id="fixInfoBtn">Submit</button>
  </form>
</div>  
    
    
<%@ include file="/template/footer.jsp" %>