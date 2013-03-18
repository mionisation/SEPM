package sepm.ss13.e1005233.test;

import java.sql.SQLException;
import java.util.*;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import sepm.ss13.e1005233.dao.PferdDAO;
import sepm.ss13.e1005233.domain.Pferd;

/**
 * Schablonenklasse, die spezifiziert, welche Tests für
 * DAO-Implementierungen vorgesehen sind und erfüllt werden
 * müssen
 */
public abstract class PferdDAOTest {
	protected PferdDAO pferdDao;
	protected Pferd testpferd;
	protected List<Pferd> pferde;
	
	public PferdDAOTest() {
		this.pferdDao = null;
		this.testpferd = null;
		this.pferde = new ArrayList<Pferd>();
	}
	
	/**
	 * Setzt das konkrete DAO, das getestet wird
	 * @param pdao das zu testende PferdDAO
	 */
	public void setPferdDAO(PferdDAO pdao) {
		this.pferdDao = pdao;
	}
	
	/**
	 * Dieser Test versucht in der Datenbank eine Entität, die auf Null
	 * referenziert, zu speichern. Das DAO muss eine Exception werfen.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void insertNullEntityThrowsException() {
		testpferd= null;
		pferdDao.insertPferd(testpferd);
	}
	
	/**
	 * Dieser Test speichert eine valide Entität in die Datenbank
	 * @throws SQLException 
	 */
	@Test
	public void insertValidEntity() throws SQLException {
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		//das Pferd darf nicht enthalten sein
		pferde = pferdDao.findAll();
		assertThat( pferde.contains(testpferd), is(false));
		pferdDao.insertPferd(testpferd);
		//das Pferd muss enthalten sein
		pferdDao.findAll();
		assertThat( pferde.contains(testpferd), is(true));	}
	
	/**
	 * Dieser Test versucht eine Entität abzuspeichern, die den Wert null in Feldern hat,
	 *  die nicht null sein dürfen. Die Datenbank muss eine Exception werfen
	 *  @throws SQLException
	 */
	@Test
	public void insertNullArgumentEntity() throws SQLException {
		testpferd = new Pferd(pferdDao.getNewId());
		testpferd.setTherapieart("Hippotherapie");
		testpferd.setRasse("Lippizaner");
		pferdDao.insertPferd(testpferd);
	}
	
	/**
	 * Dieser Test speichert eine valide Entität in die Datenbank
	 * und sucht sie wieder heraus
	 * @throws SQLException
	 */
	@Test
	public void insertAndRetrieveValidEntity() throws SQLException {
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		Pferd testpferd2;
		//das Pferd darf nicht enthalten sein
		pferde = pferdDao.findAll();
		assertThat( pferde.contains(testpferd), is(false));
		pferdDao.insertPferd(testpferd);
		//das Pferd muss enthalten sein
		pferdDao.findAll();
		assertThat( pferde.contains(testpferd), is(true));
		//nach dem Pferd wird gesucht, es wird zurückgegeben
		testpferd2 = pferdDao.getPferd(testpferd);
		//die zwei Pferde müssen gleich sein
		assertThat(testpferd2, is(testpferd));
	}
	
	/**
	 * Dieser Test speichert eine valide Entität in die Datenbank
	 * und aktualisiert sie mit neuen Werten
	 * @throws SQLException
	 */
	@Test
	public void insertAndUpdateValidEntity() throws SQLException {
		testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg",
				10.2, "HPR", "Haflinger", true, false);
		Pferd testpferd2 =  new Pferd(pferdDao.getNewId(), "Lizz", "/profil.jpg",
				10.2, "HPV", "Haflinger", true, true);;
		//das Pferd darf nicht enthalten sein
		pferde = pferdDao.findAll();
		assertThat( pferde.contains(testpferd), is(false));
		pferdDao.insertPferd(testpferd);
		//das Pferd muss enthalten sein
		pferdDao.findAll();
		assertThat( pferde.contains(testpferd), is(true));
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
	 * @throws SQLException
	 */
	@Test
	public void insertAndDeleteValidEntity() throws SQLException {
		//TODO
	}
}
