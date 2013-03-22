package sepm.ss13.e1005233.exceptions;

/**
 * Eine Exception f�r unzul�ssige Rechnungsdaten
 */
public class RechnungValidationException extends IllegalArgumentException {

	public RechnungValidationException() {
		super();
	}
	
	public RechnungValidationException(String s) {
		super(s);
	}
}