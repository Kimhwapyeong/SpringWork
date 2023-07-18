
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
	})

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
				content += '🎃'+ item.filename + '/' + item.savePath + 
							'<br>' + 's_savePath : ' + item.s_savePath + '<br>';
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
		res : ${ message }
	</form>
	
	<h2>파일 리스트 조회</h2>
	<button type="button" id="btnList">리스트 조회</button>
	<div id="fileList"></div>
</body>
</html>