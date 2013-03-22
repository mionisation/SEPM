package sepm.ss13.e1005233.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import sepm.ss13.e1005233.dao.JDBCPferdDAO;
import sepm.ss13.e1005233.dao.PferdDAO;
import sepm.ss13.e1005233.dao.RechnungDAO;
import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.domain.Rechnung;
import sepm.ss13.e1005233.domain.SuchPferd;
import sepm.ss13.e1005233.exceptions.PferdPersistenceException;
import sepm.ss13.e1005233.exceptions.PferdValidationException;
import sepm.ss13.e1005233.exceptions.RechnungValidationException;

public class JDBCService implements Service {

	private PferdDAO pferdDao;
	private RechnungDAO rechnungDao;
	private static final Logger log = Logger.getLogger(JDBCService.class);
	
	public JDBCService() {
		try {
			this.pferdDao = new JDBCPferdDAO();
		} catch (SQLException e) {
			log.error("could not create PferdDAO");
			e.printStackTrace();
		}
		log.debug("JDBCService erfolgreich erstellt!");
	}
	
	/**
	 * �berpr�ft, ob es sich um ein g�ltiges Pferd handelt
	 * @param p das auf G�ltigkeit zu �berpr�fende Pferd
	 * @return gibt true zur�ck falls das Pferd g�ltig ist, sonst false
	 */
	public boolean isValid(Pferd p) {
		return (p.getName() != null && !p.getName().isEmpty() &&
				p.getFoto() != null && !p.getFoto().isEmpty() &&
				p.getPreis() > 0 && (p.getTherapieart().equals("HPR") ||
				p.getTherapieart().equals("HPV") || p.getTherapieart().equals("Hippotherapie")));
	}
	
	@Override
	public void insertPferd(Pferd p) throws PferdValidationException {
		log.info("Bereite Service zum Einf�gen vor...");
		if(!isValid(p)) {
			log.error("Pferde Daten inkorrekt!");
			throw new PferdValidationException();
		}
		
		try {
			pferdDao.insertPferd(p);
			log.debug("Service zum Einf�gen erfolgreich beendet!");				
		} catch (PferdPersistenceException e) {
			log.error("Persistenz Error w�hrend Einf�geservice!");
			e.printStackTrace();
		}

	}

	@Override
	public void createRechnung(Rechnung r) throws RechnungValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public Pferd getPferd(Pferd p) {
		log.info("Bereite Service zur R�ckgabe vor...");
		try {
			return pferdDao.getPferd(p);
		} catch (PferdPersistenceException e) {
			log.error("Persistenz Error w�hrend R�ckgabeservice!");
			e.printStackTrace();
		}
		//TODO find better solution for exception handling
		return null;
	}
	
	//TODO implementieren
	public List<Pferd> findBy(SuchPferd sp) {
		return null;
	}

	@Override
	public void updatePferd(Pferd p) throws PferdValidationException {
		log.info("Bereite Service zum Aktualisieren vor...");
		if(!isValid(p)) {
			log.error("Pferde Daten inkorrekt!");
			throw new PferdValidationException();
		}
		
		try {
			pferdDao.updatePferd(p);
			log.debug("Service zum Aktualisieren erfolgreich beendet!");				
		} catch (PferdPersistenceException e) {
			log.error("Persistenz Error w�hrend Aktualisierungsservice!");
			e.printStackTrace();
		}
	}

	@Override
	public void deletePferd(Pferd p) {
		log.info("Bereite Service zum L�schen vor...");
		
		try {
			pferdDao.deletePferd(p);
			log.debug("Service zum L�schen erfolgreich beendet!");
		} catch (PferdPersistenceException e) {
			log.error("Persistenz Error w�hrend L�schungsservice!");
			e.printStackTrace();
		}

	}

	@Override
	public List<Pferd> findAllPferde() {
		List<Pferd> tl = null;
		try {
			tl = pferdDao.findAll();
			log.debug("Service zur R�ckgabe aller Pferde erfolgreich beendet!");
		} catch (PferdPersistenceException e) {
			log.error("Persistenz Error w�hrend Service zur R�ckgabe aller Pferde!");
			e.printStackTrace();
		}
		// TODO sch�nere L�sung? 
		return tl;
	}

	@Override
	public List<Pferd> getPopularPferde() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void verteurePferde(List<Pferd> lp) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rechnung getRechnung(Rechnung r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rechnung> findAllRechnungen() {
		// TODO Auto-generated method stub
		return null;
	}

}
