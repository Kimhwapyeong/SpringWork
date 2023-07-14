package com.momo.member;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.service.MemberService;
import com.momo.vo.Member;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MemberServiceTest {

	@Autowired
	MemberService service;
	
	@Test
	public void insert() {
		Member member = new Member();
		member.setId("hoihoihoi");
		member.setPw("1234");
		member.setName("호이호이");
		
		int res = service.insert(member);
		
		assertEquals(1, res);
		
	}
	
	@Test
	public void idCheck() {
		Member member = new Member();
		member.setId("hoihoihoi");
		
		int res = service.idCheck(member);
		
		assertEquals(0, res);
	}
}
