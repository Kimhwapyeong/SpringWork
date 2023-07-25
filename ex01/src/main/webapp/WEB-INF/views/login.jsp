<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
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
		// 로그인
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
	    					/// focus가 벗어났을 때?
	    signUpId.addEventListener('blur', function(){
	    	if(!signUpId.value){
	    		/// 그냥 innerHTML 빼고 return만 해줘도 될 것 같음.
	    		signupMsg.innerHTML = '아이디를 입력해주세요.';
	    		return;
	    	}
	    	// 아이디 체크
	    	let obj = { id : signUpId.value };
	    	console.log("아이디 체크", obj);
	    	
	    	fetchPost('/idCheck', obj, (map)=>{
	    		if(map.result == 'success'){
	    			// 아이디 중복체크 성공
	    			idCheckRes.value = '1';
	    			signUpName.focus();
	    		} else {
	    			// 아이디 사용 불가능
	    			idCheckRes.value = '0';
	    			signUpId.focus();
	    			signUpId.value='';
	    		}
	    		signupMsg.innerHTML = map.message; // 메시지 출력
	    	});
	    	
	    })
	    
	    /// 비밀번호 체크
	    pwCheck.addEventListener('blur', function(){
	    	if(!signUpPw.value){
	    		signupMsg.innerHTML = '비밀번호를 입력해주세요.';
	    		return;
	    	}
	    	/// 이 조건을 주지 않으면, 비밀번호가 둘 다 비어있어도  pwCheckRes의 value가 1이됨
	    	if(!pwCheck.value){
	    		return;
	    	}
	    	if(signUpPw.value == pwCheck.value){
	    		signupMsg.innerHTML = '비밀번호가 일치합니다.';
	    		pwCheckRes.value = '1';
	    	} else {
	    		pwCheckRes.value = '0';
	    		signupMsg.innerHTML = '<span style="color:#d45959">비밀번호가 일치하지 않습니다.</span>';
	    		/// pwCheck에 focus를 주면 값을 수정하지 않으면 벗어날 수 없음.
	    		signUpPw.focus();
	    	}
	    })
	    
	   
	    btnSignUp.addEventListener('click', function(e){
	    	e.preventDefault();
	    	
	    	let id = signUpId.value;
	    	let pw = signUpPw.value;
	    	let name = signUpName.value;
	    	
	    	if(!id){
	    		signupMsg.innerHTML = '<span style="color:#d45959">아이디를 입력해주세요.</span>';
	    		signUpId.focus();
	    		return;
	    	}
	    	if(!name){
	    		signupMsg.innerHTML = '<span style="color:#d45959">이름을 입력해주세요.</span>';
	    		signUpName.focus();
	    		return;
	    	}
	    	if(!pw){
	    		signupMsg.innerHTML = '<span style="color:#d45959">비밀번호를 입력해주세요.</span>';
	    		signUpPw.focus();
	    		return;
	    	}
	    	
	    	// 아이디 중복체크 확인
	    	if(idCheckRes.value == 0){
	    		signupMsg.innerHTML = '<span style="color:#d45959">아이디 중복을 체크해주세요.</span>';
	    		signUpId.focus();
	    		return;
	    	}
	    	
	    	// 비밀번호 일치 확인
	    	if(pwCheckRes.value == 0){
	    		signupMsg.innerHTML = '<span style="color:#d45959">비밀번호를 체크해주세요.</span>';
	    		signUpPw.focus();
	    		return;
	    	}
	    	
	    	obj = {
	    			id : id
	    			, pw : pw
	    			, name : name
	    	}
	    	console.log(obj);
	    	
	    	fetchPost('/register', obj, (map)=>{
	    		console.log(map);
	    		if(map.result == 'success'){
    		        signupForm.style.display='none';
    		        signinForm.style.display='';
    		        msg.innerHTML = '회원가입이 완료되었습니다.<br>로그인 해주세요.'
	    		} else {
	    			signupMsg.innerHTML = '<span style="color:#d45959">' + map.message + '</span>';
	    		}
	    	})
	    })
	})
	function loginCheck(map) {
		/// 로그인 성공시 session영역에 id와 member객체가 저장되고
		/// map에는 성공 여부와 메시지만 저장된다.
		console.log(map);
		// 로그인 성공 -> list로 이동
		if(map.result == 'success'){
			location.href = map.url;
		}else{
			// 실패 -> 메시지 처리
			msg.innerHTML = '<span style="color:#d45959">' + map.message + '</span>';		
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
	    <img class="mb-4" src="/resources/images/kim.png" alt="" width="100" height="100">
	    <h1 class="h3 mb-3 fw-normal">로그인</h1>
	   	<div id="msg">${ param.msg }</div> 
	
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
	    <button class="w-100 btn btn-lg btn-secondary" type="submit" id="btnLogin">로그인</button>
	    <p></p>
	    <p class="mt-5 mb-3 text-muted">&copy; 김화평 짱</p>
	  </form>
	
	<!-- 회원가입 폼 -->
  	<input type="hidden" value="0" id="idCheckRes">
  	<input type="hidden" value="0" id="pwCheckRes">
	  <form name='signupForm' style='display: none;'>
	  
	    <img class="mb-4" src="/resources/images/kim.png" alt="" width="100" height="100">
	    <h1 class="h3 mb-3 fw-normal">회원가입</h1>
	    <div id="signupMsg"></div>
	
	    <div class="form-floating">
	      <input type="text" class="form-control start" id="signUpId" placeholder='id'>
	      <label for="signUpId">id</label>
	    </div>
   	    <div class="form-floating">
	      <input type="text" class="form-control middle" id="signUpName" placeholder='name'>
	      <label for="sighUpName">name</label>
	    </div>
	    <div class="form-floating">
	      <input type="password" class="form-control middle" id="signUpPw" placeholder='Password'>
	      <label for="signUpPw">Password</label>
	    </div>
	    <div class="form-floating">
	      <input type="password" class="form-control end" id="pwCheck" placeholder="PasswordCheck">
	      <label for="pwCheck">PasswordCheck</label>
	    </div>
	
	    <button class="w-100 btn btn-lg btn-secondary" id="btnSignUp" type="submit" >회원가입</button>
	    <p></p>
	
	    <p class="mt-5 mb-3 text-muted">&copy; 김화평 킹왕짱</p>
	  </form>
	  <div class="d-flex justify-content-center"></div>
	  	<button type="button" class="btn btn-light" id='btnSignupView'>회원가입</button>
	  	<button type="button" class="btn btn-light" id='btnSigninView'>로그인</button>
      </div>
  	<%
    String clientId = "3e08G9h3p3CiR5l0D3Ho";//애플리케이션 클라이언트 아이디값";
    String redirectURI = URLEncoder.encode("http://localhost:8080/login/naver_callback", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    
    // 요청  URL -> 네이버 로그인 및 사용자 정보제공 동의 -> 콜백으로 코드를 제공
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += "&client_id=" + clientId;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&state=" + state;
    session.setAttribute("state", state);
 	%>
	<a href="<%=apiURL%>"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>
	</main>
</body>
</html>