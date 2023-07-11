<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<style type="text/css">
    * {
      margin: 0;
      box-sizing: border-box;
    }
    body {      
      display: flex;
      height: 100vh;
      /* 교차축 정렬 */
      align-items: center;
      /* 주축 정렬 */
      justify-content: center;
    }
</style>
</head>
<body>
<!-- 	<h1>로그인</h1>
	<form action="./loginAction" method="post">
		id <input type="text" name="id" value="admin"><br>
		pw <input type="text" name="pw" value="1234"><br>
		<input type="submit">
	</form> -->


	<form class="row g-3" action="./loginAction" method="post">
		<div class="col-auto">
			<label for="id" class="visually-hidden">id</label>
			<input type="text" class="form-control" id="id" name="id"
				placeholder="아이디" required>
		</div>
		<div class="col-auto">
			<label for="pw" class="visually-hidden">Password</label>
			<input type="password" class="form-control" id="pw" name="pw"
				placeholder="나만의시크릿넘버" required>
		</div>
		<div class="col-auto">
			<button type="submit" class="btn btn-primary mb-3">로그인</button>
		</div>
	</form>
</body>
</html>