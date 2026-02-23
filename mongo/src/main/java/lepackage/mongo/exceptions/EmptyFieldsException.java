package lepackage.mongo.exceptions;

public class EmptyFieldsException extends Exception {

	private static final long serialVersionUID = -6015751531160444541L;
	
	public EmptyFieldsException(String campo) {
		super("Campo/i " + campo + " non possono essere vuoti o null");
	}

}
