
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://kit.fontawesome.com/362f754739.js" crossorigin="anonymous"></script>
<script type="text/javascript">
	window.addEventListener('load',()=>{
		btnList.addEventListener('click', ()=>{
			getFileList();
		})
		
		// 파일 업로드
		btnFileupload.addEventListener('click', function(){
			// 웹 개발에서 HTML 폼 데이터를
			// JavaScript로 쉽게 조작하고 전송하는 방법을 제공하는 API이다
							/// 가지고 있는 모든 input을 가져갈 수 있도록 하는 객체?
			let formData = new FormData(fileuploadForm);
			//formData.append('name', 'momo');
				
			console.log('formData : ', formData);
			// FormData값 확인
			for(var pair of formData.entries()){
				console.log(pair);
						/// 배열의 첫 번째가 이름 두 번째가 값
				console.log(pair[0] + ' : ' + pair[1])
				
				if(typeof(pair[1]) == 'object'){
					let fileName = pair[1].name;
					let fileSize = pair[1].size;
					
					// 파일 확장자, 크기 체크
					// 서버에 전송 가능한 형식인지 확인
					// 최대 전송가능한 용량을 초과하지 않는지
					
					/// 앞의 조건을 넣지 않으면 첫 번째를 비우고 파일 업로드를 하면 false가 리턴되어 이벤트가 끝나버림
					if(fileName != '' && !checkExtension(fileName, fileSize)){
						return false;
					};
					console.log('fileName', fileName);
					console.log('fileSize', fileSize);

				}
			}				
							
 			fetch('/file/fileuploadActionFetch'
					,{
						method : 'post'
						, body : formData
					})
				.then(response => response.json())
				.then(map => fileuploadRes(map))
		})
	})
	
	function checkExtension(fileName, fileSize){
		let maxSize = 1024 * 1024 * 2;  /// 파일업로드 크기 제한이 root-context.xml에 설정되어 있어서
										/// 그 크기보다 maxSize가 크면 스크립트에서 거르지 못하고, 에러가 발생된다.
		// .exe, .sh, .zip, .alz 끝나는 문자열
		// 정규 표현식 : 특정 규칙을 가진 문자열을 검색하거나 치환할 때 사용
		let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
		if(maxSize <= fileSize){
			alert('파일 사이즈 초과');
			/// false를 반환해 주어야 이벤트가 완전히 끝난다?
			return false;
		}
		
		// 문자열에 정규식 패턴을 만족하는 값이 있으면 true, 없으면 false를 리턴한다
		if(regex.test(fileName)){
			alert('해당 종류의 파일은 업로드 할 수 없습니다.');
			return false;
		}
		
		return true;
	}
	
	function fileuploadRes(map){
		if(map.result == 'success'){
			divFileuploadRes.innerHTML = map.message;
		} else {
			alert(map.message);
		}
	}

	function getFileList(){
		// /file/list/{bno}
		/// let bno = 으로 변수를 선언하게 되면 호이스팅(호이스팅 아니고 어떤 영역에 저장)에 의해 변수를 미리 생성하고
		/// bno 에 undefined가 저장되어 id bno로 접근 할 수 없게 되어 value를 가져올 수 없다.
		/// 하지만 그냥 bno로 하게되면 호이스팅 되지 않아 저장 가능? / 하지만 재 호출 시 다시 저장 되면 bno(bno값).value가 되어
											// 에러가 난다. 따라서 document로 하거나 변수 이름을 다르게 하거나.
		let bno = document.querySelector("#bno").value;
		//bno = bno.value;
		fetch('/file/list/'+bno)
			.then(response => response.json())
			.then(map => viewFileList(map))   /// 콜백 함수를 익명의 함수로 생성한다면 map => () =>{}로 하면 안됨
											///  (map) => {} 으로 바로 생성해주어야 함
	}
	
	function viewFileList(map){
		console.log('viewFileListParamMap', map);
		let content = '';
		if(map.list.length>0){
			map.list.forEach(function(item, index){
				/// url에 사용되는 기호들 때문에(url에서 사용될 수 없는 기호가 savePath에 있을 수 있어서) uri 인코더를 사용해야 함.
				let savePath = encodeURIComponent(item.savePath);
				content += '<a href="/file/download?fileName=' + savePath + '" style="text-decoration:none; color:black">' 
						+ '🎃'+ item.filename + '</a>'
						+ ' <i onclick="attachFileDelete(this)" class="fa-solid fa-square-xmark" data-bno="' + item.bno + '" data-uuid="' + item.uuid + '"></i>'	
						+ '<br>';
			})		
		} else {
			content = '등록된 파일이 없습니다.';
		}
		fileList.innerHTML = content;
	}
	
	function attachFileDelete(e){
/* 		if(e.dataset.uuid != ''){
			
			fetch('/file/delete/'+e.dataset.bno+'/'+e.dataset.uuid)
				.then(response => response.json())
				.then(map => fileRes(map))
		} */
		
		let bno = e.dataset.bno;
		let uuid = e.dataset.uuid;
		//값이 유효하지 않은 경우 메시지 처리
		// fetch 요청
		// el 표현식 -> \${ } (el 표현식으로 처리하지 않음) /// el태그는 주석처리 안됨?
		fetch(`/file/delete/\${bno}/\${uuid}`)  /// jsp에서 백틱을 사용할 때에는 $ 앞에 \ 를 추가해주어야 한다. el과 충돌
			.then(response => response.json())
			.then(map => fileDeleteRes(map));
	}
	
	// 삭제 결과 처리
	function fileDeleteRes(map){
		console.log(map);
		if(map.result == 'success'){
			getFileList();
		} else {
			alert(map.message);
		}
	}
</script>
</head>
<body>
	<h2>파일 업로드</h2>
	<form method="post" enctype="multipart/form-data" 
						action="/file/fileuploadAction" name="fileuploadForm">
		
		<h2>파일 선택</h2>	
		bno : <input type="text" name="bno" id="bno" value="8"><br>
		<input type="file" name="files"><br>
		<input type="file" name="files"><br>
		<input type="file" name="files"><br>
		
		<button type="submit">파일업로드</button><br>
		<button type="button" id="btnFileupload">Fetch파일업로드</button><br>
		res : ${ message }
	</form>
	<div id="divFileuploadRes"></div>
	
	<h2>파일 리스트 조회</h2>
	<button type="button" id="btnList">리스트 조회</button>
	<div id="fileList"></div>
</body>
</html>