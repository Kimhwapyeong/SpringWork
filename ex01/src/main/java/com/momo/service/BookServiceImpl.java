package com.momo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.momo.mapper.BookMapper;
import com.momo.vo.BookVO;
import com.momo.vo.Criteria;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	BookMapper bm;
	
	@Override
	public List<BookVO> getList(Criteria cri, Model model) {
		List<BookVO> list = bm.getList(cri);
		model.addAttribute("list", list);
		return list;
	}

}
