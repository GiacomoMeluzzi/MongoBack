package lepackage.mongo.utilities;

public class Constants {
	
	public final static String LOGIN_REGEX_MAIL = "^[A-Za-z0-9._-]{1,30}@[A-Za-z0-9]{1,10}\\.[A-Za-z0-9]{1,4}$";
	public final static String LOGIN_REGEX_PSW  = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d!?.\\-_]{1,30}$";
	public final static String LOGIN_REGEX_USR  = "^[a-zA-z0-9_.]{4,20}$";
}
