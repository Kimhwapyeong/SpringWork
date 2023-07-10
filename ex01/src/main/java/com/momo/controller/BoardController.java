package com.momo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.momo.service.BoardService;
import com.momo.vo.BnoArr;
import com.momo.vo.BoardVO;
import com.momo.vo.Criteria;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*")
@Log4j
public class BoardController {

	@Autowired
	BoardService boardService;

	@GetMapping("/reply/test")
	public String test() {
		
		return "/reply/test";
	}
	
	/**
	 * /board/msg WEB-INF/views/board/msg.jsp
	 */
	@GetMapping("msg")
	public void msg() {

	}

	@GetMapping("message")
	public void message(Model model) {

	}

//	@GetMapping("list_bs")
//	public void list_bs(Model model) {
//		List<BoardVO> list = boardService.getListXml();
//		log.info("========================");
//		log.info(list);
//		model.addAttribute("list", list);
//	}

	/*
	 * 파라메터의 자동 수집
	 * 	기본 생성자를 이용해서 객체를 생성
	 * 	-> setter 메서드를 이용해서 세팅
	 * 
	 */
	@GetMapping("list")
	public void getList(Model model, Criteria cri) {
//		List<BoardVO> list = boardService.getListXml(cri, model);
		log.info("cri : " + cri);
		boardService.getListXml(cri, model);
		//log.info("========================");
		//log.info(list);
		//model.addAttribute("list", list);

	}

	@GetMapping("view")
	public void getOne(Model model, BoardVO paramVO) {
		log.info("===================== bno" + paramVO);
		model.addAttribute("board", boardService.getOne(paramVO.getBno()));
	}

	@GetMapping("write")
	public void write(Model model) {

	}

	/**
	 * RedirectAttributes
	 * 
	 * 리다이렉트 URL의 화면까지 데이터를 전달
	 * 
	 * Model과 같이 매개변수로 받아 사용 
	 * addAttribute : 쿼리스트링에 파라메터로 전송 
	 * addFlashAttribute : 세션에 저장 후 페이지 전환
	 * 
	 */
	@PostMapping("write") /// redirect를 할 때에도 파라메터를 넘기기 위한 매개변수
	public String writeAction(BoardVO board, Model model, RedirectAttributes rttr) {
		// 시퀀스 조회 후 시퀀스 번호를 bno에 저장
		int res = boardService.insertSelectKey(board); /// 레퍼런스 값이라 bno가 살아서 온다?

		if (res > 0) {
			// url?message=등록.. (쿼리스트링으로 전달 -> param.message)
			// rttr.addAttribute("message", "등록되었습니다.");

			// 세션영역에 저장 -> message
			// 새로고침시 유지되지 않음
			rttr.addFlashAttribute("message", board.getBno() + "번 등록되었습니다.");
			/// 페이지를 redirect 해주고 싶을 때 이렇게 하면 된다.
			/// 그렇지 않으면 당연히 데이터를 받아오지 못하고 list 페이지를 출력하게 됨.
			return "redirect:/board/list";

		} else {
			model.addAttribute("message", "등록 중 예외 발생");
			return "/board/message";
		}

	}

	
	@PostMapping("edit") 
	public String editPost(BoardVO board, Model model, RedirectAttributes rttr, Criteria cri) { 
	int res = boardService.update(board);
  
	if(res > 0) {
		model.addAttribute("board", board);
		model.addAttribute("message", board.getBno() + "번 게시물 수정 완료");
		model.addAttribute("cri", cri);
		/// 왜 bno를 넘겨주지 않아도 되지? // board를 model에 담아가니까.
		return "/board/view";
		// return "/board/view?bno=" + board.getBno(); /// 안됨
  
	} else { 
		model.addAttribute("message", "수정 중 예외 발생");
	  
	  	return "/board/message"; }
	  
	}
	

	@GetMapping("edit")
	public String edit(BoardVO paramVO, Model model) {
		model.addAttribute("board", boardService.getOne(paramVO.getBno()));

		return "/board/write";
	}

	@GetMapping("delete")
	public String delete(BoardVO paramVO, Model model, RedirectAttributes rttr) {
		//System.out.println("getBno() : " + paramVO.getBno());
		String[] bnoArr = paramVO.getBno().split(",");
		BnoArr bnoArry = new BnoArr();
		bnoArry.setBnoArr(bnoArr);
		int res = boardService.delete(bnoArry);
		if(res > 0) {
			rttr.addFlashAttribute("message", paramVO.getBno() + "번 게시물 삭제 완료");
			return "redirect:/board/list";
		} else {
			model.addAttribute("message", "삭제 중 오류 발생");
			return "/board/message";
		}
		
	}



	
}
