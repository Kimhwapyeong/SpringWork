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
import com.momo.vo.ReplyVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyServiceTest {
	
	@Autowired
	ReplyMapper service;
	
	@Test
	public void test() {
		assertNotNull(service);
		
		List<ReplyVO> list = service.getList(8);
		log.info("===========================");
		log.info("list : " + list);
	}
	
	@Test
	public void insert() {
//		ReplyVO replyVo = new ReplyVO(0, 8, "나는", "나다", "", "");
//		
//		int res = service.insert(replyVo);
//		assertEquals(1, res);
	}
	
	@Test
	public void delete() {
		int res = service.delete(20);
		assertEquals(1, res);
	}
}
