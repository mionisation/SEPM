package sepm.ss13.e1005233.test;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sepm.ss13.e1005233.service.JDBCService;

public class JDBCServiceTest extends ServiceTest {

	private static final Logger log = Logger.getLogger(JDBCServiceTest.class);
	public JDBCServiceTest() {
		super();
		
	}
	
	/**
	 * Nimmt Initialisierungen und Vorbereitungen f�r den Test vor
	 * @throws SQLException
	 */
	@Before
	public void setUp() throws SQLException {
		log.info("Bereite " + this.getClass().getSimpleName() +" vor...");
		service = new JDBCService();
		setService(service);
		//auto-commit deaktivieren
		((JDBCService) service).getConnection().setAutoCommit(false);
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
		((JDBCService) service).getConnection().rollback();
		log.debug(this.getClass().getSimpleName() +" erfolgreich zur�ckgesetzt!");

	}
	//TODO tests
	/**
	 * Dieser Test �berpr�ft, ob die �berpr�fung von
	 * validen Buchungen funktioniert. Es werden sowohl g�ltige
	 * als auch ung�ltige Buchungen �berpr�ft
	 */
	@Test
	public void isBuchungValid() {
		log.debug("F�hre isBuchungValid-Test aus...");
		log.info("F�hre validen Buchungstest aus:");
		//assertThat(jdbcService.isValid(new Buchung(pferd, rechnung, stunden, preis)), is(true));
	}
	
	/**
	 * Dieser Test �berpr�ft, ob die �berpr�fung von
	 * validen Rechnungen funktioniert. Es werden sowohl g�ltige
	 * als auch ung�ltige Rechnungen �berpr�ft
	 */
	@Test
	public void isRechnungValid() {
		
	}
	
	/**
	 * Dieser Test �berpr�ft, ob die �berpr�fung von
	 * validen PFerden funktioniert. Es werden sowohl g�ltige
	 * als auch ung�ltige Pferde �berpr�ft
	 */
	@Test
	public void isPferdValid() {
		
	}
	
}
