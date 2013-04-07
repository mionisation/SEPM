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
	 * Überprüft, ob es sich um ein gültiges Pferd handelt
	 * @param p das auf Gültigkeit zu überprüfende Pferd
	 * @return gibt true zurück falls das Pferd gültig ist, sonst false
	 */
	public boolean isValid(Pferd p) {
		return (p.getName() != null && !p.getName().isEmpty() &&
				p.getFoto() != null && !p.getFoto().isEmpty() &&
				p.getPreis() > 0 && (p.getTherapieart().equals("HPR") ||
				p.getTherapieart().equals("HPV") || p.getTherapieart().equals("Hippotherapie")));
	}
	
	/**
	 * Überprüft, ob es sich um eine gültige Rechnung handelt
	 * @param r die auf Gültigkeit zu prüfende Rechnung
	 * @return gibt true zurück wenn die Rechnung gültig ist, sonst false
	 */
	public boolean isValid(Rechnung r) {
		return (r.getGesamtpreis() > 0 && r.getGesamtstunden() > 0 &&
				r.getName() != null && !r.getName().isEmpty() &&
				r.getTelefon() >= 1000 && r.getTelefon() < 9223372036854775806L &&
				(r.getZahlungsart().equals("Barzahlung") || r.getZahlungsart().equals("Kreditkarte") ||
						r.getZahlungsart().equals("Ueberweisung")));
	}
	
	
	/**
	 * Überprüft, ob es sich um eine gültige Buchung handelt
	 * @param b die auf Gültigkeit zu prüfende Buchung
	 * @return gibt true zurück wenn die Buchung gültig ist, sonst false
	 */
	public boolean isValid(Buchung b) {
		return b.getStunden() > 0 && b.getPreis() > 0;
	}
	
	@Override
	public void insertPferd(Pferd p) throws PferdValidationException, PferdPersistenceException {
		log.info("Bereite Service zum Einfügen vor...");
		if(!isValid(p)) {
			log.error("Pferde Daten inkorrekt!");
			throw new PferdValidationException();
		}
		
		pferdDao.insertPferd(p);
		log.debug("Service zum Einfügen neuer Pferde erfolgreich beendet!");	

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
		log.debug("Service zum Einfügen neuer Rechnungen erfolgreich beendet!");

	}

	@Override
	public Pferd getPferd(Pferd p) {
		log.info("Bereite Service zur Rückgabe vor...");
		try {
			return pferdDao.getPferd(p);
		} catch (PferdPersistenceException e) {
			log.error("Persistenz Error während Rückgabeservice!");
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
			log.error("Persistenz Error während Suchservice!");
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
		log.info("Bereite Service zum Löschen vor...");
		
		try {
			pferdDao.deletePferd(p);
			log.debug("Service zum Löschen erfolgreich beendet!");
		} catch (PferdPersistenceException e) {
			log.error("Persistenz Error während Löschungsservice!");
			e.printStackTrace();
		}

	}

	@Override
	public List<Pferd> findAllPferde() {
		List<Pferd> tl = null;
		try {
			tl = pferdDao.findAll();
			log.debug("Service zur Rückgabe aller Pferde erfolgreich beendet!");
		} catch (PferdPersistenceException e) {
			log.error("Persistenz Error während Service zur Rückgabe aller Pferde!");
			e.printStackTrace();
		}
		return tl;
	}
	
	@Override
	public List<Pferd> findAllUndeletedPferde() {
		List<Pferd> tl = null;
		try {
			tl = pferdDao.findAllUndeleted();
			log.debug("Service zur Rückgabe aller nicht gelöschten Pferde erfolgreich beendet!");
		} catch (PferdPersistenceException e) {
			log.error("Persistenz Error während Service zur Rückgabe aller nicht gelöschten Pferde!");
			e.printStackTrace();
		}
		return tl;
	}
	

	@Override
	public List<Pferd> getPopularPferde() throws NotEnoughPferdeException {
		log.info("Beginne Suche nach den 3 meistgebuchten Pferden...");
		//alle Pferde die durchgegangen werden müssen
		List<Pferd> all = findAllUndeletedPferde();
		int countPferde = all.size();
		if(countPferde < 3)
			throw new NotEnoughPferdeException();
		//ein Hilfsarray um die Pferde nach gebuchten Stunden sortieren zu können
		PferdVergleich[] sumPferde = new PferdVergleich[countPferde];
		int stundenCounter;
		int pferdIndex = 0;
		for(Pferd p : all) {
			stundenCounter = 0;
			try {
				List<Buchung> buchungen = rechnungDao.getBuchungen(p);
				//Die gebuchten Stunden in jedem Pferd werden zusammengezählt
				for(Buchung b : buchungen) {
					stundenCounter += b.getStunden();
				}
				sumPferde[pferdIndex] = new PferdVergleich(p.getId(), stundenCounter);
			} catch (RechnungPersistenceException e) {
				log.error("Fehler während der Rückgabe aller Buchungen!");
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
				log.error("Fehler während der Verteurung der Pferde!");
				e.printStackTrace();
			}
		}

	}

	@Override
	public Rechnung getRechnung(Rechnung r) {
		log.info("Beginne Service zur Rückgabe der Rechnung");
		try {
			return rechnungDao.getRechnung(r);
		} catch (RechnungPersistenceException e) {
			log.error("Fehler während der Rechnungsrückgabe!");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Rechnung> findAllRechnungen() {
		List<Rechnung> rl = null;
		try {
			rl = rechnungDao.findAll();
			log.debug("Service zur Rückgabe aller Rechnungen erfolgreich beendet!");
		} catch (RechnungPersistenceException e) {
			log.error("Persistenz Error während Service zur Rückgabe aller Rechnungen!");
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
			log.debug("Fehler während Rückgabe der Buchungen!");
			e.printStackTrace();
		}
		return buchungen;
	}
}
