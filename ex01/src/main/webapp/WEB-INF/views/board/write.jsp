<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link href="/resources/css/style.css" rel="stylesheet">
<style>
   body {
	  min-height: 75rem;
	  padding-top: 4.5rem;
   }
</style>
<script type="text/javascript">
	function requestAction(url) {
		writeForm.action=url;
		writeForm.submit();
	}
	
	window.addEventListener('load', function(){
		
		btnList.addEventListener('click', function(){
			writeForm.action='/board/list';
			writeForm.method='get';
			writeForm.submit();
		})
	})
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
		<c:if test="${ empty board }" var="res">
			<h1>글쓰기</h1>	
		</c:if>
		<c:if test="${ not res }">
			<h1>게시물 수정</h1>
		</c:if>
	</div>
	<div class="d-grid gap-2 d-md-flex justify-content-md-end">
	<a class="btn btn-lg btn-primary" href="#"
		role="button" id="btnList">리스트</a>
	</div>
</div>
<p></p>
<div class="list-group w-auto">

	<form name="writeForm" method="post" enctype="multipart/form-data">
		<!-- 검색유지 -->
		<input type="hidden" name="pageNo" value="${ param.pageNo }">
		<input type="hidden" name="searchField" value="${param.searchField }">
		<input type="hidden" name="searchWord" value="${param.searchWord }">
		
		<div class="mb-3">
		  <label for="title" class="form-label">제목</label>
		  <input name="title" type="text" class="form-control" id="title" value=${board.title }>
		</div>
		<div class="mb-3">
		  <label for="content" class="form-label">내용</label>
		  <textarea name="content" class="form-control" id="content" rows="3">${board.content }</textarea>
		</div>
		<div class="mb-3">
		  <label for="writer" class="form-label">작성자</label>
		  <input name="writer" type="text" class="form-control" id="writer" value=${userId }
		  						${ not res?"readonly":"" }>
		</div>
		<div class="mb-3">
		  <label for="files" class="form-label">첨부파일</label>
		  <input name="files" type="file" class="form-control" id="files" multiple>
		</div>
		<div class="d-grid gap-2 d-md-flex justify-content-md-center">
			<!-- board가 없으면 글쓰기 -->
			<c:if test="${ res }">
				<button type="button" class="btn btn-primary btn-lg" onclick="requestAction('/board/write')">글쓰기</button>
			</c:if>
			<!-- board가 있으면 수정하기 -->
			<c:if test="${ not res }">
				<button type="button" class="btn btn-primary btn-lg" onclick="requestAction('/board/edit')">수정완료</button>
				<input type="hidden" name="bno" value="${board.bno }">
			</c:if>
			<button type="reset" class="btn btn-secondary btn-lg">초기화</button>
		</div>
		<!-- <input type="text" name="title"> -->
	</form>
	
</div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</body>
</html>