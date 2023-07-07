package com.momo.book;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.service.BookService;
import com.momo.vo.BookVO;
import com.momo.vo.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BookServiceTest {

	@Autowired
	BookService bs;
	
	
	/// 테스트 메소드는 매개변수를 넣지 못해 model을 넣어주지 못해 테스트하지 못하나?
//	@Test
//	public void getList() {
//		List<BookVO> list = bs.getList(new Criteria());
//		
//		assertNotNull(list);
////		list.forEach(book -> {
////			log.info(book.getTitle());
////		});
//	}
//	
//	@Test
//	public void getOne() {
//		BookVO book = bs.getOne("370");
//		
//		log.info(book.getTitle());
//	}
	
}
