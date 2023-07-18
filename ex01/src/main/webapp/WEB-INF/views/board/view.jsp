<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
<meta name="generator" content="Hugo 0.104.2">
<title>Insert title here</title>
<link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/navbar-fixed/">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">

<!-- CSS -->
<link href="/resources/css/style.css" rel="stylesheet">

<!-- JS -->
<script src="/resources/js/reply.js"></script>

<style>
   body {
	  min-height: 75rem;
	  padding-top: 4.5rem;
   }
}
</style>
<script type="text/javascript">

	/// header.jsp 에 window.onload가 이미 있어서 적용되지 않음
	/// window.onload는 한 개밖에 적용이 되지 않기 때문
	/// 그러면 왜 window.onload를 사용하는지는 모르겠음.
	window.addEventListener('load', function(){
		
		// 수정페이지로 이동
		btnEdit.addEventListener('click', function(){
			viewForm.action='/board/edit';
			viewForm.submit();
		});
		
		// 삭제 처리 후 리스트 페이지로 이동
		btnDelete.addEventListener('click', function(){
			viewForm.action='/board/delete';
			viewForm.submit();
		});
		
		// 리스트 페이지로 이동
		btnList.addEventListener('click', function(){
			viewForm.action='/board/list';
			viewForm.submit();
		});
		
		// 답글등록 버튼
		btnReplyWrite.addEventListener('click', function(){
			replyWrite();
		});
		
		// 댓글 목록 조회 및 출력
		getReplyList(1);
		
		/// 세션에 등록된 아이디와 작성자가 일치하지 않으면 수정, 삭제 버튼 숨김
		if('${userId}' != '${board.writer}'){
			btnEdit.style.display = 'none';
			btnDelete.style.display = 'none';
		}
	});

/* 	function requestAction(url) {
		viewForm.action=url;
		viewForm.submit();
	} */
	
</script>
</head>
<body>

<jsp:include page="../common/header.jsp"/>
<main class="container">
<div class="bg-light p-5 rounded">
	<div class="d-grid gap-2 d-md-flex justify-content-md-center">
		<p class="lead">부트스트랩을 이용한 게시판 만들기</p>
	</div>
	<div class="d-grid gap-2 d-md-flex justify-content-md-center">
		<h1>게시물</h1>
	</div>
	<div class="d-grid gap-2 d-md-flex justify-content-md-end">
	<!-- / 왜 둘로 나눴는지 생각해봐라 -->
	<c:if test="${ empty cri }" var="res">
<%-- 	<a class="btn btn-lg btn-primary" href="/board/list?pageNo=${param.pageNo }&searchField=${param.searchField}&searchWord=${param.searchWord}" role="button">리스트</a> --%>
		<a class="btn btn-lg btn-primary" href="#" id="btnList" role="button">리스트</a>
	</c:if>
	<c:if test="${ not res }">
		<a class="btn btn-lg btn-primary" href="#" id="btnList"
			role="button">리스트</a>
	</c:if>
	</div>
</div>
<form method="get" name="viewForm">
	<!-- 검색유지 -->
	<input type="hidden" name="pageNo" value=${param.pageNo }>
	<input type="hidden" name="searchField" value=${param.searchField }>
	<input type="hidden" name="searchWord" value=${param.searchWord }>
	<!-- / 이 input값은 댓글 리스트를 받아올 때 사용된다. -->
	<input type="hidden" name="bno" id="bno" value="${board.bno }">
	<div class="mb-3">
	  <label for="title" class="form-label">제목</label>
	  <input name="title" type="text" class="form-control" id="title" value="${board.title }" readonly>
	</div>
	<div class="mb-3">
	  <label for="content" class="form-label">내용</label>
	  <textarea name="content" class="form-control" id="content" rows="3" readonly>${board.content }</textarea>
	</div>
	<div class="mb-3">
	  <label for="writer" class="form-label">작성자</label>
	  <input name="writer" type="text" class="form-control" id="writer" value="${board.writer }" readonly>
	</div>
	<!-- / 자바스크립트로 처리하였기 때문에 아래 if문은 필요 없어짐 -->
	<c:if test="${ userId != board.writer }">
		<c:set value="disabled" var="disabled"></c:set>
	</c:if>
	<div class="d-grid gap-2 d-md-flex justify-content-md-center">
		<button type="button" class="btn btn-primary btn-lg ${ disabled }" id="btnEdit">수정하기</button>
		<!-- <button type="button" class="btn btn-primary btn-lg" id="btnEdit" onclick="requestAction('/board/edit')">수정하기</button> -->
		<button type="button" class="btn btn-secondary btn-lg ${ disabled }" id="btnDelete">삭제하기</button>
		<!-- <button type="button" class="btn btn-secondary btn-lg" id="btnDelete" onclick="requestAction('/board/delete')">삭제하기</button> -->
	</div>
	
</form>
<p></p>
<!-- 댓글 리스트 -->
<!-- 그냥 if문으로 답글작성 div를 감싸주게 되면 script에서 btnReplyWrite 버튼을 찾지 못해 오류가 발생한다. -->
<c:if test="${ empty member }"> <!-- / 맴버가 비어있으면 -->
	<c:set var="loginCheck" value="display:none"></c:set> <!-- / 변수생성해서 -->
</c:if>
	<input type="hidden" id="replyer" value="${ userId }">
	<div class="input-group" style="${loginCheck}"> <!-- / 스타일에 넣어준다. -->
	  <span class="input-group-text">답글작성</span>
	  <input type="text" aria-label="First name" class="form-control" id="reply">
	  <input type="button" id="btnReplyWrite" aria-label="Last name" value="등록하기" class="input-group-text">
	</div>
<div id="replyDiv"></div>
<input type="hidden" id="page" name="page" value="1">
<%-- <jsp:include page="../reply/test.jsp"/> --%>
</main>

<div id="test"></div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</body>
</html>