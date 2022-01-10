package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// 생성자
	// 메소드 gs
	// 메소드 일반

	public void getConnection() {

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void close() {

		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	public List<GuestbookVo> getList() { // DB에서 데이터를 리스트 형태로 가져오기 <>:어떤형태로 가져올지

		List<GuestbookVo> guestbookList = new ArrayList<GuestbookVo>();
		
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select no, ";
			query += "		name, ";
			query += "		password, ";
			query += "		content, ";
			query += "		to_char(reg_date, 'yyyy-mm-dd hh:mi:ss') reg_date ";
			query += " from guestbook ";
			query += " order by reg_date desc ";

			// 쿼리
			pstmt = conn.prepareStatement(query);

			// 바인딩(x)

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) { // 첫번재 줄의 것들을 하나하나 담음
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");

				GuestbookVo guestbookVo = new GuestbookVo(no, name, password, content, regDate); // 위의 낱개로 있던 것들을 GuestbookVo에 넣어줌																									 
				guestbookList.add(guestbookVo); // 꼭대기에 만들어둔 guestbookList에 guestbookVo내용 담기

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return guestbookList;
	}

	public int insert(GuestbookVo vo) {
		
		int count = 0;
		
		getConnection();
		
		try {
			String query = "";
			query += " insert into guestbook ";
			query += " values(seq_guestbook_no.nextval, ?, ?, ?, sysdate) ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContent());

			count = pstmt.executeUpdate();
			
			System.out.println(count + "건 등록");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return count;
	}
	
	public int delete(GuestbookVo vo) {
		
		int count = 0;
		
		getConnection();
		
		try {
			String query = "";
			query += " delete from guestbook ";
			query += " where no = ? ";
			query += " and password = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건 삭제");
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();		
		
		return count;
	}
	
}
