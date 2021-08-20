package com.spring.persistance;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.spring.byla.board.vo.BoardVO;




public class JDBCTests {
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConnection() {
		try (Connection conn = 
				DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","mytest","mytest");
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM myboard")){
			List<BoardVO> boardList = new ArrayList<BoardVO>();
			try {
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					BoardVO board = new BoardVO();
					board.setSeq(rs.getInt("seq"));
					board.setTitle(rs.getString("title"));
					board.setWriter(rs.getString("writer"));
					board.setContent(rs.getString("content"));
					board.setRegDate(rs.getDate("regdate"));
					board.setCnt(rs.getInt("cnt"));
					boardList.add(board);
				}
				for(BoardVO board : boardList) {
					System.out.println("===>  " + board.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
}