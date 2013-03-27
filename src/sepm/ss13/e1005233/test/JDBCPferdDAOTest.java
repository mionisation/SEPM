package sepm.ss13.e1005233.test;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import sepm.ss13.e1005233.dao.JDBCPferdDAO;

public class JDBCPferdDAOTest extends PferdDAOTest {

	private static final Logger log = Logger.getLogger(JDBCPferdDAOTest.class);

	public JDBCPferdDAOTest() {
		super();
	}
	
	/**
	 * Nimmt Initialisierungen und Vorbereitungen f�r den Test vor
	 * @throws SQLException
	 */
	@Before
	public void setUp() throws SQLException {
		log.info("Bereite " + this.getClass().getSimpleName() +" vor...");
		pferdDao = new JDBCPferdDAO();
		setPferdDAO(pferdDao);
		//auto-commit deaktivieren
		((JDBCPferdDAO) pferdDao).getConnection().setAutoCommit(false);
		log.debug(this.getClass().getSimpleName() +" erfolgreich vorbereitet!");
	}
	
	/**
	 * Nimmt Nachbereitungen nach dem Testen vor
	 * @throws SQLException
	 */
	@After
	public void tearDown() throws SQLException {
		//Transaktionen zur�cksetzen
		log.info("Setze " + this.getClass().getSimpleName() +" zur�ck...");
		((JDBCPferdDAO) pferdDao).getConnection().rollback();
		log.debug(this.getClass().getSimpleName() +" erfolgreich zur�ckgesetzt!");

	}
	
}
