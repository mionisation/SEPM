package sepm.ss13.e1005233.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.domain.SuchPferd;
import sepm.ss13.e1005233.exceptions.JDBCPferdPersistenceException;

/**
 * Stellt Methoden zum Austausch von Pferd-entitäten zu einer über JDBC
 * realisierten HSQLDB-Datenbank zur Verfügung
 *
 */
public class JDBCPferdDAO implements PferdDAO {
	
	private ConnectionTool ct;
	private Connection c;
	private Statement st;
	private PreparedStatement pst;
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
	public void insertPferd(Pferd p) throws JDBCPferdPersistenceException {
		log.info("Beginne Einfügvorgang...");
		log.debug("Füge Pferd ein mit Id " + p.getId());
		
		try {
		pst = c.prepareStatement("INSERT INTO Pferde (id, name, foto, preis, therapieart, "
				+"rasse, kinderfreundlich, deleted) VALUES (?,?,?,?,?,?,?,?)");
		
		pst.setInt(1,p.getId());
		pst.setString(2,p.getName());
		pst.setString(3,p.getFoto());
		pst.setDouble(4,p.getPreis());
		pst.setString(5,p.getTherapieart());
		pst.setString(6,p.getRasse());
		pst.setBoolean(7,p.isKinderfreundlich());
		pst.setBoolean(8,p.isDeleted());
		pst.executeUpdate();
		pst.close();
		} catch(SQLException e) {
			throw new JDBCPferdPersistenceException();
		}
		log.info("Einfügen abgeschlossen.");

	}

	@Override
	public Pferd getPferd(Pferd p) throws JDBCPferdPersistenceException {
		log.info("Beginne Rückgabevorgang...");
		log.debug("Gebe Pferd zurück mit Id" + p.getId());
		try {
		pst = c.prepareStatement("SELECT * FROM Pferde WHERE ID = ?");
		pst.setInt(1, p.getId());
		result = pst.executeQuery();
		pst.close();
		result.next();
		log.info("Rückgabe abgeschlossen.");
		return new Pferd(result.getInt("id"), result.getString("name"),
				result.getString("foto"), result.getDouble("preis"),
				result.getString("therapieart"), result.getString("rasse"),
				result.getBoolean("kinderfreundlich"), result.getBoolean("deleted"));
		} catch(SQLException e) {
			throw new JDBCPferdPersistenceException();
		}
	}

	@Override
	public void updatePferd(Pferd p) throws JDBCPferdPersistenceException {
		log.info("Beginne Aktualisierungsvorgang...");
		log.debug("Aktualisiere Pferd mit Id" + p.getId());
		
		try {
		pst = c.prepareStatement("UPDATE Pferde SET name = ?, foto = ?, preis = ?, therapieart = ?, "
				+"rasse = ?, kinderfreundlich = ?, deleted = ? WHERE id = ?");
		
		pst.setString(1,p.getName());
		pst.setString(2,p.getFoto());
		pst.setDouble(3,p.getPreis());
		pst.setString(4,p.getTherapieart());
		pst.setString(5,p.getRasse());
		pst.setBoolean(6,p.isKinderfreundlich());
		pst.setBoolean(7,p.isDeleted());
		pst.setInt(8,p.getId());
		pst.executeUpdate();
		pst.close();
		} catch(SQLException e) {
			throw new JDBCPferdPersistenceException();
		}
		
		log.info("Aktualisierung abgeschlossen.");
		
	}

	@Override
	public void deletePferd(Pferd p) throws JDBCPferdPersistenceException {
		log.info("Beginne Löschvorgang...");
		log.debug("Lösche Pferd mit Id" + p.getId());
		
		try {
		pst = c.prepareStatement("UPDATE Pferde SET deleted = ? WHERE id = ?");
		pst.setBoolean(1, true);
		pst.setInt(2, p.getId());
		pst.executeUpdate();
		pst.close();
		} catch(SQLException e) {
			throw new JDBCPferdPersistenceException();
		}
		log.info("Löschen abgeschlossen.");
		
		
	}
	
	@Override
	public int getNewId() throws JDBCPferdPersistenceException {
		int id;
		log.info("Beginne Id Generierung...");
		try {
		result = st.executeQuery("SELECT COUNT(1) FROM PFERDE");
		result.next();
		id = result.getInt(1) + 1;
		log.debug("Gebe neue ID zurück mit Wert " + id);
		return id;
		} catch (SQLException e) {
			throw new JDBCPferdPersistenceException();
		}
	}


	@Override
	public List<Pferd> findAll() throws JDBCPferdPersistenceException {
		log.info("Finde alle Pferde...");
		try {
		result = st.executeQuery("SELECT * FROM PFERDE");
		log.debug("Gebe Liste aller gespeicherten Pferde zurück...");
		return genPferdList(result);
		} catch(SQLException e) {
			throw new JDBCPferdPersistenceException();
		}
	}
	
	/**
	 * Gibt die Verbindung zur HSQLDB zurück
	 * @return die Connection zur Datenbank
	 */
	public Connection getConnection() {
		return c;
	}

	@Override
	public List<Pferd> findBy(SuchPferd sp) throws JDBCPferdPersistenceException {
		log.info("Finde alle Pferde die den Suchkriterien entsprechen...");
		try {
		result = st.executeQuery(getSQLStatement(sp));
		log.debug("Gebe Liste der den Suchkriterien entsprechenden Pferde zurück...");
		return genPferdList(result);
		} catch(SQLException e) {
			throw new JDBCPferdPersistenceException();
		}
	}

	/**
	 * Diese Methode generiert ein passendes SQL-Statement aus einem
	 * angegebenen Suchpferd
	 * @param sp das Suchpferd mit den gesuchten Attributen
	 * @return eine SQL-SELECT-Abfrage die nach den gewünschten Attributen sucht
	 */
	private String getSQLStatement(SuchPferd sp) {
		log.info("Generiere SQL-Statement...");
		boolean set = false;
		String statement = "SELECT * FROM PFERDE WHERE ";
		
		if(sp.getName() != null && !sp.getName().isEmpty()) {
			statement += "NAME = '" + sp.getName() + "' ";
			set = true;
		}
		if(sp.getMinpreis() > 0) {
			if(set)
				statement += " AND ";
			statement += "PREIS > " + sp.getMinpreis();
			set = true;
		}
		if(sp.getMaxpreis() > 0) {
			if(set)
				statement += " AND ";
			statement +=" PREIS < " + sp.getMaxpreis();
			set = true;
		}
		if(sp.getRasse() != null && !sp.getRasse().isEmpty()) {
			if(set)
				statement += " AND ";
			statement += "RASSE ='" + sp.getRasse() + "' ";
			set = true;
		}
		if(sp.getTherapieart() != null && !sp.getTherapieart().isEmpty()) {
			if(set)
				statement += " AND ";
			statement += "AND THERAPIEART ='" + sp.getTherapieart() + "' ";
			set = true;
		}
		if(set) {
			statement += " AND ";
		}
		//es sollen nur nicht gelöschte Pferde gefunden werden
		statement += "DELETED = FALSE AND KINDERFREUNDLICH = " + sp.isKinderfreundlich();
		statement += ";";
		log.debug("SQL-Statement generiert: " + statement);
		return statement;
	}

	public List<Pferd> genPferdList(ResultSet set) throws JDBCPferdPersistenceException {
		ArrayList<Pferd> pferde = new ArrayList<Pferd>();
		try {
			while(set.next()) {
				pferde.add(new Pferd(set.getInt("id"), set.getString("name"),
						set.getString("foto"), set.getDouble("preis"),
						set.getString("therapieart"), set.getString("rasse"),
						set.getBoolean("kinderfreundlich"), set.getBoolean("deleted")));
			}
		} catch (SQLException e) {
			throw new JDBCPferdPersistenceException();
		}
		return pferde;
	}

}
