package com.momo.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.mapper.MemberMapper;
import com.momo.vo.Member;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MemberMapperTest {
	
	@Autowired
	MemberMapper memberMapper;
	
	@Test
	public void test1() {
		assertNotNull(memberMapper);
	}
	
	@Test
	public void test() {
		
		Member member = new Member();
		member.setId("admin");
		member.setPw("1234");
		member = memberMapper.login(member);
		log.info(member);
		assertNotNull(member);
	}
	
	@Test
	public void deleteTest() {
		String id = "user2";
		int res = memberMapper.delete(id);
		
		log.info("res ==================" + res);
		assertEquals(1, res);
	}
}
