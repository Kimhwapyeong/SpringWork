package com.momo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.momo.vo.BoardVO;
import com.momo.vo.Criteria;

@Service
public interface BoardService {

	public List<BoardVO> getListXml(Criteria cri, Model model);
	
	public int insert(BoardVO board);
	
	public int insertSelectKey(BoardVO board);
	
	public BoardVO getOne(String bno);
	
	public int delete(String bno);
	
	public int update(BoardVO board);
	
	public int getTotalCnt(Criteria cri);
}
