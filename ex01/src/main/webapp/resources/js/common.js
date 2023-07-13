console.log("common.js 파일 다운 완료")
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