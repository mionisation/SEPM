package sepm.ss13.e1005233.exceptions;

public class RechnungPersistenceException extends Exception {

	/**
	 * Eine Exception f�r Fehler w�hrend DAO-Prozesse
	 */
	public RechnungPersistenceException() {
		super();
	}

	public RechnungPersistenceException(String s) {
		super(s);
	}
}
