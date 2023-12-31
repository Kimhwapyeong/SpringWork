console.log('reply ==============')
// get방식 요청
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

// post방식 요청
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

function getReplyList(page) {
	/// 댓글 수정, 삭제 후에도 페이지를 유지하기 위해 
	document.querySelector("#page").value = page;    /// 이 코드를 넣었다가 처음 view 호출 시
													/// page value에 'undefined' 문자열로 삽입되어
													/// 댓글 삭제 후 댓글 페이지를 로드하지 못하였다.
													/// view load 이벤트에서 getReplyList에 파라메터 1을 주어 해결
	/*
	 * falsey : false, 0, "", NaN, undefined, null
	 * falsey한 값 이외의 값이 들어 있으면 true를 반환
	 * 
	 * page에 입력된 값이 없으면 1을 세팅
	 */
	/// 하였으나 수정, 삭제 후 현재 페이지 유지를 위해 id=page에 매개변수 page를 넣고, 기본값을 1을 주었기 때문에
	/// 이 코드는 맨 처음 페이지를 출력할 때만 사용된다. 그 때도 page.value 값을 넣어주면 지워도 됨.
	if(!page){
		page = 1    /// 처음 view 페이지 호출 시 파라메터로 1을 넣어주기 때문에 사용되지 않음
	}
	
//	/// 처음 페이지를 열면 page가 undefined가 되어 댓글 삭제 후 페이지를 출력하지 못했다.
//	if(page == 'undefined'){			
//		page = 1
//	}
	
	let bno = document.querySelector("#bno").value;
	/*let page = document.querySelector("#page").value;*/
	
	console.log('bno : ', bno);
	
	console.log('/reply/list/' + bno + '/' + page);
	console.log(`/reply/list/${bno}/${page}`);
	
	// url : 요청경로
	// callback : 응답결과를 받아 실행시킬 함수
	fetchGet(`/reply/list/${bno}/${page}`, replyView);
}

function replyView(map) {
	
	let list = map.list;
	let pageDto = map.pageDto;
	
	console.log(list);
	console.log(pageDto);
	
	let replyBox = '';
	replyBox = ''
		+'<table class="table text-break text-center">             '
		+'  <thead>                         '
		+'    <tr>                          '
		+'      <th scope="col" class="col-1">#</th>      '
		+'      <th scope="col" class="col-6">댓글</th>    '
		+'      <th scope="col" class="col-2">작성자</th>   '
		+'      <th scope="col" class="col-4">날짜</th>    '
		+'    </tr>                         '
		+'  </thead>                        '
		+'  <tbody>                         ';
	
	/// 리스트의 첫 번째가 비어 있으면 출력 
	if(!list[0]){
		replyBox +='	<tr>'
			     + '		<td colspan="4" class="text-center">'
			     + '            등록된 댓글이 없습니다.'
			     + '		</td>'
			     + '	</tr>';
	} else {
		/// 리스트가 있으면 반복하여 출력
		list.forEach((reply, i) => {
			/// 세션영역에 등록된 userId는 jsp 밖에서 작성된 js에서 접근하지 못한다. (있을 수 있지만 나는 모른다)
			/// 그래서 view.jsp에 hidden 으로 userId를 value로 갖는 input을 만들어서 읽어왔다.
			/// 정말 오래 고민했는데, 나름 쉬운 해결책이었다
			let userId = document.querySelector("#userId").value;
			replyBox += ''
				+`    <tr id="tr${reply.rno}" data-reply="${reply.reply}">      `
				+`      <td scope="row">${reply.rn}</td> ` /// ReplyVO에 rn필드를 넣어줌.
				
				+`      <td class="text-start">${reply.reply} 
						<a href="#none" id="tip${reply.rno}">`
				
				if(userId == reply.replyer){
					replyBox +=
						`<i class="fa-regular fa-pen-to-square tooltip-1" onclick="replyEdit(${reply.rno})">
						<span class="tooltip-text">수정</span></i></a>
						<a href="#none">
						<i class="fa-solid fa-trash tooltip-1" onclick="replyDelete(${reply.rno})">
						<span class="tooltip-text">삭제</span></i></a></td>	`
				}
				replyBox +=
				`      <td>${reply.replyer}</td>   `
				
				+`      <td>${reply.updatedate}</td>`
				+'    </tr>                         ';
		});
	}
	/// tbody와 table 태그 닫아줌
	replyBox += ''
		+'  </tbody>                        '
		+'</table>                          ';
	
	/// replyDiv에 replyBox innerHTML하여 만들어준 문자열을 화면에 출력
	replyDiv.innerHTML = replyBox;
	
	// 페이지 블럭 생성
	let disabled = pageDto.prev ? '' : 'disabled';
	console.log(pageDto.cri.pageNo);
	let pageBlock =
		`<nav aria-label="...">
		  <ul class="pagination justify-content-center">`;
			// 처음으로
		  if(pageDto.prev){
			  pageBlock +=
			    `<li class="page-item">
			      <a class="page-link" href="#none" onclick="getReplyList(${1})"><<</a>
			    </li>`;
		  }
		  	// prev 버튼
		  if(list[0]){
			  pageBlock +=
				  `<li class="page-item">
				  <a class="page-link ${disabled}" href="#none" onclick="getReplyList(${pageDto.startNo-1})">Previous</a>
				  </li>`;
		  }
			
		// 페이지 버튼
		for(i=pageDto.startNo;i<=pageDto.endNo;i++){
			let active = pageDto.cri.pageNo ==  i ? 'active' : '';
			pageBlock +=
			`<li class="page-item" aria-current="page">
			<a class="page-link ${active}" href="#none" onclick="getReplyList(${i})">${i}</a>
			</li>`;
		}
			// next 버튼
			disabled = pageDto.next ? '' : 'disabled';
			if(list[0]){
				pageBlock +=
					`<li class="page-item">
					<a class="page-link ${disabled}" href="#none" onclick="getReplyList(${pageDto.endNo+1})">Next</a>
					</li>`;
				
			}
		    
		    // 마지막 페이지
		    if(pageDto.next){
		    	pageBlock +=
		    		`<li class="page-item">
		    		<a class="page-link" href="#none" onclick="getReplyList(${pageDto.realEnd})">>></a>
		    		</li>`;
		    }
		  pageBlock +=
		  `</ul>
		</nav>`;
	
	replyDiv.innerHTML += pageBlock;
}

// getReplyList() 메서드에 page 매개변수를 받아세팅해주면서 go()함수가 필요 없어짐
/*function go(page){
	document.querySelector("#page").value = page;
	getReplyList(page);
}*/

// 답글 등록하기
function replyWrite(){
	// bno, reply, replyer
	let bno = document.querySelector("#bno").value;
	let reply = document.querySelector("#reply").value;
	let replyer = document.querySelector("#replyer").value;
	/// 댓글 작성 후 댓글창 초기화
	document.querySelector("#reply").value="";
	let obj = {
			bno : bno
			, reply : reply
			, replyer : replyer };
	
	console.log(obj);
	
	// url : /reply/insert
	// url : 요청 경로
	// obj : JSON형식으로 전달할 데이터
	// callback : 콜백함수
	fetchPost('/reply/insert', obj, replyRes);
}

// 답글 등록, 수정, 삭제의 결과를 처리하는 함수
function replyRes(map){
	if(map.result == 'success'){
		// 성공 : 리스트 조회 및 출력
		/// 댓글 수정, 삭제 후 현재 페이지 유지
		let page = document.querySelector("#page").value
		getReplyList(page);  ///
	} else {
		// 실패 : 메시지 출력
		alert(map.message);
	}
	console.log(map);
}

// 답글 삭제
function replyDelete(rno){
	fetchGet(`/reply/delete/${rno}`, replyRes);
}

// 답글 수정
function replyEdit(rno){
	let editBox = document.querySelector(`#tr${rno}`);
	let bno = document.querySelector(`#bno`).value;
	let replyText = editBox.dataset.reply;
	console.log('bno', bno);
	
	editBox.innerHTML = ``
	+`<td colspan="4">`
	+	`<div class="input-group">`
	+  		`<span class="input-group-text">답글수정</span>`
	+  		`<input type="text" aria-label="First name" class="form-control" value="${replyText}" id="editReplyText${rno}">`
	+  		`<input type="button" id="btnEdit" aria-label="Last name" value="수정하기" onclick="replyEditAction(${rno})" class="input-group-text">`
	+  		`<input type="button"aria-label="Last name" value="취소" onclick="getReplyList()" class="input-group-text">`
	+	`</div>`
	+`</td>`;
	
}

function replyEditAction(rno){
	let editReplyText = document.querySelector(`#editReplyText${rno}`).value;
	let obj = {
			rno : rno
			, reply : editReplyText
	};
	
	fetchPost('/reply/edit', obj, replyRes);
}










