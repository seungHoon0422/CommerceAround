<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ include file="/template/header.jsp" %>
<c:if test="${empty memberInfo}">
	<script>
	alert("로그인 사용자만 볼 수 있는 페이지입니다.");
	location.href = "${root}/member?action=movelogin";
	</script>
</c:if>
<c:if test="${!empty memberInfo}">
    <style>
        mark.sky {
            background: linear-gradient(to top, #54fff9 20%, transparent 30%);
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#registerBtn").click(function () {
                if (!$("#subject").val()) {
                    alert("제목 입력!!!!");
                    return;
                } else if (!$("#content").val()) {
                    alert("내용 입력!!!!");
                    return;
                } else {
                    $("#writeform").attr("action", "${root}/notice").submit();
                }
            });
        });
    </script>

    <div class="container text-center mt-3">
        <div class="col-lg-8 mx-auto">
      <div class="row col-lg-8 p-3 m-3 mx-auto justify-content-center">
        <h2 class="text-info ">공지사항 작성</h2>
      </div>
            <form id="writeform" class="text-left mb-3" method="post" action="">
            	<input type="hidden" name="action" value="register" />
                <div class="form-group">
                    <label for="subject">제목:</label>
                    <input type="text" class="form-control" id="subject" name="subject" placeholder="제목...">
                </div>
                <div class="form-group">
                    <label for="content">내용:</label>
                    <textarea class="form-control" rows="15" id="content" name="content"></textarea>
                </div>
                <div class="text-center">
                    <button type="button" id="registerBtn" class="btn btn-outline-primary">글작성</button>
                    <button type="reset" class="btn btn-outline-danger">초기화</button>
                </div>
            </form>
        </div>
    </div>
</c:if>
<%@ include file="/template/footer.jsp" %>