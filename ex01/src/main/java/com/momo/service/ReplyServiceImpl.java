package com.momo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momo.mapper.ReplyMapper;
import com.momo.vo.Criteria;
import com.momo.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	ReplyMapper replyMapper;
	
	@Override
	public List<ReplyVO> getList(int bno, Criteria cri) {
		
		List<ReplyVO> list = replyMapper.getList(bno, cri);
		
		return list;
	}

	@Override
	public int insert(ReplyVO replyVo) {
		int res = replyMapper.insert(replyVo);
		
		return res;
	}

	@Override
	public int delete(int rno) {
		int res = replyMapper.delete(rno);
		
		return res;
	}

	@Override
	public int edit(ReplyVO replyVo) {
		int res = replyMapper.edit(replyVo);
		
		return res;
	}

	@Override
	public int totalCnt(int bno) {
		
		return replyMapper.totalCnt(bno);
	}

}
