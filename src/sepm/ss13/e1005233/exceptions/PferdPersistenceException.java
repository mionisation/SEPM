package sepm.ss13.e1005233.exceptions;

public class PferdPersistenceException extends Exception {

	/**
	 * Eine Exception für Fehler während DAO-Prozesse
	 */
	public PferdPersistenceException() {
		super();
	}
	
	public PferdPersistenceException(String s) {
		super(s);
	}
}
