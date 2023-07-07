package com.momo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.momo.mapper.BookMapper;
import com.momo.vo.BookVO;
import com.momo.vo.Criteria;
import com.momo.vo.pageDto;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	BookMapper bm;
	
	@Override
	public List<BookVO> getList(Criteria cri, Model model) {
		/*
		 * 1. 리스트 조회
		 * 2. 총건수 조회
		 * 3. 페이지 DTO 생성(페이지 블럭 생성)
		 */
		
		List<BookVO> list = bm.getList(cri);
		int totalCnt = bm.getTotalCnt(cri);
		model.addAttribute("list", list);
		model.addAttribute("pageDto", new pageDto(cri, totalCnt));
		return list;
	}

	@Override
	public int getTotalCnt(Criteria cri) {
		int totalCnt = bm.getTotalCnt(cri);
		
		return totalCnt;
	}

	@Override
	public BookVO getOne(String no, Model model) {
		BookVO book = bm.getOne(no);
		model.addAttribute("book", book);
		
		return book;

	}
	


	

}
