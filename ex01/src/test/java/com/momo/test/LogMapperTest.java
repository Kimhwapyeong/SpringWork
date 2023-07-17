package com.momo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.mapper.LogMapper;
import com.momo.vo.LogVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class LogMapperTest {
	
	@Autowired
	LogMapper mapper;
	
	@Test
	public void insertTest() {
		LogVO vo = new LogVO();
		vo.setClassName("클래스네임");
		vo.setErrmsg("에러");
		vo.setMethodName("메소드");
		vo.setParams("파람");
		
		int res = mapper.insert(vo);
		
		assertEquals(1, res);
	}
}
