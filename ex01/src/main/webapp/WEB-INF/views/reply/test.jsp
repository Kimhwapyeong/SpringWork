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

	
	// 1. 서버에 댓글리스트 요청
	function getList(){
		let bno = document.querySelector("#bno").value;
		// url요청 결과를 받아옵니다
		fetch('/reply/list/' + bno)
		// response.json() : 요청 결과를 js object형식으로 변환
		.then(response => response.json())
		
		// 반환받은 오브젝트를 이용하여 화면에 출력합니다.
		.then(list => replyView(list));
		///	.then(map => callback(map));  /// 보통 map으로 잘 받아옴
	}

	// 2. 리스트를 화면에 출력
	function replyView(list){
		// 콘솔창에 리스트 출력	
		console.log(list);
		
		// div 초기화
		replyDiv.innerHTML = '';
		
		// 댓글 리스트로부터 댓글을 하나씩 읽어와서 div에 출력
		/// index를 넣어주는 이유는 id 때문, 변수명을 index로 하지 않아도 index값임
		list.forEach((reply, index) => {
			replyDiv.innerHTML 
			+= '<figure id="reply' + index + '">'
			+  	'<blockquote class="blockquote">'
			+    	'<p>' + reply.reply 											/// index값을 넘겨 주어야 id를 선택해서 innerHTML을 해줄 수 있음
			+ 			'&nbsp&nbsp<i class="fa-regular fa-pen-to-square" onclick="replyEdit(' + index + ', ' + reply.rno + ')"></i>'
			+ 			'<i class="fa-regular fa-trash-can" onclick="replyDelete('+ reply.rno +')"></i></p>'
			+  	'</blockquote>'
			
			+  	'<figcaption class="blockquote-footer">'
			+    	reply.replyer + '<cite title="Source Title">&nbsp&nbsp' + reply.replydate + '</cite>'
			+  	'</figcaption>'
			+ '</figure>';
		})
	
	}
	
	function replyEdit(index, rno){
		document.querySelector("#reply" + index).innerHTML
			= '<input type="hidden" id="rno" name="rno" value="' + rno + '">'  // 필요 없는 코드
			+ '<div class="input-group mb-3">'
			+  '<input type="text" id="editReply" class="form-control" placeholder="내용을 입력하세요" aria-label="Recipient\'s username" aria-describedby="basic-addon2">'
			+  '<span class="input-group-text" id="btnReplyEdit" onclick="edit('+ rno + ')")">수정완료</span>'
			+ '</div>';
		
	}
	
	/// 댓글 수정
	function edit(rno){
		let reply = document.querySelector("#editReply").value;
		
		let replyObj = {
				rno : rno
				, reply : reply
			}
		
		let replyJson = JSON.stringify(replyObj);
		
		fetch('/reply/edit'
				, {method : 'post'
					, headers : {'Content-Type' : 'application/json'}
					, body : replyJson})
			// 4. 응답
			.then(response => response.json())
			.then(map => replyRes(map));
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
	
	
	function replyDelete(rno) {
		// url요청 결과를 받아옵니다
		fetch('/reply/delete/' + rno)
		// response.json() : 요청 결과를 js object형식으로 변환
		.then(response => response.json())
		// 반환받은 오브젝트를 이용하여 화면에 출력합니다.
		
		.then(map => replyRes(map));
	}

	// 버튼이 생성되고 나서 이벤트를 부여하기 위해 onload 이벤트에 작성
	window.onload = () => {
	// 리스트 조회 및 출력
	getList();
	
		btnReplyWrite.addEventListener('click', function(){
			let bno = document.querySelector('#bno').value;
			let reply = document.querySelector('#reply').value;
			let replyer = document.querySelector('#replyer').value;
			
			console.log('bno', bno);
			console.log('reply', reply);
			console.log('replyer', replyer);
			
			// 1. 전송할 데이터를 javascript 객체로 생성
			let replyObj = {
				bno : bno
				, reply : reply
				, replyer : replyer
			}
			// 2. 객체를 json 타입 문자열로 변환
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
				.then(map => replyRes(map));
		})
		
	}
	
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
	
</script>
</head>
<body>
<h2>답글달기</h2>
<input type="text" id="bno" name="bno" value="8">
<div class="input-group mb-3">
  <input type="text" id="replyer">	
  <input type="text" id="reply" class="form-control" placeholder="내용을 입력하세요" aria-label="Recipient's username" aria-describedby="basic-addon2">
  <span class="input-group-text" id="btnReplyWrite">댓글작성</span>
</div>

<div id="replyDiv">
	
</div>
</body>
</html>