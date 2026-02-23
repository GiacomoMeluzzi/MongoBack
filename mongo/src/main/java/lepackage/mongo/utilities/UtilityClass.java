package lepackage.mongo.utilities;

import java.util.regex.Pattern;

import lepackage.mongo.exceptions.EmptyFieldsException;
import lepackage.mongo.exceptions.IncorrectRegexException;
import lepackage.mongo.varie.Role;

public class UtilityClass {
	
	public static boolean regexCheck (String regexArgs, String inputArgs, String type) throws IncorrectRegexException, EmptyFieldsException {
		System.out.println("Chiamata utiliy class.");
		if(inputArgs == null) {
			throw new EmptyFieldsException(type);
		}
		System.out.println("Input regexCheck " + type + " " + inputArgs);
		if (Pattern.matches(regexArgs, inputArgs)) {
			System.out.println("Regex check " + type + ", successo!");
			return true;
		}
		System.out.println("Regex check " + type + ", errore!");
		throw new IncorrectRegexException(type);
	}
	
	public static boolean roleCheck (Role role) throws IncorrectRegexException, EmptyFieldsException {
		if(role == null) {
			throw new EmptyFieldsException("Ruolo nullo.");
		}
		System.out.println("Input rolechek: " +  role);
		if (Role.PROFESSORE == role) {
			System.out.println("Role check, successo: PROFESSORE!");
			return true;
		}
		if (Role.STUDENTE == role) {
			System.out.println("Role check, successo: STUDENTE!");
			return true;
		}
		else {
		System.out.println("Role check, errore!");
		throw new IncorrectRegexException("Ruolo invalido!");
		}
	}
	
}
