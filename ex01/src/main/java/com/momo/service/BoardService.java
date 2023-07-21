package com.momo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.momo.vo.BnoArr;
import com.momo.vo.BoardVO;
import com.momo.vo.Criteria;

@Service
public interface BoardService {

	public List<BoardVO> getListXml(Criteria cri, Model model);
	
	public int insert(BoardVO board);
	
	public int insertSelectKey(BoardVO board, List<MultipartFile> files) throws Exception;
	
	public BoardVO getOne(String bno);
	
//	public int delete(String bno);
	public int delete(BnoArr bnoArry);
	
	public int deleteOne(int bno);
	
//	public int update(BoardVO board);
	public int update(BoardVO board, List<MultipartFile> files) throws Exception;
	
	public int getTotalCnt(Criteria cri);
	
	public int updateReplyCnt(int bno, int amount);
}
