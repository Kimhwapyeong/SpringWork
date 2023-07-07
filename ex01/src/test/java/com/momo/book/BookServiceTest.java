package com.momo.book;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;

import com.momo.service.BookService;
import com.momo.vo.BookVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BookServiceTest {

	@Autowired
	BookService bs;
	
	@Test
	public void getList(Model model) {
		List<BookVO> list = bs.getList(model);
		
		assertNotNull(list);
//		list.forEach(book -> {
//			log.info(book.getTitle());
//		});
	}
}
