package com.momo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.momo.mapper.MemberMapper;
import com.momo.vo.Member;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	MemberMapper memberMapper;
	@Autowired
	BCryptPasswordEncoder encoder;

	@Override
	public Member login(Member paramMember) {
		
		/// id만 가지고 mapper의 login으로 맴버를 조회 해온 후, 데이터베이스에 있는 pw와
		/// 사용자가 입력한 pw를 encoder.matches()로 비교하여 true가 반환되면 member를 리턴해준다.
		Member member = memberMapper.login(paramMember);
		if(member != null) {
			// 사용자가 입력한 비밀번호, 데이터베이스에 암호화되어 저장된 비밀번호
			boolean res = encoder.matches(paramMember.getPw(), member.getPw());
			
			// 비밀번호 인증이 성공하면 member객체를 반환
			if(res) {
				// 사용자 권한을 조회
				member.setRole(memberMapper.getMemberRole(member.getId()));
				return member;
			}
		}
		return null;
	}

	@Override
	public int insert(Member member) {
		int res = 0;
		
		// 비밀번호 암호화
		// BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		member.setPw(encoder.encode(member.getPw()));
		System.out.println(member.getPw());
		res = memberMapper.insert(member);
		
		return res;
	}

	@Override
	public int idCheck(Member member) {
		int res = memberMapper.idCheck(member);
		return res;
	}
}
