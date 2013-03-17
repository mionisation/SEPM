package sepm.ss13.e1005233.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import sepm.ss13.e1005233.domain.Rechnung;

public class JDBCRechnungDAO implements RechnungDAO {

	/**
	 * Stellt Methoden zum Austausch von Rechnung-entitäten zu einer über JDBC
	 * realisierten HSQLDB-Datenbank zur Verfügung
	 *
	 */
	ConnectionTool ct;
	Connection c;
	
	public JDBCRechnungDAO() throws SQLException {
		ct = new ConnectionTool();
		ct.openConnection();
		c = ct.getConnection();
	}
	
	@Override
	public void insertRechnung(Rechnung r) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rechnung getRechnung(Rechnung r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateRechnung(Rechnung r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRechnung(Rechnung r) {
		// TODO Auto-generated method stub
		
	}


}
