package sepm.ss13.e1005233.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import sepm.ss13.e1005233.domain.Pferd;

/**
 * Stellt Methoden zum Austausch von Pferd-entit�ten zu einer �ber JDBC
 * realisierten HSQLDB-Datenbank zur Verf�gung
 *
 */
public class JDBCPferdDAO implements PferdDAO {
	
	private ConnectionTool ct;
	private Connection c;
	private Statement st;
	private static final Logger log = Logger.getLogger(JDBCPferdDAO.class);
	private ResultSet result;
			
	public JDBCPferdDAO() throws SQLException {
		log.debug("Initialisiere JDBCPferdDAO...");
		ct = new ConnectionTool();
		ct.openConnection();
		c = ct.getConnection();
		st = c.createStatement();
		result = null;
	}
	
	@Override
	public void insertPferd(Pferd p) {
		log.debug("F�ge Pferd ein mit Id " + p.getId());

	}

	@Override
	public Pferd getPferd(Pferd p) {
		log.debug("Gebe Pferd zur�ck mit Id" + p.getId());
		return null;
	}

	@Override
	public void updatePferd(Pferd p) {
		log.debug("Aktualisiere Pferd mit Id" + p.getId());
		
	}

	@Override
	public void deletePferd(Pferd p) {
		log.debug("L�sche Pferd mit Id" + p.getId());
		
	}
	
	public int getNewId() throws SQLException {
		result = st.executeQuery("SELECT COUNT(1) FROM PFERDE");
		return (result.getInt(1) + 1);
	}


	@Override
	public List<Pferd> findAll() throws SQLException {
		result = st.executeQuery("SELECT * FROM PFERDE");
		ArrayList<Pferd> pferde = new ArrayList<Pferd>();
		while(result.next()) {
			pferde.add(new Pferd(result.getInt("id"), result.getString("name"),
					result.getString("foto"), result.getDouble("preis"),
					result.getString("therapieart"), result.getString("rasse"),
					result.getBoolean("kinderfreundlich"), result.getBoolean("deleted")));
		}
		return pferde;
	}
	
	/**
	 * Gibt die Verbindung zur HSQLDB zur�ck
	 * @return die Connection zur Datenbank
	 */
	public Connection getConnection() {
		return c;
	}


}
