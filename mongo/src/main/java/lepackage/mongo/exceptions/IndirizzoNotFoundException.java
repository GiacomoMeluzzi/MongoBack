package lepackage.mongo.exceptions;

public class IndirizzoNotFoundException extends Exception {

	private static final long serialVersionUID = 3422181541360597418L;

	public IndirizzoNotFoundException() {
		super("L'indirizzo non esiste.");
	}

}
