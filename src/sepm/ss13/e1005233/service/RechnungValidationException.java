package sepm.ss13.e1005233.service;

/**
 * Eine Exception für unzulässige Rechnungsdaten
 */
public class RechnungValidationException extends IllegalArgumentException {

	private static final long serialVersionUID = 2366414410107971476L;

	public RechnungValidationException() {
		super();
	}
	
	public RechnungValidationException(String s) {
		super(s);
	}
}