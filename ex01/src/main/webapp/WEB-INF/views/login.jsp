<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<link href="/resources/css/signin.css" rel="stylesheet">
<link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/sign-in/">
<style type="text/css">
/*     * {
      margin: 0;
      box-sizing: border-box;
    }
    body {     
   	  background-color : #eee; 
      display: flex;
      height: 100vh;
       교차축 정렬 
      align-items: center;
       주축 정렬 
      justify-content: center;
    } */
</style>
<!-- Custom styles for this template -->

<script type="text/javascript">
	window.addEventListener('load', function(){
		btnLogin.addEventListener("click", function(e){
			// 기본이벤트 제거
			e.preventDefault();
			// 파라메터 수집
			let obj={
				id : document.querySelector('#id').value
				, pw : document.querySelector('#pw').value
			}
			
			console.log(obj);
			
			// 요청
			fetchPost('./loginAction', obj, loginCheck)
		})
		
		/// 로그인 버튼 클릭 시 회원가입 폼 숨김
	    btnSigninView.addEventListener('click',function(){
	      signupForm.style.display='none';
	      signinForm.style.display='';
	    })
	    
		/// 회원가입 버튼 클릭 시 로그인 폼 숨김
	    btnSignupView.addEventListener('click',function(){
	      signupForm.style.display='';
	      signinForm.style.display='none';
	    })
	})
	function loginCheck(map) {
		console.log(map);
		// 로그인 성공 -> list로 이동
		if(map.result == 'success'){
			location.href='/board/list';
		}else{
			// 실패 -> 메시지 처리
			msg.innerHTML = map.message;		
		}
	}
</script>
<script type="text/javascript" src="/resources/js/common.js"></script>
</head>
<body class="text-center">
<!-- 	<form action="./loginAction" method="post">
		id <input type="text" name="id" id="id"><br>
		pw <input type="text" name="pw" id="pw"><br>
		<input type="submit" id="btnLogin">
	</form> -->

  
	<main class="form-signin w-100 m-auto">
	  <form name='signinForm'>
	    <img class="mb-4" src="/resources/images/kim.png" alt="" width="72" height="57">
	    <h1 class="h3 mb-3 fw-normal">로그인</h1>
	   	<div id="msg"></div> 
	
	    <div class="form-floating">
	      <input type="text" class="form-control start" id="id" placeholder='id'>
	      <label for="id">id</label>
	    </div>
	    <div class="form-floating">
	      <input type="password" class="form-control end" id="pw" placeholder="Password">
	      <label for="pw">Password</label>
	    </div>
	    <div class="checkbox mb-3">
		      <label style="position:relative; top:2px;">
		        <input type="checkbox" value="remember-me"> ID 저장
		      </label>
		      <button type="button" class="btn btn-light">ID찾기</button>
		      <button type="button" class="btn btn-light">PW찾기</button>
   	  	</div>
	    <button class="w-100 btn btn-lg btn-primary" type="submit" id="btnLogin">로그인</button>
	    <p></p>
	    <p class="mt-5 mb-3 text-muted">&copy; 2017–2022</p>
	  </form>
	
	  <form name='signupForm' style='display: none;'>
	    <img class="mb-4" src="/resources/images/kim.png" alt="" width="72" height="57">
	    <h1 class="h3 mb-3 fw-normal">로그인</h1>
	
	    <div class="form-floating">
	      <input type="text" class="form-control start" id="id" placeholder='id'>
	      <label for="id">id</label>
	    </div>
	    <div class="form-floating">
	      <input type="password" class="form-control middle" id="id" placeholder='Password'>
	      <label for="id">Password</label>
	    </div>
	    <div class="form-floating">
	      <input type="password" class="form-control end" id="pw" placeholder="Password">
	      <label for="pw">Password</label>
	    </div>
	
	    <button class="w-100 btn btn-lg btn-primary" type="submit" >회원가입</button>
	    <p></p>
	
	    <p class="mt-5 mb-3 text-muted">&copy; 김화평 컴퍼니</p>
	  </form>
	  <div class="d-flex justify-content-center"></div>
	  	<button type="button" class="btn btn-light" id='btnSignupView'>회원가입</button>
	  	<button type="button" class="btn btn-light" id='btnSigninView'>로그인</button>
      </div>
	</main>
</body>
</html>