package sepm.ss13.e1005233.test;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import sepm.ss13.e1005233.dao.JDBCRechnungDAO;

public class JDBCRechnungDAOTest extends RechnungDAOTest {

	private static final Logger log = Logger.getLogger(JDBCRechnungDAOTest.class);

	public JDBCRechnungDAOTest() {
		super();
	}
	
	/**
	 * Nimmt Initialisierungen und Vorbereitungen für den Test vor
	 * @throws SQLException
	 */
	@Before
	public void setUp() throws SQLException {
		log.info("Bereite " + this.getClass().getSimpleName() +" vor...");
		rechnungDao = new JDBCRechnungDAO();
		setRechnungDAO(rechnungDao);
		//auto-commit deaktivieren
		((JDBCRechnungDAO) rechnungDao).getConnection().setAutoCommit(false);
		log.debug(this.getClass().getSimpleName() +" erfolgreich vorbereitet!");
	}
	
	/**
	 * Nimmt Nachbereitungen nach dem Testen vor
	 * @throws SQLException
	 */
	@After
	public void tearDown() throws SQLException {
		//Transaktionen zurücksetzen
		log.info("Setze " + this.getClass().getSimpleName() +" zurück...");
		((JDBCRechnungDAO) rechnungDao).getConnection().rollback();
		log.debug(this.getClass().getSimpleName() +" erfolgreich zurückgesetzt!");

	}

}
