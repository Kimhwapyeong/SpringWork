package com.momo.ex;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.mapper.SampleMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class MybatisTest {

	@Autowired
	SampleMapper sampleMapper;
	
	@Test
	public void test() {
		System.out.println(sampleMapper);
		String time = sampleMapper.getTime();
		System.out.println("===============" + time);
		assertNotNull(time);
	}
	
	@Test
	public void test1() {
		System.out.println("XML연동 테스트");
		String time = sampleMapper.getTime2();
		System.out.println(time);
	}
}
