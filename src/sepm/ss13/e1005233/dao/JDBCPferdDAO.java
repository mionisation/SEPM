package sepm.ss13.e1005233.dao;

import java.sql.Connection;
import java.sql.SQLException;

import sepm.ss13.e1005233.domain.Pferd;

/**
 * Stellt Methoden zum Austausch von Pferd-entitäten zu einer über JDBC
 * realisierten HSQLDB-Datenbank zur Verfügung
 *
 */
public class JDBCPferdDAO implements PferdDAO {
	
	ConnectionTool ct;
	Connection c;
	
	public JDBCPferdDAO() throws SQLException {
		ct = new ConnectionTool();
		ct.openConnection();
		c = ct.getConnection();
	}
	
	@Override
	public void insertPferd(Pferd p) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pferd getPferd(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePferd(int id, Pferd p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePferd(int id) {
		// TODO Auto-generated method stub

	}

}
