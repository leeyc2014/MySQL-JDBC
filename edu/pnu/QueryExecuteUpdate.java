package edu.pnu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class QueryExecuteUpdate {
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/myfirstdb";
			con = DriverManager.getConnection(url, "musthave", "tiger");
			
			// insert(con);
			// update(con);
			// delete(con);
			 selectAll(con);

		} catch(Exception e) {
			System.out.println("연결 실패 : " + e.getMessage());
		} finally {
			try {
				if(con != null)	{
					con.close();
				}
			} catch(Exception e) {
				System.out.println("오류 : " + e.getMessage());
			}
		}
	}
	
	private static void insert(Connection con) throws Exception {
		String name = "홍길동";
		String mobile = "010-1111-2222";
		String home = "051-123-4567";
		String company = "051-123-1234";
		String email = "hongkd@korea.com";
		
		String sql = "insert into phonebook(name, mobile, home, company, email) values ";
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql + String.format("('%s','%s','%s','%s','%s')", name, mobile, home, company, email));
		System.out.print("작업 완료");
		
		stmt.close();
		con.close();
	}
	
	private static void update(Connection con) throws Exception {
		String name = "홍길동";
		String mobile = "010-1111-9999";
		String home = "051-123-4567";
		String company = "051-123-1234";
		String email = "hongkd@korea.com";
		int id = 4;
		
		String sql= "update phonebook set ";
		Statement stmt= con.createStatement();
		stmt.executeUpdate(sql+ String.format("name='%s', mobile='%s', home='%s', company='%s', email='%s' where id=%d", name, mobile, home, company, email, id));
		System.out.print("추가 완료");
		
		stmt.close();
		con.close();
	}
	
	private static void delete(Connection con) throws Exception {
		int id = 4;
		
		Statement stmt = con.createStatement();
		stmt.executeUpdate("delete from phonebook " + String.format("where id=%d", id));
		System.out.print("삭제 완료");
		
		stmt.close();
		con.close();
	}
	
	private static void selectAll(Connection con) throws Exception {		
		String sql= "select * from phonebook ";
		Statement stmt= con.createStatement();
		ResultSet rs =stmt.executeQuery(sql);
		
		while(rs.next()) {
			System.out.print(rs.getString("Name") + "\t");
			System.out.print(rs.getString("mobile") + "\t");
			System.out.print(rs.getString("home") + "\t");
			System.out.print(rs.getString("company") + "\t");
			System.out.print(rs.getString("email") + "\n");
			System.out.println("-".repeat(100));
		}	
		
		rs.close();
		stmt.close();
		con.close();
	}
}