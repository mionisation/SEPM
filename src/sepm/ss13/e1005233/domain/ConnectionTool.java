package sepm.ss13.e1005233.domain;

import java.sql.*;

public class ConnectionTool {
	private Connection c;
	public ConnectionTool() {
		c = null;
	}
	
	public void openConnection() throws SQLException {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		} catch (Exception e){
			System.err.println("ERROR:_FAILED_TO_LOAD_HSQLDB_DRIVER");
			e.printStackTrace();
			return;
		}
		c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb","SA","");
	}
	
	public void closeConnection() throws SQLException {
		c.close();
	}
	
	public Connection getConnection() {
		return c;
	}
	
	
	
	
}