package sepm.ss13.e1005233.test;

import java.util.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import sepm.ss13.e1005233.dao.PferdDAO;
import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.domain.SuchPferd;
import sepm.ss13.e1005233.exceptions.*;

/**
 * Schablonenklasse, die spezifiziert, welche Tests für
 * DAO-Implementierungen vorgesehen sind und erfüllt werden
 * müssen
 */
public abstract class PferdDAOTest {
	protected PferdDAO pferdDao;
	protected Pferd testpferd, testpferd2, testpferd3;
	protected List<Pferd> pferde;
	private static final Logger log = Logger.getLogger(PferdDAOTest.class);
	
	public PferdDAOTest() {
		this.pferdDao = null;
		this.testpferd = null;
		this.testpferd2 = null;
		this.testpferd3 = null;
		this.pferde = new ArrayList<Pferd>();
	}
	
	/**
	 * Setzt das konkrete DAO, das getestet wird
	 * @param pdao das zu testende PferdDAO
	 */
	protected void setPferdDAO(PferdDAO pdao) {
		this.pferdDao = pdao;
	}
	
	/**
	 * Fügt ein Pferd ein und überprüft ob es vorhanden ist 
	 * @param p das einzufügende Pferd
	 * @throws PferdPersistenceException wenn ein Fehler während des Einfügens auftritt
	 */
	public void insertPferdAndCheck(Pferd p) throws PferdPersistenceException {
		log.debug("Führe insertPferdAndCheck-Test aus..." );
		//das Pferd darf nicht enthalten sein
		pferde = pferdDao.findAll();
		assertThat( pferde.contains(p), is(false));
		pferdDao.insertPferd(p);
		//das Pferd muss enthalten sein
		pferde = pferdDao.findAll();
		assertThat( pferde.contains(p), is(true));
	}
	
	/**
	 * Dieser Test versucht eine Entität, die auf Null
	 * referenziert, zu speichern. Das DAO muss eine Exception werfen.
	 * @throws Exception soll geworfen werden
	 */
	@Test(expected = NullPointerException.class)
	public void insertNullEntityThrowsException() throws Exception {
		log.debug("Führe insertNullEntityThrowsException-Test aus..." );
		testpferd= null;
		pferdDao.insertPferd(testpferd);
	}
	
	/**
	 * Dieser Test speichert eine valide Entität ein
	 * @throws PferdPersistenceException  wenn ein Fehler während des Tests auftritt
	 */
	@Test
	public void insertValidEntity() throws PferdPersistenceException {
		log.debug("Führe insertValidEntity-Test aus..." );
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		//füge Pferd ein
		insertPferdAndCheck(testpferd);	
	}
	
	/**
	 * Dieser Test versucht eine Entität abzuspeichern, die den Wert null in Feldern hat,
	 *  die nicht null sein dürfen. Die Datenbank muss eine PferdPersistenceException werfen
	 *  @throws PferdPersistenceException soll geworfen werden
	 */
	@Test(expected = PferdPersistenceException.class)
	public void insertNullArgumentEntityThrowsException() throws PferdPersistenceException {
		log.debug("Führe insertNullArgumentEntityThrowsException-Test aus..." );
		testpferd = new Pferd(pferdDao.getNewId());
		testpferd.setTherapieart("Hippotherapie");
		testpferd.setRasse("Lippizaner");
		pferdDao.insertPferd(testpferd);
	}
	
	/**
	 * Dieser Test speichert eine valide Entität in die Datenbank
	 * und sucht sie wieder heraus
	 * @throws PferdPersistenceException wenn ein Fehler während des Tests auftritt
	 */
	@Test
	public void insertAndRetrieveValidEntity() throws PferdPersistenceException {
		log.debug("Führe insertAndRetrieveValidEntity-Test aus..." );
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		Pferd testpferd2;
		//füge Pferd ein
		insertPferdAndCheck(testpferd);
		//nach dem Pferd wird gesucht, es wird zurückgegeben
		testpferd2 = pferdDao.getPferd(testpferd);
		//die zwei Pferde müssen gleich sein
		assertThat(testpferd2, is(testpferd));
	}
	
	/**
	 * Dieser Test speichert eine valide Entität in die Datenbank
	 * und aktualisiert sie mit neuen Werten
	 * @throws PferdPersistenceException 
	 * @throws Exception wenn ein Fehler während des Tests auftritt
	 */
	@Test
	public void insertAndUpdateValidEntity() throws PferdPersistenceException {
		log.debug("Führe insertAndUpdateValidEntity-Test aus..." );
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		Pferd testpferd2 =  new Pferd(pferdDao.getNewId(), "Lizz", "/profil.jpg",
				10.2, "HPV", "Haflinger", true, true);;
		//füge Pferd ein
		insertPferdAndCheck(testpferd);
		//das Pferd wird aktualisiert
		testpferd.setTherapieart("HPV");
		testpferd.setFoto("/profil.jpg");
		testpferd.setName("Lizz");
		testpferd.setDeleted(true);
		pferdDao.updatePferd(testpferd);
		//hole Pferd wieder heraus
		testpferd = pferdDao.getPferd(testpferd);
		//die zwei Pferde müssen gleich sein
		assertThat(testpferd2, is(testpferd));
	}
	
	/**
	 * Dieser Test speichert eine valide Entität in die Datenbank und
	 * löscht sie wieder heraus
	 * @throws PferdPersistenceException wenn ein Fehler während des Tests auftritt
	 */
	@Test
	public void insertAndDeleteValidEntity() throws PferdPersistenceException  {
		log.debug("Führe insertAndDeleteValidEntity-Test aus..." );
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		//füge Pferd ein
		insertPferdAndCheck(testpferd);
		//das Pferd wird gelöscht
		pferdDao.deletePferd(testpferd);
		//es darf nicht mehr enthalten sein
		pferde = pferdDao.findAll();
		assertThat( pferde.contains(testpferd), is(false));
	}
	
	//TODO find by tests
	/**
	 * Dieser Test speichert ein paar Entitäten in die Datenbank und
	 * versucht sie über eine Suchanfrage über Name, Rasse und Kinderfreundlichkeit herauszubekommen
	 * @throws PferdPersistenceException wenn ein Fehler während des Tests auftritt
	 */
	@Test
	public void insertAndRetrieveByNameRaceChildfriendly() throws PferdPersistenceException {
		log.debug("Führe insertAndRetrieveByNameRaceChildfriendly-Test aus...");
		//erstelle Pferde und füge sie ein
		testpferd = new Pferd(pferdDao.getNewId(), "Rolfi", "/bild.jpg",
				10.2, "HPR", "Schimmel", true, false);
		insertPferdAndCheck(testpferd);
		testpferd2 = new Pferd(pferdDao.getNewId(), "Rolfi", "/bild.jpg",
				10.2, "HPR", "Schimmel", true, false);
		insertPferdAndCheck(testpferd2);
		testpferd3 = new Pferd(pferdDao.getNewId(), "Rolfi", "/bild.jpg",
				10.2, "HPR", "Haflinger", false, false);
		insertPferdAndCheck(testpferd3);

		//erstelle neues Suchpferd und suche mit jenem nach Pferden
		SuchPferd sp = new SuchPferd("Rolfi", null, "Schimmel", 0, 0, true);
		pferde = pferdDao.findBy(sp);
		//Pferde müssen enthalten sein
		assertThat(pferde.contains(testpferd), is(true));
		assertThat(pferde.contains(testpferd2), is(true));
		
	}
}
