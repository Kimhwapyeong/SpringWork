package com.momo.ex;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class HikariDataSourceTest {

	@Autowired
	DataSource dataSource;
	
	@Test
	public void test() {
		
		try {
			Connection conn = dataSource.getConnection();
		
			ResultSet rs = conn.createStatement()
					.executeQuery("select sysdate from dual");
			
			rs.next();
			System.out.println(rs.getString(1));
			
			
			assertNotNull(conn);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
