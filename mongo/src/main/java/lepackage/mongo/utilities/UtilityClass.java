package lepackage.mongo.utilities;

import java.util.regex.Pattern;

import lepackage.mongo.exceptions.EmptyFieldsException;
import lepackage.mongo.exceptions.IncorrectRegexException;
import lepackage.mongo.varie.Role;

public class UtilityClass {

	public static boolean regexCheckUnoFinoAQuattroCampi(int numberOfRegexArguments, String firstRegexArgs, String secondRegexArgs, String thirdRegexArgs,
			String fourthRegexArgs, String firstInputArgs, String secondInputArgs, String thirdInputArgs,
			String fourthInputArgs)
			throws IncorrectRegexException {
		System.out.println("Chiamata utiliy class.");
		try {
			boolean correctnessChecker = true;
			switch (numberOfRegexArguments) {
			case 4:
				if (fourthRegexArgs == null || fourthInputArgs == null) {
					throw new IncorrectRegexException(fourthInputArgs);
				} else {
					System.out.println("Input regexCheck " + fourthInputArgs);
					if (Pattern.matches(fourthRegexArgs, fourthInputArgs)) {
						System.out.println("Regex check " + fourthInputArgs + ", successo!");
					} else {
						System.out.println("Regex check " + fourthInputArgs + ", errore!");
						throw new IncorrectRegexException(fourthInputArgs);
					}
				}
			case 3:
				if (thirdRegexArgs == null || thirdInputArgs == null) {
					throw new IncorrectRegexException(thirdInputArgs);
				} else {
					System.out.println("Input regexCheck " + thirdInputArgs);
					if (Pattern.matches(thirdRegexArgs, thirdInputArgs)) {
						System.out.println("Regex check " + thirdInputArgs + ", successo!");
					} else {
						System.out.println("Regex check " + thirdInputArgs + ", errore!");
						throw new IncorrectRegexException(thirdInputArgs);
					}
				}
			case 2:
				if (secondRegexArgs == null || secondInputArgs == null) {
					throw new IncorrectRegexException(secondInputArgs);
				} else {
					System.out.println("Input regexCheck " + secondInputArgs);
					if (Pattern.matches(secondRegexArgs, secondInputArgs)) {
						System.out.println("Regex check " + secondInputArgs + ", successo!");
					} else {
						System.out.println("Regex check " + secondInputArgs + ", errore!");
						throw new IncorrectRegexException(secondInputArgs);
					}
				}
			case 1:
				if (firstRegexArgs == null || firstInputArgs == null) {
					throw new IncorrectRegexException(firstInputArgs);
				} else {
					System.out.println("Input regexCheck " + firstInputArgs);
					if (Pattern.matches(firstRegexArgs, firstInputArgs)) {
						System.out.println("Regex check " + firstInputArgs + ", successo!");
					} else {
						System.out.println("Regex check " + firstInputArgs + ", errore!");
						throw new IncorrectRegexException(firstInputArgs);
					}
				}
				break;
			}
			return correctnessChecker;
		} catch (IncorrectRegexException e) {
			throw e;
		}
	}

	public static boolean roleCheck(Role role) throws IncorrectRegexException, EmptyFieldsException {
		if (role == null) {
			System.out.println("Role check, errore, ruolo nullo!");
			throw new EmptyFieldsException("Ruolo nullo.");
		}
		System.out.println("Input rolechek: " + role);
		if (Role.PROFESSORE == role) {
			System.out.println("Role check, successo: PROFESSORE!");
			return true;
		}
		if (Role.STUDENTE == role) {
			System.out.println("Role check, successo: STUDENTE!");
			return true;
		} else {
			System.out.println("Role check, errore!");
			throw new IncorrectRegexException("Ruolo invalido!");
		}
	}

}
