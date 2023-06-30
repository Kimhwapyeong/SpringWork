package com.momo.ex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class TestOjdbc {
	
	@Test
	public void calcTest() {
		Calc calc = new Calc();
		int res = calc.add(1, 2);
		
		assertEquals(4, res);
	}
	
	@Test
	public void ojdbctest() {
		try {
			// 접속정보
			// 							   @ip:port:sid
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String id = "jsp";
			String pw = "1234";
			
			Connection conn = null;
		
			// 커넥션 생성
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. 커넥션 생성
			conn = DriverManager.getConnection(url, id, pw);
			
			ResultSet rs = conn.createStatement()
					.executeQuery("select to_char(sysdate, 'yyyy/mm/dd')||'입니다' from dual");
			
			rs.next();
			System.out.println(rs.getString(1));
			System.out.println(conn);
			
			assertNotNull(conn);
			
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			System.err.println("라이브러리 로드중 오류가 발생하였습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
