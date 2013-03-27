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
 * Schablonenklasse, die spezifiziert, welche Tests f�r
 * DAO-Implementierungen vorgesehen sind und erf�llt werden
 * m�ssen
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
	 * F�gt ein Pferd ein und �berpr�ft ob es vorhanden ist 
	 * @param p das einzuf�gende Pferd
	 * @throws PferdPersistenceException wenn ein Fehler w�hrend des Einf�gens auftritt
	 */
	public void insertPferdAndCheck(Pferd p) throws PferdPersistenceException {
		log.debug("F�hre insertPferdAndCheck-Test aus..." );
		//das Pferd darf nicht enthalten sein
		pferde = pferdDao.findAll();
		assertThat( pferde.contains(p), is(false));
		pferdDao.insertPferd(p);
		//das Pferd muss enthalten sein
		pferde = pferdDao.findAll();
		assertThat( pferde.contains(p), is(true));
	}
	
	/**
	 * Dieser Test versucht eine Entit�t, die auf Null
	 * referenziert, zu speichern. Das DAO muss eine Exception werfen.
	 * @throws Exception soll geworfen werden
	 */
	@Test(expected = NullPointerException.class)
	public void insertNullEntityThrowsException() throws Exception {
		log.debug("F�hre insertNullEntityThrowsException-Test aus..." );
		testpferd= null;
		pferdDao.insertPferd(testpferd);
	}
	
	/**
	 * Dieser Test speichert eine valide Entit�t ein
	 * @throws PferdPersistenceException  wenn ein Fehler w�hrend des Tests auftritt
	 */
	@Test
	public void insertValidEntity() throws PferdPersistenceException {
		log.debug("F�hre insertValidEntity-Test aus..." );
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		//f�ge Pferd ein
		insertPferdAndCheck(testpferd);	
	}
	
	/**
	 * Dieser Test versucht eine Entit�t abzuspeichern, die den Wert null in Feldern hat,
	 *  die nicht null sein d�rfen. Die Datenbank muss eine PferdPersistenceException werfen
	 *  @throws PferdPersistenceException soll geworfen werden
	 */
	@Test(expected = PferdPersistenceException.class)
	public void insertNullArgumentEntityThrowsException() throws PferdPersistenceException {
		log.debug("F�hre insertNullArgumentEntityThrowsException-Test aus..." );
		testpferd = new Pferd(pferdDao.getNewId());
		testpferd.setTherapieart("Hippotherapie");
		testpferd.setRasse("Lippizaner");
		pferdDao.insertPferd(testpferd);
	}
	
	/**
	 * Dieser Test speichert eine valide Entit�t in die Datenbank
	 * und sucht sie wieder heraus
	 * @throws PferdPersistenceException wenn ein Fehler w�hrend des Tests auftritt
	 */
	@Test
	public void insertAndRetrieveValidEntity() throws PferdPersistenceException {
		log.debug("F�hre insertAndRetrieveValidEntity-Test aus..." );
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		Pferd testpferd2;
		//f�ge Pferd ein
		insertPferdAndCheck(testpferd);
		//nach dem Pferd wird gesucht, es wird zur�ckgegeben
		testpferd2 = pferdDao.getPferd(testpferd);
		//die zwei Pferde m�ssen gleich sein
		assertThat(testpferd2, is(testpferd));
	}
	
	/**
	 * Dieser Test versucht ein nicht verf�gbares Pferd zur�ckzugeben.
	 * Es muss eine Exception geworfen werden
	 * @throws PferdPersistenceException muss geworfen werden, da
	 * falscher Programmablauf
	 */
	@Test(expected = PferdPersistenceException.class)
	public void retrieveInvalidEntity() throws PferdPersistenceException {
		log.debug("F�hre retrieveInvalidEntity-Test aus..." );
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		//nach dem Pferd wird gesucht
		testpferd2 = pferdDao.getPferd(testpferd);

	}
	
	/**
	 * Dieser Test speichert eine valide Entit�t in die Datenbank
	 * und aktualisiert sie mit neuen Werten
	 * @throws PferdPersistenceException 
	 * @throws Exception wenn ein Fehler w�hrend des Tests auftritt
	 */
	@Test
	public void insertAndUpdateValidEntity() throws PferdPersistenceException {
		log.debug("F�hre insertAndUpdateValidEntity-Test aus..." );
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		Pferd testpferd2 =  new Pferd(pferdDao.getNewId(), "Lizz", "/profil.jpg",
				10.2, "HPV", "Haflinger", true, true);;
		//f�ge Pferd ein
		insertPferdAndCheck(testpferd);
		//das Pferd wird aktualisiert
		testpferd.setTherapieart("HPV");
		testpferd.setFoto("/profil.jpg");
		testpferd.setName("Lizz");
		testpferd.setDeleted(true);
		pferdDao.updatePferd(testpferd);
		//hole Pferd wieder heraus
		testpferd = pferdDao.getPferd(testpferd);
		//die zwei Pferde m�ssen gleich sein
		assertThat(testpferd2, is(testpferd));
	}
	
	/**
	 * Dieser Test speichert eine valide Entit�t in die Datenbank
	 * und aktualisiert sie mit unzul�ssigen Werten, es wird eine PferdPersistenceException
	 * geworfen
	 * @throws PferdPersistenceException wenn ein Fehler w�hrend des Tests auftritt,
	 * muss geworfen werden
	 */
	@Test(expected = PferdPersistenceException.class)
	public void insertAndUpdateInvalidEntity() throws PferdPersistenceException {
		log.debug("F�hre insertAndUpdateValidEntity-Test aus..." );
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		//f�ge Pferd ein
		insertPferdAndCheck(testpferd);
		//das Pferd wird mit ung�ltigen Werten aktualisiert
		testpferd.setTherapieart("BlahBlah");
		testpferd.setName(null);
		pferdDao.updatePferd(testpferd);
	}

	
	/**
	 * Dieser Test speichert eine valide Entit�t in die Datenbank und
	 * l�scht sie wieder heraus
	 * @throws PferdPersistenceException wenn ein Fehler w�hrend des Tests auftritt
	 */
	@Test
	public void insertAndDeleteValidEntity() throws PferdPersistenceException  {
		log.debug("F�hre insertAndDeleteValidEntity-Test aus..." );
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		//f�ge Pferd ein
		insertPferdAndCheck(testpferd);
		//das Pferd wird gel�scht
		pferdDao.deletePferd(testpferd);
		//es darf nicht mehr enthalten sein
		pferde = pferdDao.findAll();
		assertThat( pferde.contains(testpferd), is(false));
	}
	
	/**
	 * Dieser Test speichert zwei valide Entit�t in die Datenbank und
	 * l�scht sie wieder heraus, versucht aber auch eine Entit�t zu l�schen,
	 * die nicht abgespeichert ist. Dadurch soll aber kein Fehler in der
	 * Datenstruktur entstehen
	 * @throws PferdPersistenceException wenn ein Fehler w�hrend des Tests auftritt
	 */
	@Test
	public void insertAndDeleteValidEntityAndDeleteNotAvailableEntity() throws PferdPersistenceException  {
		log.debug("F�hre insertAndDeleteValidEntityAndDeleteNotAvailableEntity-Test aus..." );
		//Erstelle und f�ge Pferde ein
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		insertPferdAndCheck(testpferd);
		testpferd2 = new Pferd(pferdDao.getNewId(), "Dolly", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		insertPferdAndCheck(testpferd2);
		testpferd3 = new Pferd(pferdDao.getNewId(), "Albert", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		//Pferde werden gel�scht
		pferdDao.deletePferd(testpferd);
		pferdDao.deletePferd(testpferd2);
		pferdDao.deletePferd(testpferd3);
		//d�rfen nicht mehr enthalten sein
		pferde = pferdDao.findAll();
		assertThat( pferde.contains(testpferd2), is(false));
		assertThat( pferde.contains(testpferd3), is(false));
		assertThat( pferde.contains(testpferd), is(false));

	}
	
	/**
	 * Dieser Test speichert ein paar valide Entit�ten ein.
	 * Dann wird eine Liste mit allen abgespeicherten Pferden
	 * zur�ckgegeben, die alle zuvor eingef�gten Pferde enthalten muss.
	 * @throws PferdPersistenceException  wenn ein Fehler w�hrend des Tests auftritt
	 */
	@Test
	public void findAllEntities() throws PferdPersistenceException {
		log.debug("F�hre findAllEntities-Test aus..." );
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		//f�ge Pferd ein
		insertPferdAndCheck(testpferd);
		testpferd2 = new Pferd(pferdDao.getNewId(), "Albert", "/bild.jpg",
				9.5, "HPR", "", true, false);
		//f�ge Pferd ein
		insertPferdAndCheck(testpferd2);	
		testpferd3 = new Pferd(pferdDao.getNewId(), "Fred", "/bild.jpg",
				4, "Hippotherapie", "Schimmel", true, false);
		//f�ge Pferd ein
		insertPferdAndCheck(testpferd3);	
		pferde = pferdDao.findAll();
		assertThat(pferde.contains(testpferd), is(true));
		assertThat(pferde.contains(testpferd2), is(true));
		assertThat(pferde.contains(testpferd3), is(true));

	}
	
	/**
	 * Dieser Test speichert ein paar valide Entit�ten ein.
	 * Dann wird eine Liste mit allen abgespeicherten und nicht gel�schten Pferden
	 * zur�ckgegeben, die alle zuvor eingef�gten und nicht als gel�scht markierten Pferde enthalten muss.
	 * @throws PferdPersistenceException  wenn ein Fehler w�hrend des Tests auftritt
	 */
	@Test
	public void findAllUndeletedEntities() throws PferdPersistenceException {
		log.debug("F�hre findAllUndeletedEntities-Test aus..." );
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		//f�ge Pferd ein
		insertPferdAndCheck(testpferd);
		testpferd2 = new Pferd(pferdDao.getNewId(), "Albert", "/bild.jpg",
				9.5, "HPR", "", true, true);
		//f�ge Pferd ein
		insertPferdAndCheck(testpferd2);	
		testpferd3 = new Pferd(pferdDao.getNewId(), "Fred", "/bild.jpg",
				4, "Hippotherapie", "Schimmel", true, false);
		//f�ge Pferd ein
		insertPferdAndCheck(testpferd3);	
		pferde = pferdDao.findAllUndeleted();
		assertThat(pferde.contains(testpferd), is(true));
		assertThat(pferde.contains(testpferd2), is(false));
		assertThat(pferde.contains(testpferd3), is(true));

	}
	
	/**
	 * Dieser Test speichert ein paar Entit�ten in die Datenbank und
	 * versucht sie �ber eine Suchanfrage �ber Name, Rasse und Kinderfreundlichkeit herauszubekommen
	 * @throws PferdPersistenceException wenn ein Fehler w�hrend des Tests auftritt
	 */
	@Test
	public void insertAndRetrieveByNameRaceChildfriendly() throws PferdPersistenceException {
		log.debug("F�hre insertAndRetrieveByNameRaceChildfriendly-Test aus...");
		//erstelle Pferde und f�ge sie ein
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
		SuchPferd sp = new SuchPferd("Rolfi", null, "Schimmel", 0, 0, "TRUE");
		pferde = pferdDao.findBy(sp);
		//Pferde m�ssen enthalten sein
		assertThat(pferde.contains(testpferd), is(true));
		assertThat(pferde.contains(testpferd2), is(true));
		assertThat(pferde.contains(testpferd3), is(false));

		
	}
	/**
	 * Dieser Test speichert ein paar Entit�ten in die Datenbank und
	 * versucht sie �ber eine Suchanfrage �ber Preis und Therapieart herauszubekommen
	 * @throws PferdPersistenceException wenn ein Fehler w�hrend des Tests auftritt
	 */
	@Test
	public void insertAndRetrieveByPriceTherapy() throws PferdPersistenceException {
		log.debug("F�hre insertAndRetrieveByPriceTherapy-Test aus...");
		//erstelle Pferde und f�ge sie ein
		testpferd = new Pferd(pferdDao.getNewId(), "Dodel", "/bild1.jpg",
				4, "HPR", "Schimmel", true, false);
		insertPferdAndCheck(testpferd);
		testpferd2 = new Pferd(pferdDao.getNewId(), "Dodel2", "/bild2.jpg",
				7, "HPR", "Schimmel", true, false);
		insertPferdAndCheck(testpferd2);
		testpferd3 = new Pferd(pferdDao.getNewId(), "Dodel3", "/bild3.jpg",
				10, "HPV", "Schimmel", false, false);
		insertPferdAndCheck(testpferd3);

		//erstelle neues Suchpferd und suche mit jenem nach Pferden
		SuchPferd sp = new SuchPferd(null, "HPR", null, 6, 11, "");
		pferde = pferdDao.findBy(sp);
		//Pferde m�ssen enthalten sein
		assertThat(pferde.contains(testpferd), is(false));
		assertThat(pferde.contains(testpferd2), is(true));
		assertThat(pferde.contains(testpferd3), is(false));

		
	}
}
