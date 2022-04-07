<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        	$("#mvRegisterBtn").click(function () {
                location.href = "${root}/notice?action=moveregister";
            });
        	$(".page-item").click(function () {
               	$("#pg").val($(this).attr("data-pg"));
               	$("#pageForm").attr("action", "${root}/notice").submit();
            });
        });
        
        function deleteArticle(no) {
        	if(confirm("정말로 삭제??")) {
        		location.href = "${root}/notice?action=delete&articleno=" + no;
        	}
        }
    </script>
    <form name="pageForm" id="pageForm">
    	<input type="hidden" name="act" id="act" value="list" />
    	<input type="hidden" name="pg" id="pg" value="" />
    	<input type="hidden" name="key" id="key" value="${key}" />
    	<input type="hidden" name="word" id="word" value="${word}" />
    </form>

    <div class="container text-center mt-3">
<!-- -------------------------------------------- -->
      <div class="row col-lg-8 p-3 m-3 mx-auto justify-content-center">
        <h2 class="text-info ">공지사항</h2>
      </div>
      <div class="row">
        <div class="row"></div>
        <form class=" col form-inline justify-content-end" action="${root}/notice">
          <input type="hidden" name="action" value="movenotice">
          <input type="hidden" name="pg" value="1">
          <select name="key" class="form-control">
            <option value="userid"> 아이디
            <option value="subject"> 제목
            <option value="articleno"> 글번호
          </select>
          <input type="text" class="ml-1 form-control" name="word">
          <button type="submit" class="ml-1 btn btn-outline-primary">검색</button>
        </form>

      </div>
      <div class="row">
        <div class="m-3 col text-right justify-content-end">
          <button type="button" id="mvRegisterBtn" class="btn btn-link">글작성</button>
        </div>
      </div>
	<c:if test="${!empty articles}">    
		<table class="table table-striped text-center">
		<thead>
		  <tr >
		    <th class="col-2" scope="col">번호</th>
		    <th class="col-6" scope="col"> 제목</th>
		    <th class="col-2" scope="col">작성자</th>
		    <th class="col-2" scope="col">작성일</th>
		  </tr>
		</thead>
        <tbody>
		<c:forEach var="article" items="${articles}">
              <tr>
                <th scope="row">${article.articleNo}</th>
                <td><a href="${root}/notice?articleNo=${article.articleNo}">${article.subject}</a></td>
                <td>${article.userId}</td>
                <td>${article.regTime}</td>
              </tr>
		</c:forEach>
        </tbody>
        </table>
        <div class="row m-3 justify-content-center">
        	${navi.navigator}
        </div>
	</c:if>  
	<c:if test="${empty articles}">    
			<table class="table table-striped text-left">
                    <tr class="table-info">
                        <td colspan="2">작성한 글이 없습니다.</td>
                    </tr>
            </table>
	</c:if>       
            
            



<!-- -------------------------------------------- -->
<!--         <div class="col-lg-8 mx-auto"> -->
<!--             <h2 class="p-3 mb-3 shadow bg-light"><mark class="sky">공지사항</mark></h2> -->
<!--             <div class="m-3 text-right"> -->
<!--                 <button type="button" id="mvRegisterBtn" class="btn btn-link">글작성</button> -->
<!--             </div> -->
<!--             <div class="m-3 row justify-content-end"> -->
<%--             	<form class="form-inline" action="${root}/notice"> --%>
<!-- 	            	<input type="hidden" name="action" value="movenotice"> -->
<!-- 	            	<input type="hidden" name="pg" value="1"> -->
<!-- 	            	<select name="key" class="form-control"> -->
<!-- 	            		<option value="userid"> 아이디 -->
<!-- 	            		<option value="subject"> 제목 -->
<!-- 	            		<option value="articleno"> 글번호 -->
<!-- 	            	</select> -->
<!-- 	            	<input type="text" class="ml-1 form-control" name="word"> -->
<!-- 	            	<button type="submit" class="ml-1 btn btn-outline-primary">검색</button> -->
<!--             	</form> -->
<!--             </div> -->
<%-- 	<c:if test="${!empty articles}">     --%>
<%-- 		<c:forEach var="article" items="${articles}"> --%>
<!--             <table class="table table-active text-left"> -->
<!--                 <tbody> -->
<!--                     <tr class="table-info"> -->
<%--                         <td>작성자 : ${article.userName}</td> --%>
<%--                         <td class="text-right">작성일 : ${article.regTime}</td> --%>
<!--                     </tr> -->
<!--                     <tr> -->
<!--                         <td colspan="2" class="table-danger"> -->
<%--                             <strong>${article.articleNo}. ${article.subject}</strong> --%>
<%--                            <c:if test="${memberInfo.id eq article.userId}"> --%>
<!--                             <div class="text-right"> -->
<%--                             	<a href="${root}/notice?action=mvmodify&articleno=${article.articleNo}">수정</a> --%>
<%--                             	<a href="javascript:deleteArticle(${article.articleNo});">삭제</a> --%>
<!--                             </div> -->
<%--                             </c:if>  --%>
<!--                         </td> -->
<!--                     </tr> -->
<!--                     <tr> -->
<%--                         <td class="p-4" colspan="2">${article.content}</td> --%>
<!--                     </tr> -->
<!--                 </tbody> -->
<!--             </table> -->
<%-- 		</c:forEach> --%>
<%-- 		${navi.navigator} --%>
<%-- 	</c:if>   --%>
<%-- 	<c:if test="${empty articles}">     --%>
<!-- 			<table class="table table-active text-left"> -->
<!--                     <tr class="table-info"> -->
<!--                         <td colspan="2">작성한 글이 없습니다.</td> -->
<!--                     </tr> -->
<!--             </table> -->
<%-- 	</c:if>        --%>
        </div>
</c:if>
<%@ include file="/template/footer.jsp" %>