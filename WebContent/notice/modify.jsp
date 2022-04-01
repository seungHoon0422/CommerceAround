<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ include file="/template/header.jsp" %>
<c:if test="${empty memberInfo}">
	<script>
	alert("로그인 사용자만 볼 수 있는 페이지입니다.");
	location.href = "${root}/member?action=mvlogin";
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
            $("#modifyBtn").click(function () {
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
            <h2 class="p-3 mb-3 shadow bg-light"><mark class="sky">글수정</mark></h2>
            <form id="writeform" class="text-left mb-3" method="post" action="">
            	<input type="hidden" name="action" value="modify" />
            	<input type="hidden" name="articleno" value="${article.articleNo}" />
                <div class="form-group">
                    <label for="subject">제목:</label>
                    <input type="text" class="form-control" id="subject" name="subject" value="${article.subject}" placeholder="제목...">
                </div>
                <div class="form-group">
                    <label for="content">내용:</label>
                    <textarea class="form-control" rows="15" id="content" name="content">${article.content}</textarea>
                </div>
                <div class="text-center">
                    <button type="button" id="modifyBtn" class="btn btn-outline-primary">글수정</button>
                    <button type="reset" class="btn btn-outline-danger">초기화</button>
                </div>
            </form>
        </div>
    </div>
</c:if>
<%@ include file="/template/footer.jsp" %>