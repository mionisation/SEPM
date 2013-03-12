package sepm.ss13.e1005233.service;

import java.sql.*;

import sepm.ss13.e1005233.domain.ConnectionTool;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ConnectionTool ct = new ConnectionTool();
		Connection c = null;

		try {
			ct.openConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		c = ct.getConnection();
		
		
		
		/*
		 * DEMO CODE
		 */
		
		try {
			Statement demo = c.createStatement();
			ResultSet result = demo.executeQuery("SELECT * FROM PFERDE");
			
			while(result.next()) {
			System.out.println(result.getString(1));
			System.out.println(result.getString(2));
			System.out.println(result.getString(3));
			System.out.println(result.getString(4));
			System.out.println(result.getString(5));
			System.out.println(result.getString(6));
			}
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
	}
	

}
