package sepm.ss13.e1005233.dao;

import java.sql.*;

import org.apache.log4j.Logger;
/**
 * Hilfsklasse zur Verwaltung der Verbindung zur Datenbank
 * Es kann nur eine solche Verbindung existieren
 */
public class ConnectionTool {
	private static Connection c = null;
	private static final Logger log = Logger.getLogger(ConnectionTool.class);
	
	/**
	 * Öffnet die Verbindung zur Datenbank
	 * @throws SQLException
	 */
	public void openConnection() throws SQLException {
		log.info("Versuche Verbindung zu erstellen...");
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		} catch (Exception e){
			log.error("Konnte keine Verbindung aufbauen :(");
			System.err.println("ERROR:_FAILED_TO_LOAD_HSQLDB_DRIVER");
			e.printStackTrace();
			return;
		}
		c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb","SA","");
		log.debug("Verbindung erstellt!");
	}
	
	/**
	 * Schließt die Verbindung zur Datenbank
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
		if(c != null) {
			c.close();
			log.debug("Schliesse Datenbankverbindung...");
		}
	}
	
	/**
	 * Gibt die Verbindung zur Datenbank zurück
	 * @return eine Referenz auf die Datenbank
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		if (c == null) {
			openConnection();
			log.debug("Oeffne Datenbankverbindung...");
		}
		return c;
	}
	
	
	
	
}