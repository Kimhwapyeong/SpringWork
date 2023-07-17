package com.momo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.momo.service.ReplyService;
import com.momo.vo.Criteria;
import com.momo.vo.ReplyVO;
import com.momo.vo.pageDto;

import lombok.extern.log4j.Log4j;

/*
 *  RestController
 *  	Controller가 REST 방식을 처리하기 위한 것임을 명시합니다.
 */
@RestController
@Log4j
public class ReplyController extends CommonRestController{

	@Autowired
	ReplyService service;
	
	@GetMapping("/test")
	public String Test() {
		return "test";
	}
	
	/**
	 * PathVariable
	 * 		URL 경로에 있는 값을 파라메터로 추출하려고 할 때 사용
	 * 
	 * 		URL 경로의 일부를 변수로 사용
	 * 
	 * @return
	 */
	@GetMapping("/reply/list/{bno}/{page}")
	public Map<String, Object> getList(@PathVariable("bno") int bno
									,@PathVariable("page") int page){
		log.info("bno : " + bno);
		Criteria cri = new Criteria();
		cri.setAmount(5);
		cri.setPageNo(page);
		
		// 페이지 처리(시작번호 ~ 끝번호)
		int totalCnt = service.totalCnt(bno);
		List<ReplyVO> list = service.getList(bno, cri);
		
		// 페이지 블럭 생성
		pageDto pageDto = new pageDto(cri, totalCnt);
		
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		map.put("list", list);
//		map.put("pageDto", pageDto);
		
		return responseSelectMap(list, pageDto);
	}
	
	/**
	 * RequestBody
	 * 		JSON 데이터를 원하는 타입으로 바인딩 처리
	 * 		파라메터 자동 수집
	 * 
	 * @param vo
	 * @return
	 */
	@PostMapping("/reply/insert")
	public Map<String, Object> insert(@RequestBody ReplyVO vo){
		
		log.info("===================================");
		log.info("replyVO : " + vo);
		
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			int res = service.insert(vo);
			
			return map = responseWriteMap(res);
			
		} catch(Exception e) {
			map.put("result", "fail");
			map.put("message", e.getMessage());
		}
			
//		int res = service.insert(vo);
//		if(res > 0) {
//			return map = responseWriteMap(res);
//		} else {
//			map.put("result", "fail");
//		}
		
		return map;
	}
	
	@GetMapping("/reply/delete/{rno}")
	public Map<String, Object> delete(@PathVariable("rno") int rno){

		return responseDeleteMap(service.delete(rno));
	}
	
	@PostMapping("/reply/edit")
	public Map<String, Object> edit(@RequestBody ReplyVO vo/*, @PathVariable("rno") int rno*/){

		return responseEditMap(service.edit(vo));
	}
	

	
}
