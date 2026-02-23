package lepackage.mongo.exceptions;

public class LoginNeededException extends Exception {

	private static final long serialVersionUID = -2583874660821700387L;
	
	public LoginNeededException() {
		super("Login richiesto per accedere a questa pagina.");
	}

}
