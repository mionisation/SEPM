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
	 * Nimmt Initialisierungen und Vorbereitungen für den Test vor
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
		//Transaktionen zurücksetzen
		log.info("Setze " + this.getClass().getSimpleName() +" zurück...");
		((JDBCService) service).getConnection().rollback();
		log.debug(this.getClass().getSimpleName() +" erfolgreich zurückgesetzt!");

	}
	//TODO tests
	/**
	 * Dieser Test überprüft, ob die Überprüfung von
	 * validen Buchungen funktioniert. Es werden sowohl gültige
	 * als auch ungültige Buchungen überprüft
	 */
	@Test
	public void isBuchungValid() {
		log.debug("Führe isBuchungValid-Test aus...");
		log.info("Führe validen Buchungstest aus:");
		//assertThat(jdbcService.isValid(new Buchung(pferd, rechnung, stunden, preis)), is(true));
	}
	
	/**
	 * Dieser Test überprüft, ob die Überprüfung von
	 * validen Rechnungen funktioniert. Es werden sowohl gültige
	 * als auch ungültige Rechnungen überprüft
	 */
	@Test
	public void isRechnungValid() {
		
	}
	
	/**
	 * Dieser Test überprüft, ob die Überprüfung von
	 * validen PFerden funktioniert. Es werden sowohl gültige
	 * als auch ungültige Pferde überprüft
	 */
	@Test
	public void isPferdValid() {
		
	}
	
}
