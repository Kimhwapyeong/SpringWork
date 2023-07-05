<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	// 메시지 처리
	/*
		부트스트랩을 이용한 모달창 띄우기
		
		1. css, js 파일 추가하기
		2. 모달요소 복사
			타이틀 및 메시지 수정
		3. 모달창 열기
			자바스크립트를 이용해서 모달객체 생성 후 show()메서드 호출
		4. 모달창 닫기(닫기버튼 클릭, 배경화면 클릭)
			자바스크립트를 이용해서 닫는 이벤트가 발생시 뒤로가기 실행
			-> 모달창이 닫히면서 발생하는 이벤트(hidden.bs.modal)에 뒤로가기 추가
	*/


	let msg = '${message}';
	//let msg = '등록 되었습니다. msg...';
/* 	if(msg != ''){
		alert(msg);
		history.go(-1);
	} */
	
	window.onload = function() {
		if(msg != ''){
			document.querySelector(".modal-body").innerHTML = msg;
			
			const myModal = new bootstrap.Modal('#myModal', {
				  keyboard: false
				});
			
			myModal.show();
			
			const myModalEl = document.getElementById('myModal')
			myModalEl.addEventListener('hidden.bs.modal', event => {
			  	history.go(-1);
			})
			
		}
	}
</script>
<!-- 부트스트랩을 사용하기 위해서 css, js를 추가합니다. -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
</head>
<body>

	<!-- Button trigger modal -->
<!-- 	<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
	  Launch demo modal
	</button> -->
	
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">알림</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        ...
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary">Save changes</button>
	      </div>
	    </div>
	  </div>
	</div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>


</body>
</html>