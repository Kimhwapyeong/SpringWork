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
function deleteBoard() {
	// 체크박스가 선택된 요소의 value값을 ,로 연결
	delNoList = document.querySelectorAll("[name=delNo]:checked");
	
	let delNo = "";
	// 배열을 돌면서 저장된 변수 e의 value를 delNo 변수에 +
	delNoList.forEach((e)=>{
		delNo += e.value + ",";
	})
	// 마지막 , 삭제
	delNo = delNo.substr(0, delNo.length-1);
	console.log(delNo);
	// 삭제 요청 
	searchForm.action="../board/delete";
	searchForm.bno.value=delNo;
//	searchForm.bnoArr.value=delNoList;
	searchForm.submit();
	
}
</script>
</head>
<body>

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
	<a class="btn btn-lg btn-primary" onclick="deleteBoard()" href="#"
		role="button">삭제</a>
	</div>
</div>
<p></p>
<jsp:include page="../common/searchForm.jsp"/>
<div class="list-group w-auto">
	
	<c:forEach items="${list }" var="vo">
		<a href="/board/view?bno=${vo.bno }&pageNo=${pageDto.cri.pageNo}&searchField=${pageDto.cri.searchField}&searchWord=${pageDto.cri.searchWord}"
			class="list-group-item list-group-item-action d-flex gap-3 py-3"
			aria-current="true"><input type="checkbox" name="delNo" value="${vo.bno }"> <img src="/resources/images/kim.png"
			alt="twbs" width="32" height="40"
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
<jsp:include page="../common/pageNavi.jsp"></jsp:include>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>

</body>
</html>