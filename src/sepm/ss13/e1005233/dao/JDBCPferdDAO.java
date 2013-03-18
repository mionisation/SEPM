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
	public void insertPferd(Pferd p) throws SQLException {
		log.info("Beginne Einfügvorgang...");
		log.debug("Füge Pferd ein mit Id " + p.getId());
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
		log.info("Einfügen abgeschlossen.");

	}

	@Override
	public Pferd getPferd(Pferd p) throws SQLException {
		log.info("Beginne Rückgabevorgang...");
		log.debug("Gebe Pferd zurück mit Id" + p.getId());
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
	}

	@Override
	public void updatePferd(Pferd p) throws SQLException {
		log.info("Beginne Aktualisierungsvorgang...");
		log.debug("Aktualisiere Pferd mit Id" + p.getId());
		
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
		log.info("Aktualisierung abgeschlossen.");
		
	}

	@Override
	public void deletePferd(Pferd p) throws SQLException {
		log.info("Beginne Löschvorgang...");
		log.debug("Lösche Pferd mit Id" + p.getId());
		pst = c.prepareStatement("DELETE FROM Pferde WHERE ID = ?");
		pst.setInt(1, p.getId());
		pst.executeUpdate();
		pst.close();
		log.info("Löschen abgeschlossen.");
		
		
	}
	
	@Override
	public int getNewId() throws SQLException {
		result = st.executeQuery("SELECT COUNT(1) FROM PFERDE");
		result.next();
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
	 * Gibt die Verbindung zur HSQLDB zurück
	 * @return die Connection zur Datenbank
	 */
	public Connection getConnection() {
		return c;
	}


}
