package sepm.ss13.e1005233.service;

/**
 * Eine Exception f�r unzul�ssige Pferddaten
 */
public class PferdValidationException extends IllegalArgumentException {

	private static final long serialVersionUID = -3533842186978175817L;

	public PferdValidationException() {
		super();
	}
	
	public PferdValidationException(String s) {
		super(s);
	}
}