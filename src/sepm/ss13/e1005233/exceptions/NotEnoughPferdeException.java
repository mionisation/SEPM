package sepm.ss13.e1005233.exceptions;

public class NotEnoughPferdeException extends Exception {

	/**
	 * Eine Exception, die geworfen werde muss wenn zu wenige
	 * Pferde vorhanden sind
	 */
	public NotEnoughPferdeException() {
	}

	public NotEnoughPferdeException(String arg0) {
		super(arg0);
	}
}
