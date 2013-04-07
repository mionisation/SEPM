package sepm.ss13.e1005233.test;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import sepm.ss13.e1005233.domain.Buchung;
import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.domain.Rechnung;
import sepm.ss13.e1005233.service.JDBCService;

public class JDBCServiceTest extends ServiceTest {

	private static final Logger log = Logger.getLogger(JDBCServiceTest.class);
	private JDBCService jdbcService;
	public JDBCServiceTest() {
		super();
		jdbcService = new JDBCService();
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

	/**
	 * Dieser Test �berpr�ft, ob die �berpr�fung von
	 * validen Buchungen funktioniert. Es werden sowohl g�ltige
	 * als auch ung�ltige Buchungen �berpr�ft
	 */
	@Test
	public void isBuchungValid() {
		log.debug("F�hre isBuchungValid-Test aus...");
		log.info("F�hre validen Buchungstest aus:");
		Buchung b1 = new Buchung(new Pferd(1), new Rechnung(new Timestamp(1231456L)), 12, 4.4);
		assertThat(jdbcService.isValid(b1), is(true));
		
		log.info("F�hre nicht validen Buchungstest aus:");
		b1 = new Buchung(new Pferd(1), new Rechnung(new Timestamp(1231456L)), -12, 4.4);
		assertThat(jdbcService.isValid(b1), is(false));
		
		log.info("F�hre nicht validen Buchungstest aus:");
		b1 = new Buchung(new Pferd(1), new Rechnung(new Timestamp(1231456L)), 0, 0);
		assertThat(jdbcService.isValid(b1), is(false));
		
		log.debug("isBuchungValid-Test abgeschlossen!");

	}
	
	/**
	 * Dieser Test �berpr�ft, ob die �berpr�fung von
	 * validen Rechnungen funktioniert. Es werden sowohl g�ltige
	 * als auch ung�ltige Rechnungen �berpr�ft
	 */
	@Test
	public void isRechnungValid() {
		log.debug("F�hre isRechnungValid-Test aus...");
		
		log.info("F�hre validen Rechnungstest aus:");
		Rechnung r1 = new Rechnung(new Timestamp(12425212L), "Hansi", "Ueberweisung", 1234, 57, 1337, null);
		assertThat(jdbcService.isValid(r1), is(true));
		
		log.info("F�hre nicht validen Rechnungstest (falsche Zahlungsart) aus:");
		r1 = new Rechnung(new Timestamp(12425212L), "Hansi", "Bestechung", 1234, 57, 1337, null);
		assertThat(jdbcService.isValid(r1), is(false));
		
		log.info("F�hre nicht validen Rechnungstest (leerer Name) aus:");
		r1 = new Rechnung(new Timestamp(12425212L), "", "Ueberweisung", 1234, 57, 1337, null);
		assertThat(jdbcService.isValid(r1), is(false));
		
		log.info("F�hre nicht validen Rechnungstest (negativer Preis) aus:");
		r1 = new Rechnung(new Timestamp(12425212L), "Heinrich", "Ueberweisung", -1234, 57, 1337, null);
		assertThat(jdbcService.isValid(r1), is(false));
		
		log.info("F�hre nicht validen Rechnungstest (kein Name) aus:");
		r1 = new Rechnung(new Timestamp(12425212L), null, "Ueberweisung", 1234, 57, 1337, null);
		assertThat(jdbcService.isValid(r1), is(false));
		
		log.debug("isRechnungValid-Test abgeschlossen!");
	}
	
	/**
	 * Dieser Test �berpr�ft, ob die �berpr�fung von
	 * validen Pferden funktioniert. Es werden sowohl g�ltige
	 * als auch ung�ltige Pferde �berpr�ft
	 */
	@Test
	public void isPferdValid() {
		log.debug("F�hre isPferdValid-Test aus...");
		
		log.info("F�hre validen Pferdetest aus:");
		Pferd p1 = new Pferd(1, "Johnny", "/bild.jpg", 12.2, "Hippotherapie", "Schimmel", false, false);
		assertThat(jdbcService.isValid(p1), is(true));
		
		log.info("F�hre nicht validen Pferdetest (keine Therapieart angegeben) aus:");
		p1 = new Pferd(2, "Johnny", "/bild.jpg", 12.2, "", "Schimmel", false, false);
		assertThat(jdbcService.isValid(p1), is(false));

		log.info("F�hre nicht validen Pferdetest (unzul�ssige Preisangabe) aus:");
		p1 = new Pferd(3, "Johnny", "/bild.jpg", 0, "HPR", "Schimmel", false, false);
		assertThat(jdbcService.isValid(p1), is(false));
		
		log.info("F�hre nicht validen Pferdetest (kein Name) aus:");
		p1 = new Pferd(2, null, "/bild.jpg", 5, "HPR", "Schimmel", false, false);
		assertThat(jdbcService.isValid(p1), is(false));
		
		log.info("F�hre nicht validen Pferdetest (kein Pfad f�r Foto) aus:");
		p1 = new Pferd(5, "Johnny", null, 8, "HPR", "Schimmel", false, false);
		assertThat(jdbcService.isValid(p1), is(false));
		
		
	}
	
}
