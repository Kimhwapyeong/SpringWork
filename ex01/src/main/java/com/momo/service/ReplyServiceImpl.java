package com.momo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.momo.mapper.BoardMapper;
import com.momo.mapper.ReplyMapper;
import com.momo.vo.Criteria;
import com.momo.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	ReplyMapper replyMapper;
	
	@Autowired
	BoardMapper boardMapper;
	
	@Override
	public List<ReplyVO> getList(int bno, Criteria cri) {
		
		List<ReplyVO> list = replyMapper.getList(bno, cri);
		
		return list;
	}
	
	/*
	 * Transactional
	 * 		서비스 로직에 대한 트랜잭션 처리를 지원
	 * 		오류 발생시 롤백
	 */
	@Transactional
	@Override
	public int insert(ReplyVO replyVo) {
		// 댓글 입력시 Board테이블의 댓글수(replyCnt)를 1증가 시켜줍니다.
		boardMapper.updateReplyCnt(replyVo.getBno(), 1);
		int res = replyMapper.insert(replyVo);
		return res;
	}

	@Transactional
	@Override
	public int delete(int rno) {
		ReplyVO replyVo = replyMapper.getOne(rno);
		boardMapper.updateReplyCnt(replyVo.getBno(), -1);
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
