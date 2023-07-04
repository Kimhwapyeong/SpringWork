package com.momo.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.mapper.BoardMapper;
import com.momo.vo.BoardVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class boardTest {

	@Autowired
	BoardMapper boardMapper;
	
	@Test
	public void getList() {
		assertNotNull(boardMapper);
		List<BoardVO> list = boardMapper.getList();
		
		/// 반복문(람다식)
		list.forEach(board -> {
			log.info("boardVo==================");
			log.info(board.getBno());
			log.info(board.getTitle());
			log.info(board.getContent());
		});
	}
	
	@Test
	public void getListXml() {
		List<BoardVO> list = boardMapper.getListXml();
		
		list.forEach(board -> {
			log.info("boardVo==================");
			log.info(board.getBno());
			log.info(board.getTitle());
			log.info(board.getContent());
		});
	}
	

	@Test
	public void insert() {
		
		BoardVO board = new BoardVO();
		
		board.setTitle("집");
		board.setContent("집에가고싶다");
		board.setWriter("김화평");
		int res = boardMapper.insert(board);
		
		assertEquals(res, 1);
	}
	
	@Test
	public void insertSelectKey() {
		BoardVO board = new BoardVO();
		board.setTitle("집");
		board.setContent("집에가고싶다");
		board.setWriter("김화평");		
		
		int res = boardMapper.insertSelectKey(board);
		log.info("bno : " + board.getBno());
		assertEquals(res, 1);
	}
	
	@Test
	public void getOne() {
		
		BoardVO board = boardMapper.getOne(6);
		
		log.info("title : " + board.getTitle());
		
		assertNotNull(board);
	}
	
	@Test
	public void delete() {
		int res = boardMapper.delete(10);
		
		assertEquals(res, 1);
	}
	
	@Test
	public void update() {
		BoardVO board = new BoardVO();
		board.setBno(3);
		board.setTitle("집으로");
		board.setContent("집에가자");
		board.setWriter("김두한");
		
		int res = boardMapper.update(board);
		
		assertEquals(res, 1);
	}
	
	@Test
	public void getTotalCnt() {
		int totalCnt = boardMapper.getTotalCnt();
		log.info("totalCnt : " + totalCnt);
	}
	
}
