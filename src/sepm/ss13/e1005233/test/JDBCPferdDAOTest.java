package sepm.ss13.e1005233.test;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

import sepm.ss13.e1005233.dao.JDBCPferdDAO;

public class JDBCPferdDAOTest extends PferdDAOTest {

	public JDBCPferdDAOTest() {
		super();
	}
	
	/**
	 * Nimmt Initialisierungen und Vorbereitungen für den Test vor
	 * @throws SQLException
	 */
	@Before
	public void setUp() throws SQLException {
		pferdDao = new JDBCPferdDAO();
		setPferdDAO(pferdDao);
		//auto-commit deaktivieren
		((JDBCPferdDAO) pferdDao).getConnection().setAutoCommit(false);
		
	}
	
	/**
	 * Nimmt Nachbereitungen nach dem Testen vor
	 * @throws SQLException
	 */
	@After
	public void tearDown() throws SQLException {
		//Transaktionen zurücksetzen
		((JDBCPferdDAO) pferdDao).getConnection().rollback();
	}

}
