package com.momo.interceptor;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.momo.vo.Member;

@Component
public class AdminInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		if(session.getAttribute("member") != null) {
			Member member = (Member)session.getAttribute("member");
			List<String> role = member.getRole();
			if(role.contains("ADMIN_ROLE")) {
				return true;
			}
		}

		// 로그인 페이지로 이동
		String msg = URLEncoder.encode("관리자만 사용 가능한 메뉴 입니다.", "utf-8");
		response.sendRedirect("/login?message="+msg);
		return false;
	}
}
