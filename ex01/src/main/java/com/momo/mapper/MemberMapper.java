package com.momo.mapper;

import com.momo.vo.Member;

public interface MemberMapper {

	public Member login(Member member);
	
	public int insert(Member member);
	
	public int idCheck(Member member);
	
	public int delete(String id);
}
