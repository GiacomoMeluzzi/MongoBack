package lepackage.mongo.exceptions;

public class IncorrectRegexException extends Exception {

	private static final long serialVersionUID = -7786152211172061272L;
	
	public IncorrectRegexException(String type) {
		super("Formato " + type +" sbagliato.");
	}

}
