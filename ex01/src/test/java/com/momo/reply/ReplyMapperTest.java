package com.momo.reply;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.board.BoardSeriveTest;
import com.momo.mapper.ReplyMapper;
import com.momo.vo.Criteria;
import com.momo.vo.ReplyVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTest {
	
	@Autowired
	ReplyMapper mapper;
	
	@Test
	public void test() {
		assertNotNull(mapper);
		
		Criteria cri = new Criteria();
		List<ReplyVO> list = mapper.getList(8, cri);
		log.info("===========================");
		log.info("list : " + list);
	}
	
	@Test
	public void insert() {
//		ReplyVO replyVo = new ReplyVO(0, 8, "너무 재미 없어요", "user", "", "");
//		
//		int res = mapper.insert(replyVo);
//		assertEquals(res, 1);
	}
	
	@Test
	public void delete() {
		int res = mapper.delete(19);
		assertEquals(1, res);
	}
	
	@Test
	public void edit() {
		ReplyVO replyVo = new ReplyVO();
		replyVo.setReply("김화평킹왕짱");
		replyVo.setRno(15);
		int res = mapper.edit(replyVo);
		assertEquals(1, res);
	}
	
	@Test
	public void totalCnt() {
		int res = mapper.totalCnt(8);
		
		log.info("totalCnt : " + res);
	}
}
