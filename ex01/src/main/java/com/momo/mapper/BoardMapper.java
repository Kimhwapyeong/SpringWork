package com.momo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.momo.vo.BnoArr;
import com.momo.vo.BoardVO;
import com.momo.vo.Criteria;

public interface BoardMapper {
	
	@Select("select * from tbl_board")
	public List<BoardVO> getList();
	
	public List<BoardVO> getListXml(Criteria cri);
	
	public int insert(BoardVO board);
	
	public int insertSelectKey(BoardVO board);
	
	public BoardVO getOne(String bno);
	
//	public int delete(String bno);
	public int delete(BnoArr bnoArr);
	
	public int update(BoardVO board);
	
	public int getTotalCnt(Criteria cri);
}
