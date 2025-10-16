package edu.pnu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PhoneBookApp {
	private static Scanner sc = new Scanner(System.in);
	private static String url = "jdbc:mysql://localhost:3306/myfirstdb";
	
	private static void insertPhonebook(Connection con) throws Exception {
		System.out.print("name: ");
		String name = sc.next();
		System.out.print("mobile: ");
		String mobile = sc.next();
		System.out.print("home: ");
		String home = sc.next();
		System.out.print("company: ");
		String company = sc.next();
		System.out.print("email: ");
		String email = sc.next();
		
		String sql = "insert into phonebook(name, mobile, home, company, email) values ";
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql + String.format("('%s','%s','%s','%s','%s')", name, mobile, home, company, email));
		System.out.print("작업 완료 \n");
		
		stmt.close();
	}
	
	private static void updatePhonebook(Connection con) throws Exception {
		System.out.print("name: ");
		String name = sc.next();
		System.out.print("mobile: ");
		String mobile = sc.next();
		System.out.print("home: ");
		String home = sc.next();
		System.out.print("company: ");
		String company = sc.next();
		System.out.print("email: ");
		String email = sc.next();
		System.out.print("id: ");
		int id = sc.nextInt();
		
		String sql= "update phonebook set ";
		Statement stmt= con.createStatement();
		stmt.executeUpdate(sql+ String.format("name='%s', mobile='%s', home='%s', company='%s', email='%s' where id=%d", name, mobile, home, company, email, id));
		System.out.print("추가 완료 \n");
		
		stmt.close();
	}
	
	private static void deletePhonebook(Connection con) throws Exception {
		System.out.print("id: ");
		int id = sc.nextInt();
		
		Statement stmt = con.createStatement();
		stmt.executeUpdate("delete from phonebook " + String.format("where id=%d", id));
		System.out.print("삭제 완료 \n");
		
		stmt.close();
	}
	
	private static void selectAllPhonebook(Connection con) throws Exception {
		System.out.print("id: ");
		int id = sc.nextInt();
		
		String sql= "select * from phonebook ";
		Statement stmt= con.createStatement();
		ResultSet rs =stmt.executeQuery(sql + String.format("where id > %d", id));
		
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
	}
	
	private static void nativeQuery(Connection con) throws SQLException {
		sc.nextLine(); // 버퍼에서 \n 제거용으로 한번 호출
		System.out.print("입력: ");
		String sql = sc.nextLine();
		String pre = sql.substring(0, 6).toLowerCase();
		Statement stmt = con.createStatement();
		if(!pre.startsWith("select")) {
			int cnt = stmt.executeUpdate(sql);
			if(pre.startsWith("insert")) {
				System.out.println(cnt + "건이 입력되었습니다.");
			}
			else if(pre.startsWith("update")) {
				System.out.println(cnt + "건이 수정되었습니다.");
			}
			else if(pre.startsWith("delete")) {
				System.out.println(cnt + "건이 삭제되었습니다.");
			}
			return;
		}
		ResultSet rs = stmt.executeQuery(sql);
		ResultSetMetaData md = rs.getMetaData();
		while(rs.next()) {
			for(int i = 1; i <= md.getColumnCount(); i++) {
				if(i != 1) {
					System.out.print(", ");					
				}	
				System.out.print(rs.getString(i));
			}	
			System.out.println();
		}	
		stmt.close();
		rs.close();
	}

	public static void main(String[] args) throws Exception {
		Connection con = DriverManager.getConnection(url, "musthave", "tiger");
		boolean flag = true;
		while (flag) {
			System.out.print("[I]nsert / [U]pdate / [D]elete / [S]elect / [N]ative / [Q]uit: ");
			char c = sc.next().toUpperCase().charAt(0);
			switch (c) {
			case 'I':
				insertPhonebook(con);
				break;
			case 'U':
				updatePhonebook(con);
				break;
			case 'D':
				deletePhonebook(con);
				break;
			case 'S':
				selectAllPhonebook(con);
				break;
			case 'N':
				nativeQuery(con);
				break;
			case 'Q':
				flag = false;
				break;
			}
		}
		System.out.println("Bye~");
	}
}
