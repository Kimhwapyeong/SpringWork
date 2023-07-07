package com.momo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momo.service.BookService;
import com.momo.vo.BookVO;
import com.momo.vo.Criteria;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/book/*")
@Log4j
public class BookController {

	@Autowired
	BookService bs;
	
	@GetMapping("list")
	public void getList(Criteria cri, Model model) {
		// pageNo type int -> '' 입력시 오류 발생
		
		//model.addAttribute("msg", "/book/list");
		bs.getList(cri, model);
		
		// return "/book/list"; (void로 하면 이 코드가 생략된 상태)
		// -> WEB-INF/views/book/list.jsp
	}
	
	@GetMapping("view")
	public void getOne(String no, Model model) {
		model.addAttribute("book", bs.getOne(no, model));
		
	}
}
