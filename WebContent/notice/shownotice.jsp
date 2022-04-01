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

        	
	   function deleteArticle(no) {
	   	if(confirm("정말로 삭제??")) {
	   		location.href = "${root}/notice?action=delete&articleno=" + no;
	   	}
 
        	
        	
    </script>

<div class="container text-center mt-3">
	<div class="col-lg-8 mx-auto">
		<div class="row col-lg-8 p-3 m-3 mx-auto justify-content-center">
    		<h2 class="text-dark">게시글</h2>
  		</div>
  		
  		<div class="container border border-primary">
	      <div class="row">
	        <div class="col-sm-2 p-2 border border-primary">작성자</div>
	        <div class="col-sm-4 p-2 border border-primary">${article.userId }</div>
	        <div class="col-sm-2 p-2 border border-primary">날짜</div>
	        <div class="col-sm-4 p-2 border border-primary">${article.regTime }</div>
	      </div>
	      <div class="row">
	        <div class="col-sm-2 p-2 border border-primary">제목</div>
	        <div class="col p-2 border border-primary">${article.subject }</div>
	      </div>
	      <div class="row text-left">
	        <div class="col p-2 border border-primary" style="height:300px;">${article.content }</div>
	      </div>
		</div>
        <div class="text-center m-3">
        	<a href="${root}/notice?action=movenotice">목록</a>
        	<a href="${root}/notice?action=mvmodify&articleno=${article.articleNo}">수정</a>
        	<a href="javascript:deleteArticle(${article.articleNo});">삭제</a>
        </div>
	</div>
</div>
</c:if>
<%@ include file="/template/footer.jsp" %>