package com.momo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.momo.vo.Member;

/**
 * JSON데이터를 반환하는 방법
 *  
 * 1. 라이브러리 추가
 * jackson-databind 라이브러리를 메이븐 리파지토리에서 검색 후 pom.xml 파일에 추가합니다. 
 * 
 * 2. 리턴타입에 어노테이션 추가
 * 메서드 선언부의 리턴타입에 ResponseBody 어노테이션을 추가 합니다.
 * 리턴타입에 맞게 데이터를 자동으로 변환 해줍니다!!
 * 
 * -> 개발자 도구의 네트워크 탭에서 응답헤더를 확인 해보세요
 *    Content-Type이 변경된걸 확인 할 수 있습니다.
 *    text/html  ->   application/json
 *    (https://developer.mozilla.org/ko/docs/Web/HTTP/Basics_of_HTTP/MIME_types)
 * 
 * 3. 메서드의 리턴타입
 *  ▶  VO, DTO
 *  	JSON타입의 데이터를 만들어서 반환 하는 용도로 사용
 *
 */

@Controller
public class RestController {
	
	@GetMapping("rest")
	public @ResponseBody Member rest(Member member) {
		
		return member;
	}
	
	/**
	 * ResponseEntity
	 * 	헤더 정보를 가공하기 위한 용도로 사용
	 * 
	 * 	Request, Response 객체를 직접 다루지 않고
	 * 	스프링 MVC에서 제공해주는 어노테이션 또는 객체를 이용합니다.
	 */
	@GetMapping("restResponseEntity")
	public ResponseEntity<String> rest1(){
		HttpHeaders header = new HttpHeaders();
		header.add("content-type", "application/json;charset=utf-8");
		
		String msg = "{\"name\":\"모모\"}";
		ResponseEntity<String> rs = 
				new ResponseEntity<String>(msg, header, HttpStatus.OK);
		
		return rs;
	}
}
