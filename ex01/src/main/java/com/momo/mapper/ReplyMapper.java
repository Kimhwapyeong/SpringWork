package com.momo.mapper;

import java.util.List;

import com.momo.vo.ReplyVO;

public interface ReplyMapper {
	
	public List<ReplyVO> getList(int bno);
	
	public int insert(ReplyVO replyVO);
	
	public int delete(int rno);
	
	public int edit(ReplyVO replyVO);
}
