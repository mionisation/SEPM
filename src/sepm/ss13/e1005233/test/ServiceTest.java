package sepm.ss13.e1005233.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.apache.log4j.Logger;
import org.junit.Test;

import sepm.ss13.e1005233.dao.JDBCPferdDAO;
import sepm.ss13.e1005233.exceptions.NotEnoughPferdeException;
import sepm.ss13.e1005233.service.Service;

public class ServiceTest {

	protected Service service;
	private static final Logger log = Logger.getLogger(ServiceTest.class);

	
	public ServiceTest() {
		this.service = null;
	}
	
	public void setService(Service serv) {
		this.service = serv;
	}
	
	//TODO tests getpopular, verteure
	

	/**
	 * Dieser Test überprüft, ob eine valide ID für das nächste
	 * Pferd geliefert wird
	 */
	@Test
	public void getNewIdTest() {
		log.debug("Führe getNewIdTest-Test aus...");
		service.getNewId();
		//assertThat(testRechnung, is(testRechnung2));
	}
	
	/**
	 * Dieser Test überprüft, ob auch wirklich die meistgebuchten Pferde
	 * zurückgeliefert werden
	 */	
	@Test
	public void getPopularPferdeTest() throws NotEnoughPferdeException {
		log.debug("Führe getPopularPferdeTest durch...");
		service.getPopularPferde();

	}

}
