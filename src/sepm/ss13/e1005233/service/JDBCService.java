package sepm.ss13.e1005233.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import sepm.ss13.e1005233.dao.JDBCPferdDAO;
import sepm.ss13.e1005233.dao.JDBCRechnungDAO;
import sepm.ss13.e1005233.dao.PferdDAO;
import sepm.ss13.e1005233.dao.RechnungDAO;
import sepm.ss13.e1005233.domain.*;
import sepm.ss13.e1005233.exceptions.*;

public class JDBCService implements Service {

	private PferdDAO pferdDao;
	private RechnungDAO rechnungDao;
	private static final Logger log = Logger.getLogger(JDBCService.class);
	public JDBCService() {
		try {
			this.pferdDao = new JDBCPferdDAO();
			this.rechnungDao = new JDBCRechnungDAO();
		} catch (SQLException e) {
			log.error("could not create PferdDAO or RechnungDAO");
			log.error("Check if Database is online... Exiting...");
			System.exit(-1);
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
	
	/**
	 * �berpr�ft, ob es sich um eine g�ltige Rechnung handelt
	 * @param r die auf G�ltigkeit zu pr�fende Rechnung
	 * @return gibt true zur�ck wenn die Rechnung g�ltig ist, sonst false
	 */
	public boolean isValid(Rechnung r) {
		return (r.getGesamtpreis() > 0 && r.getGesamtstunden() > 0 &&
				r.getName() != null && !r.getName().isEmpty() &&
				r.getTelefon() >= 1000 && r.getTelefon() < 9223372036854775806L &&
				(r.getZahlungsart().equals("Barzahlung") || r.getZahlungsart().equals("Kreditkarte") ||
						r.getZahlungsart().equals("Ueberweisung")));
	}
	
	
	/**
	 * �berpr�ft, ob es sich um eine g�ltige Buchung handelt
	 * @param b die auf G�ltigkeit zu pr�fende Buchung
	 * @return gibt true zur�ck wenn die Buchung g�ltig ist, sonst false
	 */
	public boolean isValid(Buchung b) {
		return b.getStunden() > 0 && b.getPreis() > 0;
	}
	
	@Override
	public void insertPferd(Pferd p) throws PferdValidationException, PferdPersistenceException {
		log.info("Bereite Service zum Einf�gen vor...");
		if(!isValid(p)) {
			log.error("Pferde Daten inkorrekt!");
			throw new PferdValidationException();
		}
		
		pferdDao.insertPferd(p);
		log.debug("Service zum Einf�gen neuer Pferde erfolgreich beendet!");	

	}

	@Override
	public void insertRechnung(Rechnung r) throws RechnungValidationException, RechnungPersistenceException, BuchungValidationException {
		if(!isValid(r)) {
			log.error("Rechnungsdaten inkorrekt!");
			throw new RechnungValidationException();
		}
		for(Buchung b : r.getBuchungen()) {
			if(!isValid(b)) {
				log.error("Buchungsdaten inkorrekt!");
				throw new BuchungValidationException();
			}
		}
		
		rechnungDao.insertRechnung(r);
		log.debug("Service zum Einf�gen neuer Rechnungen erfolgreich beendet!");

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
		return null;
	}
	
	@Override
	public List<Pferd> findBy(SuchPferd sp) throws SearchValidationException {
		log.info("Bereite Service zum Suchen vor...");
		
		if( (sp.getMaxpreis() < sp.getMinpreis()) && sp.getMaxpreis() < 0 && sp.getMinpreis() < 0 &&
				!(sp.getTherapieart().equals("HPR") || sp.getTherapieart().equals("HPV") ||
						sp.getTherapieart().equals("Hippotherapie"))) {
			log.error("Suchdaten inkorrekt!");
			throw new SearchValidationException();
		}
		try {
			return pferdDao.findBy(sp);
		} catch (PferdPersistenceException e) {
			log.error("Persistenz Error w�hrend Suchservice!");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updatePferd(Pferd p) throws PferdValidationException, PferdPersistenceException {
		log.info("Bereite Service zum Aktualisieren vor...");
		if(!isValid(p)) {
			log.error("Pferde Daten inkorrekt!");
			throw new PferdValidationException();
		}
		
		pferdDao.updatePferd(p);
		log.debug("Service zum Aktualisieren erfolgreich beendet!");				
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
		return tl;
	}
	
	@Override
	public List<Pferd> findAllUndeletedPferde() {
		List<Pferd> tl = null;
		try {
			tl = pferdDao.findAllUndeleted();
			log.debug("Service zur R�ckgabe aller nicht gel�schten Pferde erfolgreich beendet!");
		} catch (PferdPersistenceException e) {
			log.error("Persistenz Error w�hrend Service zur R�ckgabe aller nicht gel�schten Pferde!");
			e.printStackTrace();
		}
		return tl;
	}
	

	@Override
	public List<Pferd> getPopularPferde() throws NotEnoughPferdeException {
		log.info("Beginne Suche nach den 3 meistgebuchten Pferden...");
		//alle Pferde die durchgegangen werden m�ssen
		List<Pferd> all = findAllUndeletedPferde();
		int countPferde = all.size();
		if(countPferde < 3)
			throw new NotEnoughPferdeException();
		//ein Hilfsarray um die Pferde nach gebuchten Stunden sortieren zu k�nnen
		PferdVergleich[] sumPferde = new PferdVergleich[countPferde];
		int stundenCounter;
		int pferdIndex = 0;
		for(Pferd p : all) {
			stundenCounter = 0;
			try {
				List<Buchung> buchungen = rechnungDao.getBuchungen(p);
				//Die gebuchten Stunden in jedem Pferd werden zusammengez�hlt
				for(Buchung b : buchungen) {
					stundenCounter += b.getStunden();
				}
				sumPferde[pferdIndex] = new PferdVergleich(p.getId(), stundenCounter);
			} catch (RechnungPersistenceException e) {
				log.error("Fehler w�hrend der R�ckgabe aller Buchungen!");
				e.printStackTrace();
			}
			++pferdIndex;
		}
		//die Pferde werden aufsteigend nach Stunden sortiert
		Arrays.sort(sumPferde);
		all.clear();
		all.add(getPferd(new Pferd(sumPferde[countPferde-1].id)));
		all.add(getPferd(new Pferd(sumPferde[countPferde-2].id)));
		all.add(getPferd(new Pferd(sumPferde[countPferde-3].id)));
		log.info("Suche nach den 3 beliebtesten Pferden abgeschlossen!");
		return all;
	}

	@Override
	public void verteurePferde(List<Pferd> lp) {
		for(Pferd p : lp) {
			double preis = p.getPreis()*1.05;
			p.setPreis(preis);
			try {
				pferdDao.updatePferd(p);
			} catch (PferdPersistenceException e) {
				log.error("Fehler w�hrend der Verteurung der Pferde!");
				e.printStackTrace();
			}
		}

	}

	@Override
	public Rechnung getRechnung(Rechnung r) {
		log.info("Beginne Service zur R�ckgabe der Rechnung");
		try {
			return rechnungDao.getRechnung(r);
		} catch (RechnungPersistenceException e) {
			log.error("Fehler w�hrend der Rechnungsr�ckgabe!");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Rechnung> findAllRechnungen() {
		List<Rechnung> rl = null;
		try {
			rl = rechnungDao.findAll();
			log.debug("Service zur R�ckgabe aller Rechnungen erfolgreich beendet!");
		} catch (RechnungPersistenceException e) {
			log.error("Persistenz Error w�hrend Service zur R�ckgabe aller Rechnungen!");
			e.printStackTrace();
		}
		return rl;
	}

	@Override
	public int getNewId() {
		int id = -1;
		try {
			id = pferdDao.getNewId();
		} catch (PferdPersistenceException e) {
			log.error("Konnte keine ID generieren!");
			e.printStackTrace();
		}
		return id;
	}
	
	public Connection getConnection() throws SQLException {
		return ((JDBCPferdDAO) pferdDao).getConnection();
	}
	
	/**
	 * Eine Hilfsklasse, die die id und die gesamten gebuchten Stunden
	 * eines Pferds abspeichert
	 */
	private class PferdVergleich implements Comparable<PferdVergleich> {
		private int id;
		private int stunden;
		
		public PferdVergleich(int id, int stunden) {
			this.id = id;
			this.stunden = stunden;
		}

		@Override
		public int compareTo(PferdVergleich pv) {
			if(this.stunden < pv.stunden)
				return -1;
			else if(this.stunden > pv.stunden)
				return 1;
			else
				return 0;
		}
	}

	@Override
	public List<Buchung> getBuchungen(Rechnung r) {
		List<Buchung> buchungen = null;
		if(r.getDate() == null) {
			log.debug("Kein Datum in Rechnung!");
			throw new RechnungValidationException();
		}
		try {
			buchungen = rechnungDao.getBuchungen(r);
		} catch (JDBCRechnungPersistenceException e) {
			log.debug("Fehler w�hrend R�ckgabe der Buchungen!");
			e.printStackTrace();
		}
		return buchungen;
	}
}
