package com.momo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
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

	@Autowired
	ApiExamMemberProfile apiExam;
	
	@Override
	public void naverLogin(HttpServletRequest request, Model model) {
		try {
			// callback처리 -> access_token 
			Map<String, String> callbackRes = callback(request);

			String access_token = callbackRes.get("access_token");
			// access_token -> 사용자 정보 조회
			Map<String, Object> responseBody = apiExam.getMemberProfile(access_token);
			
			Map<String, String> response = (Map<String, String>) responseBody.get("response");
			System.out.println("================ naverLogin");
			System.out.println(response.get("name"));
			System.out.println(response.get("id"));
			System.out.println(response.get("gender"));
			System.out.println("=============================");
			
			/// 세션에 저장해야 함.
			model.addAttribute("id", response.get("id"));
			model.addAttribute("name", response.get("name"));
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<String, String> callback(HttpServletRequest request) throws Exception{
	    
		String clientId = "3e08G9h3p3CiR5l0D3Ho";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "ql2fGGM2Bo";// 애플리케이션 클라이언트 시크릿값";
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		try {
			String redirectURI = URLEncoder.encode("http://localhost:8080/login/naver_callback", "UTF-8");
			String apiURL;
			apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
			apiURL += "client_id=" + clientId;
			apiURL += "&client_secret=" + clientSecret;
			apiURL += "&redirect_uri=" + redirectURI;
			apiURL += "&code=" + code;
			apiURL += "&state=" + state;
			String access_token = "";
			String refresh_token = "";
			System.out.println("apiURL=" + apiURL);
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.print("responseCode=" + responseCode);
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			if (responseCode == 200) {
				System.out.println("token요청" + res.toString());
		        // json 문자열을 Map으로 변환
		        Map<String, String> map = new HashMap<String, String>();
		        // jackson 라이브러리 사용
		        ObjectMapper objectMapper = new ObjectMapper();
		        map = objectMapper.readValue(res.toString(), Map.class);
		        return map;
			} else {
				throw new Exception("callback 반환코드 : " + responseCode);
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new Exception("callback 처리중 예외 사항이 발생하였습니다.");
		}
	}
}
