package sepm.ss13.e1005233.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import sepm.ss13.e1005233.domain.Buchung;
import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.domain.Rechnung;
import sepm.ss13.e1005233.exceptions.JDBCPferdPersistenceException;
import sepm.ss13.e1005233.exceptions.JDBCRechnungPersistenceException;

/**
 * Stellt Methoden zum Austausch von Rechnung-entitäten zu einer über JDBC
 * realisierten HSQLDB-Datenbank zur Verfügung
 *
 */
public class JDBCRechnungDAO implements RechnungDAO {

	private ConnectionTool ct;
	private Connection c;
	private Statement st;
	private PreparedStatement pst;
	private static final Logger log = Logger.getLogger(JDBCRechnungDAO.class);
	private ResultSet result;

	
	public JDBCRechnungDAO() throws SQLException {
		log.debug("Initialisiere JDBCRechnungDAO...");
		ct = new ConnectionTool();
		ct.openConnection();
		c = ct.getConnection();
		st = c.createStatement();
		
	}
	
	@Override
	public void insertRechnung(Rechnung r) throws JDBCRechnungPersistenceException {
		log.info("Beginne Einfügevorgang...");
		log.debug("Füge Rechnung ein mit Datum " + r.getDate().toString());
		
		
		try {
			pst = c.prepareStatement("INSERT INTO Rechnungen (datum, name, gesamtpreis, "
					+"gesamtstunden, zahlungsart, telefon) VALUES (?,?,?,?,?,?)");
			pst.setTimestamp(1, r.getDate());
			pst.setString(2,r.getName());
			pst.setDouble(3,r.getGesamtpreis());
			pst.setInt(4,r.getGesamtstunden());
			pst.setString(5,r.getZahlungsart());
			pst.setLong(6,r.getTelefon());
			pst.executeUpdate();
			pst.close();
			log.debug("Rechnung eingefügt! Füge jetzt zugehörige Buchungen ein...");
			for(Buchung b : r.getBuchungen()) {
				pst = c.prepareStatement("INSERT INTO Buchung(datum, id, preis, "
						+"stunden) VALUES (?,?,?,?)");
				pst.setTimestamp(1, r.getDate());
				pst.setInt(2,b.getPferd().getId());
				pst.setDouble(3, b.getPreis());
				pst.setInt(4, b.getStunden());
				pst.executeUpdate();
				pst.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.debug("Fehler während des Einfügens aufgetreten!");
			throw new JDBCRechnungPersistenceException();
		}
		log.info("Einfügen abgeschlossen!");
	
		
		
	}

	@Override
	public Rechnung getRechnung(Rechnung r) {
		log.debug("Gebe Rechnung zurück mit Datum " + r.getDate().toString());
		return null;
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
	 * Gibt die Verbindung zur HSQLDB zurück
	 * @return die Connection zur Datenbank
	 */
	public Connection getConnection() {
		return c;
	}

	@Override
	public List<Rechnung> findAll() throws JDBCRechnungPersistenceException {
		log.info("Finde alle Rechnungen...");
		try {
		result = st.executeQuery("SELECT * FROM Rechnungen");
		log.debug("Gebe Liste aller gespeicherten Rechnungen zurück...");
		return genRechnungList(result);
		} catch(SQLException e) {
			log.debug("Fehler während der Rückgabe aller Rechnungen aufgetreten!");
			throw new JDBCRechnungPersistenceException();
		}
	}
	
	/**
	 * Generiert aus einem ResultSet, das Rechnungen enthält,
	 * eine Liste aus Rechnungen
	 * @param set ein ResultSet aus Rechnungen
	 * @return eine Liste der Rechnungen
	 * @throws JDBCRechnungPersistenceException 
	 */
	public List<Rechnung> genRechnungList(ResultSet set) throws JDBCRechnungPersistenceException {
		ArrayList<Rechnung> rechnungen = new ArrayList<Rechnung>();
		try {
			while(set.next()) {
				rechnungen.add(new Rechnung(set.getTimestamp("datum"), set.getString("name"),
						set.getString("zahlungsart"), set.getDouble("gesamtpreis"),
						set.getInt("gesamtstunden"), set.getLong("telefon"),
						getBuchungen(new Rechnung(set.getTimestamp("datum")))));
			}
		} catch (SQLException e) {
			log.debug("Fehler während der Erstellung der Rechnungsliste aufgetreten!");
			throw new JDBCRechnungPersistenceException();
		}
		log.debug("Liste der Rechnungen:");
		for(Rechnung r: rechnungen) {
			log.info("Rechnung Datum:  " + r.toString());
		}
		return rechnungen;
		
	}


}
