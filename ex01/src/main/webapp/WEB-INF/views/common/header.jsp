<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
   .tooltip-1 {
   display: inline-block;
   color: black;
   font-weight: bold;
   }
   
   .tooltip-text {
   display: none;
   position: absolute;
   max-width: 200px;
   border: 1px solid;
   border-radius: 5px;
   padding: 10px;
   font-size: 0.8em;
   color: white;
   background: gray;
 
   }
   
   .tooltip-1:hover .tooltip-text {
   display: block; 
</style>
<script src="https://kit.fontawesome.com/362f754739.js" crossorigin="anonymous"></script>
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
        <h1 class="modal-title fs-5" id="exampleModalLabel"><img src="/resources/images/kim.png" width="30" height="35">알림</h1>
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

	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<div class="container-fluid">
			<a class="navbar-brand" href="/board/list"><img src="/resources/images/kim.png" width="30" height="35"></a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarCollapse"
				aria-controls="navbarCollapse" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarCollapse">
				<ul class="navbar-nav me-auto mb-2 mb-md-0">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="#">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="/board/list">게시판</a></li>
					<c:if test="${ not empty member }" var="res">
						<li class="nav-item"><a class="nav-link" 
									href="/logout">로그아웃</a></li>			
					</c:if>
					<c:if test="${ not res }">
						<li class="nav-item"><a class="nav-link" 
									href="/login">로그인</a></li>					
					</c:if>
				</ul>
			<div class="d-flex justify-content-end ">
				<c:if test="${ res }">
					<li class="nav-item nav-link" style="color:white; padding-right:10px;">${ member.name }님 환영합니다.</li>				
				</c:if>
				<c:if test="${ not res }">
					<li class="nav-item nav-link" style="color:white; padding-right:10px;">비화원 사용자</li>			
				</c:if>
			</div>
				<form class="d-flex" role="search">
					<input class="form-control me-2" type="search" placeholder="Search"
						aria-label="Search">
					<button class="btn btn-outline-success" type="submit">Search</button>
				</form>
			</div>
		</div>
	</nav>
</body>
</html>