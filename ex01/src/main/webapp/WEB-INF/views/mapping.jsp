<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
반갑습니다~!😊😊<br>

param.name : ${param.name } <!-- model에 넣어주지 않으면 param으로 받아와야 함 -->
param.age : ${param.age }<br>
name : ${name }
age : ${age }<br>
=======================
<h3>vo 출력</h3>
${member.name }<br>
${member.age }<br>
${member.dueDate}<br>
${message }<br>
=======================
<h3>배열 출력</h3>
${list[0].name}<br>
${list[1]}<br>


<a href="/mapping/requestMapping">get방식</a><br>

<a href="/mapping/getMapping?name=화평&age=12">getMapping 호출</a><br>

<a href="/mapping/getMappingVO?name=화평&age=12&dueDate=2023/07/03">getMappingVO 호출</a><br>

<a href="/mapping/getMappingArr?ids=id1&ids=id2&ids=id3&ids=id4">getMappingArr 호출</a><br>

<a href="/mapping/getMappingList?ids=id1&ids=id2&ids=id3&ids=id4">getMappingList 호출</a><br>

<h3>객체 리스트를 파라메터로 전달 해봅시다</h3>
	파라메터 전달 방법 :
	list[0].name=momo&list[0].age=123&list[1].name=admin&list[1].age=120<br>

<script>
	function voList() {
		let url = "/mapping/getMappingMemberList"
				+ "?list[0].name=momo&list[0].age=123"
			 	+ "&list[1].name=admin&list[1].age=120"
		url = encodeURI(url);
		alert(url);		
		location.href=url;
	}
</script>

<a href="#" onclick="voList()">객체리스트 전달</a>


<form action="/mapping/requestMapping" method="post">
	<input type="submit" value="post방식">
</form>


</body>
</html>