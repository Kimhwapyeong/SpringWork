<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>게시판 글쓰기</h2>
<form action="write" method="post">
	제목 : <input type="text" name="title"><br>
	내용 : <input type="text" name="content"><br>
	글쓴이 : <input type="text" name="writer"><br>
	<input type="submit" value="저장">
</form>
</body>
</html>