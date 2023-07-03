package com.momo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.momo.vo.Member;
import com.momo.vo.MemberList;

/**
 * 스프링 MCV에서 제공하고 있는 어노테이션을 이용하여 Controller를 작성해봅시다
 * 
 * 톰캣 서버를 실행하면 web.xml파일의 설정을 읽어 서버를 시작합니다.
 * web.xml파일에 기술되어 있는 
 * servlet-context.xml 파일의 
 * component-scan에 등록된 패키지를 탐색하며
 * 클래스를 조사하고 객체 설정에 사용되는 어노테이션들을 가진 클래스를 객체로 생성하고 관리합니다.
 * 
 * MVC에서 사용되는 어노테이션을 학습해봅시다
 *
 *
 * Controller
 * 해당 클래스의 인스턴스를 스프링의 빈으로 등록하고 컨트롤러로 사용
 * 
 * 스프링 MVC Controller의 장점
 * 
 *	1. 파라메터를 자동 수집
 *	2. URL매핑을 메서드 단위로 처리
 *	3. 화면에 전달할 데이터는 Model에 담아 주기만 하면 됨
 *	4. 간단한 페이지 전환(forward, redirect)
 * 	5. 상속/인터페이스 방식 대신에 어노테이션만으로도 필요한 설정 가능
 */

@Controller
@RequestMapping("/mapping/*")
public class MappingController {

	/**
	 * RequestMapping
	 * 	클래스의 상단에 적용시 현재 클래스의 모든 메서드들의 기본 URL경로를 지정
	 * 	메서드의 상단에 적용시 메서드의 URL경로를 지정
	 * 
	 * 	get방식과 post방식을 모두 처리하고 싶을 경우, 배열로 받을 수 있습니다.
	 * 
	 *	/mapping/ URI를 GET메서드로 호출하면 해당 메서드가 실행됩니다.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET )
	public String requestMapping() {
		return "mapping";
	}
	
	/**
	 * /mapping/requestMapping URI를 GET메서드로 호출하면 해당 메서드가 실행됩니다.
	 * @return
	 */
	@RequestMapping(value = "/requestMapping"
			, method= {RequestMethod.GET, RequestMethod.POST})
	public String requestMapping2() {
		System.out.println("/requestMapping 호출");
		return "mapping";
	}
	
	/**
	 * 스프링 4.3이후에는 GetMapping, PostMapping등으로 간단히 표현 가능
	 * 어노테이션 사용이 불가능할 경우 스프링의 버전을 확인합니다.
	 * GetMappping
	 *  Get방식의 요청을 처리합니다
	 * 
	 * 파라메터의 자동 수집
	 * 	VO객체를 지정할 경우, 객체를 생성후 파라메터의 name속성과 일치하는 필드에 세팅 해줍니다.
	 * 	단, 타입이 불일치하는 경우 400 오류가 발생할 수 있습니다.
	 * 
	 * 	VO객체를 지정할 경우, 객체를 생성후 파라메터의 name속성과 일치하는 필드에 세팅해줍니다.
	 * 	단, 타입이 불일치하는 경우 400 오류가 발생할 수 있습니다.
	 */
	@GetMapping("/getMapping")
	public String getMapping(@RequestParam("name") String name
								, @RequestParam("age") int age
								, Model model) {
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		
		System.out.println("name : " + name);
		System.out.println("age : " + age);
		return "mapping";
	}
	
	/**
	 * 파라메터를 VO, DTO에 수집한 경우, 별도의 저장 없이 화면까지 전달됩니다.
	 * 
	 * 화면에 값을 전달하고 싶은 경우
	 * Model객체를 매개변수로 받아 addAttribute()메서드를 이용합니다.
	 * model.addAttribute("이름", 값)
	 */
	@GetMapping("getMappingVO")
	public String getMappingVo(Member member, Model model) {
		
		model.addAttribute("message", "파라메터 자동 수집!!!!!");
		return "mapping";
	}
	
	
	@GetMapping("getMappingArr")
	public String getMappingArr(@RequestParam("ids") String[] ids) {
		for(String id : ids) {
			System.out.println("id : " + id);
		}
		
		return "mapping";
	}

	@GetMapping("getMappingList")
	public String getMappingList(@RequestParam("ids") List<String> ids) {
		
		/**
		 * ForEach : 익명의 함수를 이용한 컬렉션의 반복처리
		 * collection.forEach(변수 -> 반복처리(변수))
		 */
		ids.forEach(id ->{
			System.out.println("ids : " + id);
		});
		
		return "mapping";
	}
	
	@GetMapping("getMappingMemberList")
	public String getMappingMemberList(MemberList list, Model model) {
		System.out.println(list);
		model.addAttribute("list", list.getList());
		System.out.println(list.getList());
		return "mapping";
	}
	
}















