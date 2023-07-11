package com.momo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.momo.vo.Criteria;
import com.momo.vo.ReplyVO;

@Service
public interface ReplyService {

	public List<ReplyVO> getList(int bno, Criteria cri);
	
	public int insert(ReplyVO replyVo);
	
	public int delete(int rno);
	
	public int edit(ReplyVO replyVo);
	
	public int totalCnt(int bno);
}
