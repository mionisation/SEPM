package sepm.ss13.e1005233.test;

import org.apache.log4j.Logger;

import sepm.ss13.e1005233.dao.RechnungDAO;

/**
 * Schablonenklasse, die spezifiziert, welche Tests für
 * DAO-Implementierungen vorgesehen sind und erfüllt werden
 * müssen
 */
public abstract class RechnungDAOTest {
	protected RechnungDAO rechnungDao;
	private static final Logger log = Logger.getLogger(RechnungDAOTest.class);
	
	public RechnungDAOTest() {
		this.rechnungDao = null;
	}
	
	/**
	 * Setzt das konkrete DAO, das getestet wird
	 * @param rdao das zu testende RechnungDAO
	 */
	protected void setRechnungDAO(RechnungDAO rdao) {
		this.rechnungDao = rdao;
	}

}
