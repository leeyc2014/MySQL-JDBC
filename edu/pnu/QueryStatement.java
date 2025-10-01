package edu.pnu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class QueryStatement {
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/world";
			con = DriverManager.getConnection(url, "musthave", "tiger");
			
			//method01(con);
			//method02(con);
			//method02_1(con);
			//method02_2(con);
			//method03(con);
			//method04(con);
			//method05(con);
			//method06(con);
			method06_1(con);
			//method07(con);
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
		
	private static void method01(Connection con) throws Exception {
		System.out.print("인구수 : ");
		int pop = sc.nextInt();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select Name, Population from City where Population > " + pop);
		
		while(rs.next()) {
			System.out.print(rs.getString("Name") + ", ");
			System.out.print(rs.getInt("Population") + "\n");
		}
		rs.close();
		st.close();
	}
		
	private static void method02(Connection con) throws Exception {
		System.out.print("국가 코드 : ");
		String code = sc.next();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select Name, Population, CountryCode from City where CountryCode LIKE '" + code + "'");
		
		while(rs.next()) {
			System.out.print(rs.getString("Name") + ", ");
			System.out.print(rs.getInt("Population") + ", ");
			System.out.print(rs.getString("CountryCode") + "\n");
		}
		rs.close();
		st.close();
	}
	
	private static void method02_1(Connection con) throws Exception {
		System.out.print("국가(일부 가능) : ");
		String coun = sc.next();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select Country.Name, City.Name, City.Population from City inner join Country on Country.Code = City.CountryCode where Country.Name LIKE '%" + coun + "%'");
		
		while(rs.next()) {
			System.out.print(rs.getString("Country.Name") + ", ");
			System.out.print(rs.getString("City.Name") + ", ");
			System.out.print(rs.getInt("City.Population") + "\n");
		}
		rs.close();
		st.close();
	}
	
	private static void method02_2(Connection con) throws Exception {
		System.out.print("국가 코드 또는 국가 명 : ");
		String ct = sc.next();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select Country.Name, Country.Code, City.Name, City.Population from City inner join Country on Country.Code = City.CountryCode where Country.Name LIKE '%" + ct + "%'" + "or Country.Code LIKE '%" + ct + "%'");
		
		while(rs.next()) {
			System.out.print(rs.getString("Country.Name") + ", ");
			System.out.print(rs.getString("Country.Code") + ", ");
			System.out.print(rs.getString("City.Name") + ", ");
			System.out.print(rs.getInt("City.Population") + "\n");
		}
		rs.close();
		st.close();
	}
	
	private static void method03(Connection con) throws Exception {
		System.out.print("대륙 : ");
		String cont = sc.next();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select Name, Continent from Country where Continent LIKE '" + cont + "'");
		
		while(rs.next()) {
			System.out.print(rs.getString("Name") + ", ");
			System.out.print(rs.getString("Continent") + "\n");
		}
		rs.close();
		st.close();
	}
	
	private static void method04(Connection con) throws Exception {
		System.out.print("넓이 : ");
		double area = sc.nextDouble();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select Name, SurfaceArea from Country where SurfaceArea < " + area);
		
		while(rs.next()) {
			System.out.print(rs.getString("Name") + ", ");
			System.out.print(rs.getDouble("SurfaceArea") + "\n");
		}
		rs.close();
		st.close();
	}
	
	private static void method05(Connection con) throws Exception {
		System.out.print("한국의 District : ");
		String dis = sc.next();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select District, Name from City where District LIKE '" + dis + "'");
		
		while(rs.next()) {
			System.out.print(rs.getString("District") + ", ");
			System.out.print(rs.getString("Name") + "\n");
		}
		rs.close();
		st.close();
	}
	
	private static void method06(Connection con) throws Exception {
		System.out.print("언어 : ");
		String lan = sc.next();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select Language, CountryCode from CountryLanguage where IsOfficial LIKE 'T' && Language LIKE '" + lan + "'");
		while(rs.next()) {
			System.out.print(rs.getString("Language") + ", ");
			System.out.print(rs.getString("CountryCode") + "\n");
		}
		rs.close();
		st.close();
	}
	
	private static void method06_1(Connection con) throws Exception {
		System.out.print("언어 : ");
		String lan = sc.next();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select CountryLanguage.Language, Country.Name from CountryLanguage inner join Country on Country.Code = CountryLanguage.CountryCode where IsOfficial LIKE 'T' && Language LIKE '" + lan + "'");
		while(rs.next()) {
			System.out.print(rs.getString("CountryLanguage.Language") + ", ");
			System.out.print(rs.getString("Country.Name") + "\n");
		}
		rs.close();
		st.close();
	}
	
	private static void method07(Connection con) throws Exception {
		System.out.print("사용자 비율 : ");
		double per = sc.nextDouble();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select Language, CountryCode, Percentage from CountryLanguage where Percentage > " + per);
		while(rs.next()) {
			System.out.print(rs.getString("Language") + ", ");
			System.out.print(rs.getString("CountryCode") + ", ");
			System.out.print(rs.getDouble("Percentage") + "\n");
		}
		rs.close();
		st.close();
	}
}
