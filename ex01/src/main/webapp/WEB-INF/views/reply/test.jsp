<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://kit.fontawesome.com/362f754739.js" crossorigin="anonymous"></script>
<script type="text/javascript">

	// 버튼이 생성되고 나서 이벤트를 부여하기 위해 onload 이벤트에 작성
	window.onload = () => {
	// 리스트 조회 및 출력
	getList();
	
		btnReplyWrite.addEventListener('click', function(){
			let bno = document.querySelector('#bno').value;
			let reply = document.querySelector('#reply').value;
			let replyer = document.querySelector('#replyer').value;
			/// 댓글 작성시 1페이지로 이동을 위한
			document.querySelector('#page').value = 1;
			
			console.log('bno', bno);
			console.log('reply', reply);
			console.log('replyer', replyer);
			
			// 1. 전송할 데이터를 javascript 객체로 생성
			let replyObj = {
				bno : bno
				, reply : reply
				, replyer : replyer
			}
/* 			// 2. 객체를 json 타입 문자열로 변환
			let replyJson = JSON.stringify(replyObj);
			
			console.log('replyObj', replyObj);
			console.log('replyJson', replyJson);
			
			// 3. 서버에 요청
			fetch('/reply/insert'
					, {method : 'post'
						, headers : {'Content-Type' : 'application/json'}
						, body : replyJson})
				// 4. 응답
				.then(response => response.json())
				.then(map => replyRes(map)); */
			
			fetchPost('/reply/insert', replyObj, replyRes);
		})
		
	}
	
	
	// 1. 서버에 댓글리스트 요청
	function getList(){
		let bno = document.querySelector("#bno").value;
		let page = document.querySelector("#page").value;
		
/* 		// url요청 결과를 받아옵니다
		fetch('/reply/list/' + bno + '/' + page)
		// response.json() : 요청 결과를 js object형식으로 변환(. 찍어서 접근하기 위해)
		.then(response => response.json())
		
		// 반환받은 오브젝트를 이용하여 화면에 출력합니다.
		.then(map => replyView(map)); */
		
		fetchGet('/reply/list/' + bno + '/' + page, replyView);
	}

	// 2. 리스트를 화면에 출력
	function replyView(map){
		
		let list = map.list;
		let pageDto = map.pageDto;
		
		// 콘솔창에 리스트 출력	
		console.log(list);
		console.log(pageDto)
		
		// div 초기화
		replyDiv.innerHTML = '';
		
		// 댓글 리스트로부터 댓글을 하나씩 읽어와서 div에 출력
		/// index를 넣어주는 이유는 id 때문, 변수명을 index로 하지 않아도 index값임
		list.forEach((reply, index) => {
			replyDiv.innerHTML 						/// 값을 저장하는 것?
			+= '<figure id="reply' + index + '" data-value="'+ reply.reply +'" data-rno="'+ reply.rno +'" data-replyer="'+ reply.replyer +'" data-replydate="'+ reply.replydate +'">'
			+  	'<blockquote class="blockquote">'
			+    	'<p>' + reply.reply 											/// index값을 넘겨 주어야 id를 선택해서 innerHTML을 해줄 수 있음  / reply.reply를 넘겨주지 않아도 됨(data-value를 사용하기 때문)
			+ 			'&nbsp&nbsp<i class="fa-regular fa-pen-to-square" onclick="replyEdit(' + index + ', ' + reply.rno + ')"></i>'
			+ 			'<i class="fa-regular fa-trash-can" onclick="replyDelete('+ reply.rno +')"></i></p>'
			+  	'</blockquote>'
			
			+  	'<figcaption class="blockquote-footer">'
			+    	reply.replyer + '<cite title="Source Title">&nbsp&nbsp' + reply.replydate + '</cite>'
			+  	'</figcaption>'
			+ '</figure>';
		})
		
		// 페이지 블럭 생성
		let pageBlock = '';
		let disabledStr = (pageDto.prev) ? '' : 'disabled';
		pageBlock
		+= '<nav aria-label="...">'
		+  '<ul class="pagination justify-content-center">'
		
		// prev 버튼
		+    '<li class="page-item '+ disabledStr +'">'
		+      '<a class="page-link" href="#none" onclick="go('+ ( pageDto.startNo -1 ) +')">Previous</a>'
		+    '</li>'
		
		// 반복해서 페이지 번호를 출력
		for(let i=pageDto.startNo; i<=pageDto.endNo; i++){
			let activeStr = (pageDto.cri.pageNo == i) ? 'active' : '';
			pageBlock
			+= '<li class="page-item"><a class="page-link '+ activeStr +'" href="#none" onclick="go('+i+')">' + i + '</a></li>'
		}
/* 		+    '<li class="page-item active" aria-current="page">'
		+      '<span class="page-link">2</span>'
		+    '</li>'
		+    '<li class="page-item"><a class="page-link" href="#">3</a></li>' */
		
		// next 버튼
		disabledStr = (pageDto.next) ? '' : 'disabled';
		pageBlock
		+=   '<li class="page-item '+ disabledStr +'">'
		+      '<a class="page-link" href="#none" onclick="go('+ (pageDto.endNo + 1) +')">Next</a>'
		+    '</li>'
		+  '</ul>'
		+'</nav>';
		
		replyDiv.innerHTML += pageBlock;

	}
	
	function go(page) {
		document.querySelector("#page").value = page;
		getList();
		//document.getElementById('nickname').scrollIntoView();
	}
	
	// 수정 화면 보여주기
/* 	function replyEdit(index, rno){
		document.querySelector("#reply" + index).innerHTML
			= '<input type="hidden" id="rno" name="rno" value="' + rno + '">'  // 필요 없는 코드
			+ '<div class="input-group mb-3">'
			+  '<input type="text" id="editReply" class="form-control" placeholder="내용을 입력하세요" aria-label="Recipient\'s username" aria-describedby="basic-addon2">'
			+  '<span class="input-group-text" id="btnReplyEdit" onclick="editAction('+ rno + ')")">수정완료</span>'
			+ '</div>';
		
	}		 */
	
	// 수정 화면 보여주기                            /// data-value로 받아와서 replyStr은 안 가져와도 된다.
	function replyEdit(index, rno/* , replyStr */){
		let editBox = document.querySelector("#reply" + index);
		let replyTxt = editBox.dataset.value;
		let replyer = editBox.dataset.replyer;
		let replydate = editBox.dataset.replydate;
		//let rnoInt = editBox.dataset.rno;
			editBox.innerHTML 
			= '<div class="input-group mb-3">'
			+  '<input type="text" id="editReply' + rno + '" value="' + replyTxt + '" class="form-control" placeholder="내용을 입력하세요" aria-label="Recipient\'s username" aria-describedby="basic-addon2">'
			+  '<span class="input-group-text" id="btnReplyEdit" onclick="editAction(' + rno + ')">수정하기</span>'
			+  '<span class="input-group-text" onclick="exit()">취소</span>'
//			+  '<span class="input-group-text" onclick="exit('+index+','+rno+',\''+replyTxt+'\',\''+replyer+'\',\''+replydate+'\')">취소</span>'
			+ '</div>';
	}
	
	function exit() {
		getList();
	}
	
/* 	function exit(index, rno, replyTxt, replyer, replydate) {
		replyDiv.innerHTML 						/// 값을 저장하는 것?
		= '<figure id="reply' + index + '" data-value="'+ replyTxt +'" data-rno="'+ rno +'">'
		+  	'<blockquote class="blockquote">'
		+    	'<p>' + replyTxt											/// index값을 넘겨 주어야 id를 선택해서 innerHTML을 해줄 수 있음  / reply.reply를 넘겨주지 않아도 됨(data-value를 사용하기 때문)
		+ 			'&nbsp&nbsp<i class="fa-regular fa-pen-to-square" onclick="replyEdit(' + index + ', ' + rno + ', \'' + replyTxt + '\')"></i>'
		+ 			'<i class="fa-regular fa-trash-can" onclick="replyDelete('+ rno +')"></i></p>'
		+  	'</blockquote>'
		
		+  	'<figcaption class="blockquote-footer">'
		+    	replyer + '<cite title="Source Title">&nbsp&nbsp' + replydate + '</cite>'
		+  	'</figcaption>'
		+ '</figure>';
	} */
	
	/// 댓글 수정
	function editAction(rno){
		// 1. 파라메터 수집
		let reply = document.querySelector("#editReply" + rno ).value;
		
		// 2. 전송할 데이터를 javascript 객체로 생성
		let replyObj = {
				rno : rno
				, reply : reply
			}
		
/* 		// 3. 객체를 json 타입으로 변환
		let replyJson = JSON.stringify(replyObj);
		
		console.log(replyJson);
		
		// 4. 서버에 요청
		fetch('/reply/edit'
				, {method : 'post'
					, headers : {'Content-Type' : 'application/json'}
					, body : replyJson})
			// 4. 응답
			.then(response => response.json())
			.then(map => replyRes(map)); */
			
		fetchPost('/reply/edit', replyObj, replyRes);
	}
	
/* 		btnReplyEdit.addEventListener('click', () => {
			let rno = document.querySelector('#rno').value;
			let reply = document.querySelector('#editReply').value;
			
			let replyObj = {
					rno : rno
					reply : reply
			} 
			
			let replyJson = JSON.stringify(replyObj);
			
			fetch('reply/edit'
					, { method : 'post'
						, headers : {'Content-Type' : 'application/json'}
						, body : replyJson})
				.then(response => response.json())
				.then(map => replyRes(map))
		})  */
	
	// 댓글 삭제
	function replyDelete(rno) {
		// url요청 결과를 받아옵니다
		fetch('/reply/delete/' + rno)
		// response.json() : 요청 결과를 js object형식으로 변환
		.then(response => response.json())
		// 반환받은 오브젝트를 이용하여 화면에 출력합니다.
		.then(map => replyRes(map));
	}
	
	// 결과 출력
	function replyRes(map){
		if(map.result == 'success'){
			// 등록 성공
			// 리스트 조회 및 출력
			getList();
		} else {
			// 등록 실패
			alert(map.message);
		}
	}
	
	function fetchGet(url, callback){
		console.log("url", url)
		try{
			// 요청
			fetch(url)
				// 요청결과 json 문자열을 javascript 객체로 반환
				.then(response => response.json())
				// 콜백함수 실행
				.then(map => callback(map))
		}catch(e){
			console.log(e);
		}
	}
	
	function fetchPost(url, obj, callback){
		try{
			fetch(url
					, {method : 'post'
						, headers : {'Content-Type' : 'application/json'}
						, body : JSON.stringify(obj)})
				// 4. 응답
				.then(response => response.json())
				.then(map => callback(map));
		}catch(e){
			console.log('fetchPost', e);
		}
	}
	
</script>
</head>
<body>
<h2>답글달기<img src="/resources/images/kim.png" width="25" height="40"></h2>
<!-- <input type="hidden" id="bno" name="bno" value="8"> -->
<input type="hidden" id="page" name="page" value="1">
<div class="input-group mb-3">
  <input type="text" id="replyer">	
  <input type="text" id="reply" class="form-control" placeholder="내용을 입력하세요" aria-label="Recipient's username" aria-describedby="basic-addon2">
  <span class="input-group-text" id="btnReplyWrite">댓글작성</span>
</div>

<div id="replyDiv">
	
</div>
</body>
</html>