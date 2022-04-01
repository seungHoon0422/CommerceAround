<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/template/header.jsp" %>
      

<script type="text/javascript">
  // 유효성 검사
  $(document).ready(function() {
    var isId = false;
    $("#userId").keyup(function () {
      var ckid = $("#userId").val();
      if(ckid.length < 5 || ckid.length > 16) {
        $("#idresult").text("아이디는 5자이상 16자이하입니다.").removeClass('text-primary').removeClass('text-danger').addClass('text-dark');
        isId = false;
      } else {
        $.ajax({
          url: '${root}/member',
          data: {'action': 'checkid', 'ckid': ckid},
            type: 'GET',
            dataType: 'text',
            success: function (response) {
              console.log(response);
              var cnt = parseInt(response);
              if(cnt == 0) {
                $("#idresult").text(ckid + "는 사용가능합니다.").removeClass('text-dark').removeClass('text-danger').addClass('text-primary');
                isId = true;
              } else {
                $("#idresult").text(ckid + "는 사용할 수 없습니다.").removeClass('text-dark').removeClass('text-primary').addClass('text-danger');
                isId = false;
              }
            }
		});
      }
    });

    // 회원가입
    $("#signUpSubmitBtn").click(function () {
      if (!$("#name").val()) {
          alert("이름을 입력하세요");
          return;
      } else if (!$("#id").val()) {
          alert("아이디를 입력하세요");
          return;
      } else if (!$("#pass").val()) {
          alert("비밀번호를 입력하세요");
          return;
      } else if (!$("#address").val()) {
          alert("주소를 입력하세요");
          return;
      } else if (!$("#phoneNum").val()) {
          alert("전화번호를 입력하세요");
          return;
      } else if (!$("#question").val()) {
          alert("질문를 입력하세요");
          return;
      } else if (!$("#answer").val()) {
          alert("질문에 해당하는 답을 입력하세요");
          return;
      }else {
          $("#registerform").attr("action", "${root}/member").submit();
      }
    });

  });

</script>
  <div class="container">
  	<h4>Sign Up</h4>
    <form id="registerform" action="">
      <div class="form-group">
        <input type="hidden" name="action" id="action" value="register">
        <label for="id">Id:</label>
        <input type="text" class="form-control" id="id" name="id" placeholder = "id"/>
        <div id="idresult" class="mt-1"></div>
      </div>
      <div class="form-group">
        <label for="pass">Password:</label>
        <input type="password" class="form-control" id="pass" name="pass" placeholder = "password"/>
      </div>
      <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" class="form-control" id="name" name="name" placeholder = "name"/>
      </div>
      <div class="form-group">
        <label for="address">Address:</label>
        <input type="text" class="form-control" id="address" name="address" placeholder = "address"/>
      </div>
      <div class="form-group">
        <label for="phoneNum">Phone Number:</label>
        <input type="text" class="form-control" id="phoneNum" name="phoneNum" placeholder = "ex) 012-3456-7890"/>
      </div>
      <div class="form-group">
        <label for="question">Question:</label>
        <input type="text" class="form-control" id="question" name="question" placeholder = "비밀번호를 찾기 위한 질문을 작성해주세요"/>
      </div>
      <div class="form-group">
        <label for="answer">Answer:</label>
        <input type="text" class="form-control" id="answer" name="answer" placeholder = "위 질문의 정답을 작성해주세요"/>
      </div>
      <button type="button" class="btn btn-default btn-primary" id="signUpSubmitBtn">Submit</button>
    </form>
  </div>

<%@ include file="/template/footer.jsp" %>

    
    