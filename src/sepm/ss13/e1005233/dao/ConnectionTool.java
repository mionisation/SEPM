package sepm.ss13.e1005233.dao;

import java.sql.*;
/**
 * Hilfsklasse zur Verwaltung der Verbindung zur Datenbank
 * Es kann nur eine solche Verbindung existieren
 */
public class ConnectionTool {
	private static Connection c = null;
	
	/**
	 * Öffnet die Verbindung zur Datenbank
	 * @throws SQLException
	 */
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
	
	/**
	 * Schließt die Verbindung zur Datenbank
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
		if(c != null)
			c.close();
	}
	
	/**
	 * Gibt die Verbindung zur Datenbank zurück
	 * @return eine Referenz auf die Datenbank
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		if (c == null)
			openConnection();
		return c;
	}
	
	
	
	
}