package sepm.ss13.e1005233.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import sepm.ss13.e1005233.dao.RechnungDAO;
import sepm.ss13.e1005233.domain.Buchung;
import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.domain.Rechnung;
import sepm.ss13.e1005233.exceptions.JDBCRechnungPersistenceException;
import sepm.ss13.e1005233.exceptions.RechnungPersistenceException;

/**
 * Schablonenklasse, die spezifiziert, welche Tests f�r
 * DAO-Implementierungen vorgesehen sind und erf�llt werden
 * m�ssen
 */
public abstract class RechnungDAOTest {
	protected RechnungDAO rechnungDao;
	private static final Logger log = Logger.getLogger(RechnungDAOTest.class);
	protected List<Rechnung> rechnungen;
	protected Rechnung testRechnung, testRechnung2;
	protected List<Buchung> buchungen, buchungen2; 
	
	public RechnungDAOTest() {
		this.rechnungDao = null;
		buchungen = new ArrayList<Buchung>();
		buchungen2 = new ArrayList<Buchung>();
	}
	
	/**
	 * Setzt das konkrete DAO, das getestet wird
	 * @param rdao das zu testende RechnungDAO
	 */
	protected void setRechnungDAO(RechnungDAO rdao) {
		this.rechnungDao = rdao;
	}
	
	/**
	 * F�gt eine Rechnung ein und �berpr�ft ob sie vorhanden ist 
	 * @param r die einzuf�gende Rechnung
	 * @throws RechnungPersistenceException wenn ein Fehler w�hrend des Einf�gens auftritt
	 */
	public void insertRechnungAndCheck(Rechnung r) throws RechnungPersistenceException {
		log.debug("F�hre insertRechnungAndCheck-Test aus..." );
		//die Rechnung darf nicht enthalten sein
		rechnungen = rechnungDao.findAll();
		assertThat( rechnungen.contains(r), is(false));
		rechnungDao.insertRechnung(r);
		//die Rechnung muss enthalten sein
		rechnungen = rechnungDao.findAll();
		assertThat( rechnungen.contains(r), is(true));
	}
	
	
	/**
	 * Dieser Test speichert eine valide Entit�t ein
	 * @throws RechnungPersistenceException  wenn ein Fehler w�hrend des Tests auftritt
	 */
	@Test
	public void insertValidEntity() throws RechnungPersistenceException {
		log.debug("F�hre insertValidEntity-Test aus..." );
		buchungen.clear();
		testRechnung = new Rechnung(new Timestamp(1364310163L), "Fredi Frederikson", "Barzahlung",  200.12, 13, 232342333332L,
				buchungen);
		buchungen.add(new Buchung(new Pferd(1), testRechnung, 2, 3.3));
		buchungen.add(new Buchung(new Pferd(2), testRechnung, 2, 3.3));
		//f�ge Rechnung ein
		insertRechnungAndCheck(testRechnung);	
	}
	
	/**
	 * Dieser Test versucht eine Entit�t, die auf Null
	 * referenziert, zu speichern. Das DAO muss eine Exception werfen.
	 * @throws Exception soll geworfen werden
	 */
	@Test(expected = NullPointerException.class)
	public void insertNullEntityThrowsException() throws Exception {
		log.debug("F�hre insertNullEntityThrowsException-Test aus..." );
		testRechnung = null;
		rechnungDao.insertRechnung(testRechnung);
	}
	
	/**
	 * Dieser Test versucht eine valide Entit�t, die abgespeichert wurde,
	 * wieder herauszuholen.
	 * @throws RechnungPersistenceException 
	 */
	@Test
	public void insertAndRetrieveValidEntity() throws RechnungPersistenceException {
		log.debug("F�hre insertAndRetrieveValidEntity-Test aus...");
		buchungen.clear();
		testRechnung = new Rechnung(new Timestamp(1364310162L), "Fredi Frederikson", "Kreditkarte",  200.12, 13, 232342333332L,
				buchungen);
		buchungen.add(new Buchung(new Pferd(1), testRechnung, 2, 3.3));
		buchungen.add(new Buchung(new Pferd(2), testRechnung, 2, 3.3));
		
		//f�ge Rechnung ein
		insertRechnungAndCheck(testRechnung);
		testRechnung2 = rechnungDao.getRechnung(testRechnung);
		assertThat(testRechnung, is(testRechnung2));
	}
	
	/**
	 * Dieser Test versucht eine Entit�t, die nicht vorhanden ist,
	 * herauszuholen. Das DAO muss eine Exception werfen.
	 * @throws RechnungPersistenceException
	 */
	@Test(expected = RechnungPersistenceException.class)
	public void retrieveInvalidEntity() throws RechnungPersistenceException {
		log.debug("F�hre retrieveInvalidEntity-Test aus...");
		testRechnung = new Rechnung(new Timestamp(1364310252L), "Fredi Frederikson", "Kreditkarte",  200.12, 13, 1234589L,
				buchungen);
		//nach der Rechnung wird gesucht
		rechnungDao.getRechnung(testRechnung);
	}
		
	/**
	 * Dieser Test �berpr�ft, ob auch alle gespeicherten Rechnungen gefunden werden
	 * @throws RechnungPersistenceException 
	 */
	@Test
	public void findAllRechnungenTest() throws RechnungPersistenceException {
		log.debug("F�hre findAllRechnungenTest aus...");
		buchungen.clear();
		
		testRechnung = new Rechnung(new Timestamp(1364310162L), "Fredi Frederikson", "Kreditkarte",  200.12, 13, 232342333332L,
				buchungen);
		buchungen.add(new Buchung(new Pferd(1), testRechnung, 2, 3.3));
		buchungen.add(new Buchung(new Pferd(2), testRechnung, 2, 3.3));
		

		testRechnung2 = new Rechnung(new Timestamp(1364310164L), "Inge Ingeborg", "Ueberweisung",  170.12, 13, 6673342533332L,
				buchungen2);
		buchungen2.add(new Buchung(new Pferd(1), testRechnung2, 2, 8));
		buchungen2.add(new Buchung(new Pferd(3), testRechnung2, 2, 6.3));
		
		//f�ge Rechnungen ein
		insertRechnungAndCheck(testRechnung2);
		insertRechnungAndCheck(testRechnung);

		rechnungen = rechnungDao.findAll();
		System.out.println(rechnungen.size());
		//die Rechnungen m�ssen jetzt enthalten sein
		assertThat(rechnungen.contains(testRechnung), is(true));
		assertThat(rechnungen.contains(testRechnung2), is(true));
	}
	
	/**
	 * Dieser Test �berpr�ft, ob auch alle Buchungen zu einer
	 * Rechnung zur�ckgegeben werden
	 * @throws RechnungPersistenceException 
	 */
	@Test
	public void getBuchungenforRechnungTest() throws RechnungPersistenceException {
		log.debug("F�hre findAllRechnungenTest aus...");
		
		buchungen.clear();
		testRechnung = new Rechnung(new Timestamp(1254310162L), "Alex Alekksen", "Kreditkarte",  200.12, 13, 232342333332L,
				buchungen);
		buchungen.add(new Buchung(new Pferd(4), testRechnung, 2, 3.3));
		buchungen.add(new Buchung(new Pferd(5), testRechnung, 4, 7.3));
		insertRechnungAndCheck(testRechnung);
		
		buchungen = rechnungDao.getBuchungen(testRechnung);
		assertThat(buchungen.contains(new Buchung(new Pferd(4), testRechnung, 2, 3.3)), is(true));
		assertThat(buchungen.contains(new Buchung(new Pferd(5), testRechnung, 4, 7.3)), is(true));
	}
}
