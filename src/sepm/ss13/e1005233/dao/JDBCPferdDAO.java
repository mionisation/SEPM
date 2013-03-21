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
import sepm.ss13.e1005233.exceptions.JDBCPferdPersistenceException;

/**
 * Stellt Methoden zum Austausch von Pferd-entit�ten zu einer �ber JDBC
 * realisierten HSQLDB-Datenbank zur Verf�gung
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
		log.info("Beginne Einf�gvorgang...");
		log.debug("F�ge Pferd ein mit Id " + p.getId());
		
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
		log.info("Einf�gen abgeschlossen.");

	}

	@Override
	public Pferd getPferd(Pferd p) throws JDBCPferdPersistenceException {
		log.info("Beginne R�ckgabevorgang...");
		log.debug("Gebe Pferd zur�ck mit Id" + p.getId());
		try {
		pst = c.prepareStatement("SELECT * FROM Pferde WHERE ID = ?");
		pst.setInt(1, p.getId());
		result = pst.executeQuery();
		pst.close();
		result.next();
		log.info("R�ckgabe abgeschlossen.");
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
		log.info("Beginne L�schvorgang...");
		log.debug("L�sche Pferd mit Id" + p.getId());
		
		try {
		pst = c.prepareStatement("DELETE FROM Pferde WHERE ID = ?");
		pst.setInt(1, p.getId());
		pst.executeUpdate();
		pst.close();
		} catch(SQLException e) {
			throw new JDBCPferdPersistenceException();
		}
		log.info("L�schen abgeschlossen.");
		
		
	}
	
	@Override
	public int getNewId() throws JDBCPferdPersistenceException {
		int id;
		log.info("Beginne Id Generierung...");
		try {
		result = st.executeQuery("SELECT COUNT(1) FROM PFERDE");
		result.next();
		id = result.getInt(1) + 1;
		log.debug("Gebe neue ID zur�ck mit Wert " + id);
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
		ArrayList<Pferd> pferde = new ArrayList<Pferd>();
		while(result.next()) {
			pferde.add(new Pferd(result.getInt("id"), result.getString("name"),
					result.getString("foto"), result.getDouble("preis"),
					result.getString("therapieart"), result.getString("rasse"),
					result.getBoolean("kinderfreundlich"), result.getBoolean("deleted")));
		}
		log.debug("Gebe Liste aller gespeicherten Pferde zur�ck...");
		return pferde;
		} catch(SQLException e) {
			throw new JDBCPferdPersistenceException();
		}
	}
	
	/**
	 * Gibt die Verbindung zur HSQLDB zur�ck
	 * @return die Connection zur Datenbank
	 */
	public Connection getConnection() {
		return c;
	}


}
