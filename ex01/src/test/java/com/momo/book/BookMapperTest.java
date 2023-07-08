package com.momo.book;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.mapper.BookMapper;
import com.momo.vo.BookVO;
import com.momo.vo.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BookMapperTest {

	@Autowired
	BookMapper bm;
	
	@Test
	public void getList() {
		
		Criteria cri = new Criteria();
		cri.setSearchField("");
		cri.setSearchWord("");
		
		List<BookVO> list = bm.getList(cri);
		
		assertNotNull(list);
//		list.forEach(book -> {
//			log.info(book.getTitle());
//		});
	}
	
	@Test
	public void getOne() {
		BookVO book = bm.getOne("370");
		
		log.info(book.getTitle());
	}
}
