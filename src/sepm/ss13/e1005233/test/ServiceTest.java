package sepm.ss13.e1005233.test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


import org.apache.log4j.Logger;
import org.junit.Test;

import sepm.ss13.e1005233.dao.JDBCRechnungDAO;
import sepm.ss13.e1005233.dao.RechnungDAO;
import sepm.ss13.e1005233.domain.Buchung;
import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.exceptions.BuchungValidationException;
import sepm.ss13.e1005233.exceptions.NotEnoughPferdeException;
import sepm.ss13.e1005233.exceptions.PferdPersistenceException;
import sepm.ss13.e1005233.exceptions.PferdValidationException;
import sepm.ss13.e1005233.exceptions.RechnungPersistenceException;
import sepm.ss13.e1005233.exceptions.RechnungValidationException;
import sepm.ss13.e1005233.service.Service;

public class ServiceTest {

	protected Service service;
	private static final Logger log = Logger.getLogger(ServiceTest.class);
	private List<Pferd> pferde;
	public ServiceTest() {
		this.service = null;
		new ArrayList<Buchung>();
	}
	
	public void setService(Service serv) {
		this.service = serv;
	}
	
	/**
	 * Dieser Test überprüft, ob eine valide ID für das nächste
	 * Pferd geliefert wird
	 */
	@Test
	public void getNewIdTest() {
		log.debug("Führe getNewIdTest-Test aus...");
		int id = service.getNewId();
		log.info("Finde alle Pferde, speichere deren ID...");
		pferde = service.findAllPferde();
		int[] allID = new int[pferde.size() + 1];
		Iterator<Pferd> nextPferd = pferde.iterator();
		//alle alten und die neue Id werden in ein Array gespeichert
		for(int i = 0; i < pferde.size(); i++) {
			int tempID = nextPferd.next().getId();
			assertThat(id, is(not(tempID)));
			allID[i] = tempID;
		}
		allID[pferde.size()] = id;
		//das Array wird sortiert, die neue ID muss der letzte Eintrag sein
		Arrays.sort(allID);
		assertThat(allID[pferde.size()], is(id));
		log.debug("getNewIdTest beendet!");
	}
	
	/**
	 * Dieser Test überprüft, ob auch wirklich die meistgebuchten Pferde
	 * zurückgeliefert werden
	 * @throws PferdPersistenceException 
	 * @throws PferdValidationException 
	 * @throws BuchungValidationException 
	 * @throws RechnungPersistenceException 
	 * @throws RechnungValidationException 
	 * @throws SQLException 
	 */	
	@Test
	public void getPopularPferdeTest() throws NotEnoughPferdeException, PferdValidationException, PferdPersistenceException, RechnungValidationException, RechnungPersistenceException, BuchungValidationException, SQLException {
		log.debug("Führe getPopularPferdeTest durch...");
		
		int[] stunden = {0, 0, 0};
		int[] ids = {0, 0, 0};
		RechnungDAO rdao = new JDBCRechnungDAO();		
		pferde = service.findAllUndeletedPferde();
		for(Pferd p : pferde) {
			int pferdStunden = 0;
			for(Buchung b : rdao.getBuchungen(p)) {
				pferdStunden += b.getStunden();
			}
			if(stunden[0] < pferdStunden) {
				stunden[0] = pferdStunden;
				ids[0] = p.getId();
			}
			else if(stunden[1] <= pferdStunden) {
				stunden[1] = pferdStunden;
				ids[1] = p.getId();
			}
			else if(stunden[2] < pferdStunden) {
				stunden[2] = pferdStunden;
				ids[2] = p.getId();
			}			
		}
		pferde = service.getPopularPferde();
		assertThat(pferde.get(0).getId(), is( ids[0]));
	}
	
	/**
	 * Dieser Test überprüft, ob die Pferde auch wirklich um 5% verteuert
	 * werden
	 */
	@Test
	public void verteurePferdeTest() {
		log.debug("Führe getPopularPferdeTest durch...");
		pferde = service.findAllPferde();
		double[] stundenPreise = new double[pferde.size()];
		int ind = 0;
		
		for(Pferd p : pferde) {
			stundenPreise[ind] = 1.049 * p.getPreis();
			ind++;
		}
		service.verteurePferde(pferde);
		
		pferde = service.findAllPferde();
		double[] newStundenPreise = new double[pferde.size()];
		ind = 0;
		for(Pferd p : pferde) {
			newStundenPreise[ind] = p.getPreis();
			ind++;
		}
		
		Arrays.sort(stundenPreise);
		Arrays.sort(newStundenPreise);
		for(int i = 0; i < pferde.size(); i++) {
			assertThat(stundenPreise[i], is(not(greaterThan(newStundenPreise[i]))));
		}
	}
}
