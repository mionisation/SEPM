package sepm.ss13.e1005233.exceptions;

/**
 * Eine Exceptionklasse f�r unzul�ssige Suchanfragen
 */
public class SearchValidationException extends IllegalArgumentException {

	public SearchValidationException() {
		super();
	}

	public SearchValidationException(String s) {
		super(s);
	}

}
