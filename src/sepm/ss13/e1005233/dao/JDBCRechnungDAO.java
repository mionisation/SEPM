package sepm.ss13.e1005233.dao;

import java.sql.*;
import java.util.List;

import org.apache.log4j.Logger;

import sepm.ss13.e1005233.domain.Buchung;
import sepm.ss13.e1005233.domain.Rechnung;

/**
 * Stellt Methoden zum Austausch von Rechnung-entit�ten zu einer �ber JDBC
 * realisierten HSQLDB-Datenbank zur Verf�gung
 *
 */
public class JDBCRechnungDAO implements RechnungDAO {

	private ConnectionTool ct;
	private Connection c;
	private Statement st;
	private PreparedStatement pst;
	private static final Logger log = Logger.getLogger(JDBCRechnungDAO.class);

	
	public JDBCRechnungDAO() throws SQLException {
		log.debug("Initialisiere JDBCRechnungDAO...");
		ct = new ConnectionTool();
		ct.openConnection();
		c = ct.getConnection();
		st = c.createStatement();
		
	}
	
	@Override
	public void insertRechnung(Rechnung r) {
		log.debug("F�ge Rechnung ein mit Datum " + r.getDate().toString());
		
	}

	@Override
	public Rechnung getRechnung(Rechnung r) {
		log.debug("Gebe Rechnung zur�ck mit Datum " + r.getDate().toString());
		return null;
	}

	@Override
	public void updateRechnung(Rechnung r) {
		log.debug("Aktualisiere Rechnung mit Datum " + r.getDate().toString());
		
	}

	@Override
	public void deleteRechnung(Rechnung r) {
		log.debug("L�sche Rechnung mit Datum " + r.getDate().toString());
		
	}
	
	//TODO implement
	public List<Buchung> getBuchungen(Rechnung r) {
		return null;
	}
	
	//TODO implement
	public List<Buchung> genBuchungList(ResultSet set) {
		return null;
	}
	
	/**
	 * Gibt die Verbindung zur HSQLDB zur�ck
	 * @return die Connection zur Datenbank
	 */
	public Connection getConnection() {
		return c;
	}


}
