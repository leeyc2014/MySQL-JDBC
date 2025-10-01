package edu.pnu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class QueryByStatement {

	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/world";
			con = DriverManager.getConnection(url, "musthave", "tiger");
			st = con.createStatement();
			rs = st.executeQuery("select id, name, countrycode, " + "district, population from city");
			
			while(rs.next()) {
				System.out.print(rs.getInt("id") + ", ");
				System.out.print(rs.getString("name") + ", ");
				System.out.print(rs.getString("countrycode") + ", ");
				System.out.print(rs.getString("district") + ", ");
				System.out.print(rs.getInt("population") + "\n");
			}
		} catch(Exception e) {
			System.out.println("연결 실패 : " + e.getMessage());
		} finally {
			try {
				if(rs != null)	rs.close();
				if(st != null)	st.close();
				if(con != null)	con.close();
			} catch(Exception e) {
				System.out.println("오류 : " + e.getMessage());
			}
		}	
	}
}
