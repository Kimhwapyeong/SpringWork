<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		viewForm.action=url;
		viewForm.submit();
	}
	
	let msg = '${message}';
	
	window.onload = function() {
		if(msg != ''){
			// 메시지 출력
			document.querySelector(".modal-body").innerHTML = msg;
			// 버튼 출력 제어
			document.querySelector("#btnModalSave").style.display='none';
			
			// 모달 생성
			const myModal = new bootstrap.Modal('#myModal', {
				  keyboard: false
				});
			
			// 모달 보여주기
			myModal.show();
		}
	}
</script>
</head>
<body>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">알림</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">확인</button>
        <button id="btnModalSave" type="button" class="btn btn-primary">저장</button>
      </div>
    </div>
  </div>
</div>

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
	<a class="btn btn-lg btn-primary" href="/board/list"
		role="button">리스트</a>
	</div>
</div>

<form method="get" name="viewForm">
	<input type="hidden" name="bno" value="${board.bno }">
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
	<div class="d-grid gap-2 d-md-flex justify-content-md-center">
		<button type="submit" class="btn btn-primary btn-lg" onclick="requestAction('/board/edit')">수정하기</button>
		<button type="button" class="btn btn-secondary btn-lg" onclick="requestAction('/board/delete')">삭제하기</button>
	</div>
</form>
</main>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</body>
</html>