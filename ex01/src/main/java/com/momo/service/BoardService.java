package com.momo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.momo.vo.BoardVO;

@Service
public interface BoardService {

	public List<BoardVO> getListXml();
	
	public int insert(BoardVO board);
	
	public int insertSelectKey(BoardVO board);
	
	public BoardVO getOne(int bno);
	
	public int delete(int bno);
	
	public int update(BoardVO board);
	
	public int getTotalCnt();
}
