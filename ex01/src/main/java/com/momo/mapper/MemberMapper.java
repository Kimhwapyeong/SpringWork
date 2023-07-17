package com.momo.mapper;

import java.util.List;

import com.momo.vo.Member;

public interface MemberMapper {

	public Member login(Member member);
	
	public int insert(Member member);
	
	public int idCheck(Member member);
	
	public List<String> getMemberRole(String id);
	
	public int delete(String id);
}
