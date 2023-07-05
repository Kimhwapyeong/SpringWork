<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
 <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.104.2">
    <title>Fixed top navbar example · Bootstrap v5.2</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/navbar-fixed/">

<!-- <link href="../assets/dist/css/bootstrap.min.css" rel="stylesheet"> -->

	<link href="/resources/css/style.css" rel="stylesheet">
    <style>
      body {
		  min-height: 75rem;
		  padding-top: 4.5rem;
		}
    </style>

    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <!-- Custom styles for this template -->
    <!-- <link href="navbar-top-fixed.css" rel="stylesheet"> -->
    <script type="text/javascript">
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

<jsp:include page="../common/header.jsp"></jsp:include>

<main class="container">
<div class="bg-light p-5 rounded">
	<div class="d-grid gap-2 d-md-flex justify-content-md-center">
		<p class="lead">부트스트랩을 이용한 게시판 만들기</p>
	</div>
	<div class="d-grid gap-2 d-md-flex justify-content-md-center">
		<h1>게시판</h1>
	</div>
	<div class="d-grid gap-2 d-md-flex justify-content-md-end">
	<a class="btn btn-lg btn-primary" href="/board/write"
		role="button">글쓰기</a>
	</div>
</div>
<p></p>
<div class="list-group w-auto">
	
	<c:forEach items="${list }" var="vo">
		<a href="/board/view?bno=${vo.bno }"
			class="list-group-item list-group-item-action d-flex gap-3 py-3"
			aria-current="true"> <img src="https://github.com/twbs.png"
			alt="twbs" width="32" height="32"
			class="rounded-circle flex-shrink-0">
			<div class="d-flex gap-2 w-100 justify-content-between">
				<div>
					<h6 class="mb-0">${vo.title }</h6>
					<p class="mb-0 opacity-75">${vo.writer }</p>
				</div>
				<small class="opacity-50 text-nowrap">${vo.regDate }</small>
			</div>
		</a>
	</c:forEach>
	
</div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>

</body>
</html>