package com.momo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momo.service.BoardService;
import com.momo.vo.BoardVO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*")
@Log4j
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@GetMapping("list")
	public void getList(Model model) {
		List<BoardVO> list = boardService.getListXml();
		log.info("========================");
		log.info(list);
		model.addAttribute("list", list);
		
	}
	
	@GetMapping("view")
	public void getOne(int bno, Model model) {
		log.info("===================== bno" + bno);
		model.addAttribute("board", boardService.getOne(bno));
	}
	
	@GetMapping("write")
	public void write(Model model) {
		
	}
	
	@PostMapping("write")
	public String writeAction(BoardVO board, Model model) {
		boardService.insert(board);
		
		/// 페이지를 redirect 해주고 싶을 때 이렇게 하면 된다.
		/// 그렇지 않으면 당연히 데이터를 받아오지 못하고 list 페이지를 출력하게 됨.
		return "redirect:/board/list";
	}
	
	
}
