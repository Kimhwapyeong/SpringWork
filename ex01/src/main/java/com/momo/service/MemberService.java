package com.momo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.momo.dao.MemberDao;
import com.momo.vo.Member;

@Service
public interface MemberService {
	
	public Member login(Member member);
	
	public int insert(Member member);
	
	public int idCheck(Member member);
	
}
