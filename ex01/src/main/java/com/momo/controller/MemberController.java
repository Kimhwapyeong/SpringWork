package com.momo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.momo.service.MemberService;
import com.momo.vo.Member;

@Controller
public class MemberController extends CommonRestController{
	
	@Autowired
	MemberService service;
	
	/**
	 * 로그인 페이지 이동
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/loginAction")
			/// 반환을 json 형식으로 해주겠다는 뜻, 클래스에 @RestController  
			/// 어노테이션을 달아주면 모든 메소드의 반환타입에 자동으로 붙음.
	public @ResponseBody Map<String, Object> loginAction(
									/// 매개변수가 JSON 타입으로 오면, key값과 객체의
									/// 속성을 비교하여 자동으로 set하여 생성.
										@RequestBody Member member
										, Model model
										/// 세션에 저장해주기 위한 파라메터
										, HttpSession session) {
		System.out.println("id : " + member.getId());
		System.out.println("pw : " + member.getPw());
		
		member = service.login(member);
		if(member != null) {
			session.setAttribute("member", member);
			session.setAttribute("userId", member.getId());
			/// 맵을 생성하고, 파라메터로 넣어준 숫자로 성공, 실패를 확인하여
			/// 성공 여부와 메시지를 맵에 put 하여주는 메소드
			/// 반환은 당연히 map
			return responseMap(1, "로그인");
		} else {
			return responseMap(0, "로그인");
		}
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session, HttpServletRequest request) {
		session.invalidate();
		request.getSession(true);
		
		return "redirect:/board/list";
	}
	
}
