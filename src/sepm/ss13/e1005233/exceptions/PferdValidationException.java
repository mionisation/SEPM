package sepm.ss13.e1005233.exceptions;

/**
 * Eine Exception f�r unzul�ssige Pferddaten
 */
public class PferdValidationException extends IllegalArgumentException {

	public PferdValidationException() {
		super();
	}
	
	public PferdValidationException(String s) {
		super(s);
	}
}