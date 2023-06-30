package com.momo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.momo.dao.MemberDao;
import com.momo.vo.Member;

@Service
public class MemberService {
	
	/// 주입?
	@Autowired
	MemberDao dao;
	
	public Member login(Member paramMember, Model model) {
		Member member = dao.login(paramMember);
		
		if(member != null) {
			model.addAttribute("message", member.getName() + "님 환영합니다.");
		} else {
			model.addAttribute("message", "아이디/비밀번호를 확인해주세요.");
		}
		
		return member;
	}
}
