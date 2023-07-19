
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
		}
	}

	function getFileList(){
		// /file/list/{bno}
		/// let bno = 으로 변수를 선언하게 되면 호이스팅에 의해 변수를 미리 생성하고
		/// bno 에 undefined가 저장되어 id bno로 접근 할 수 없게 되어 value를 가져올 수 없다.
		/// 하지만 그냥 bno로 하게되면 호이스팅 되지 않아 저장 가능?
		bno = bno.value;
		fetch('/file/list/'+bno)
			.then(response => response.json())
			.then(map => viewFileList(map))
	}
	
	function viewFileList(map){
		console.log('viewFileListParamMap', map);
		let content = '';
		if(map.list.length>0){
			map.list.forEach(function(item, index){
				content += '🎃'+ item.filename + '<br>';
			})		
		} else {
			content = '등록된 파일이 없습니다.';
		}
		fileList.innerHTML = content;
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