<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function go(page) {
		document.searchForm.pageNo.value=page;
		searchForm.submit();
	}
</script>
</head>
<body>
	<!-- 페이지 블럭 생성 -->
	<nav aria-label="Page navigation example">
	  <ul class="pagination justify-content-end">

	    <li class="page-item ${pageDto.prev?'':'disabled' }">
	      <button class="page-link" onclick="go(${ pageDto.startNo-1 })">Previous</button>
	    </li>

	    <c:forEach begin="${ pageDto.startNo }" end="${ pageDto.endNo }" var="i">
		    <li class="page-item"><button class="page-link ${ pageDto.cri.pageNo eq i?'active':'' }" onclick="go(${i})">${ i }</button></li>
	    </c:forEach>

	    <li class="page-item ${pageDto.next?'':'disabled' }">
	      <button class="page-link" onclick="go(${ pageDto.endNo+1 })">Next</button>
	    </li>

	  </ul>
	</nav>
</body>
</html>