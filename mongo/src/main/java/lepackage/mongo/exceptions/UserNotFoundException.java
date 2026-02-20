package lepackage.mongo.exceptions;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -3593461150238289958L;
	
	public UserNotFoundException() {
		super("L'utente non esiste.");
	}

}
