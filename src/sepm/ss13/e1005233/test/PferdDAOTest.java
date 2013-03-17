package sepm.ss13.e1005233.test;

import java.sql.SQLException;
import java.util.*;

import org.junit.Test;

import sepm.ss13.e1005233.dao.PferdDAO;
import sepm.ss13.e1005233.domain.Pferd;

/**
 * Schablonenklasse, die spezifiziert, welche Tests f�r
 * DAO-Implementierungen vorgesehen sind und erf�llt werden
 * m�ssen
 */
public abstract class PferdDAOTest {
	protected PferdDAO pferdDao;
	protected Pferd testpferd;
	protected List<Pferd> pferde;
	
	public PferdDAOTest(PferdDAO pferdDao) {
		this.pferdDao = pferdDao;
		this.testpferd = null;
		this.pferde = new ArrayList<Pferd>();
	}
	
	/**
	 * Dieser Test versucht in der Datenbank eine Entit�t, die auf Null
	 * referenziert, zu speichern. Das DAO muss eine Exception werfen.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void insertNullEntityThrowsException() {
		this.testpferd= null;
		pferdDao.insertPferd(testpferd);
	}
	
	/**
	 * Dieser Test speichert eine valide Entit�t in die Datenbank
	 * @throws SQLException 
	 */
	@Test
	public void insertValidEntity() throws SQLException {
		this.testpferd = new Pferd(pferdDao.getNewId(), "Marie", "/bild.jpg", 10.2, "PVR", "Haflinger", true, false);
		this.pferde = pferdDao.findAll();
		assert pferde.contains(testpferd) == false;
		pferdDao.insertPferd(testpferd);
		pferdDao.findAll();
		assert pferde.contains(testpferd) == true;
	}
}
