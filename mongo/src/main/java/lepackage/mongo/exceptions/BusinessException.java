package lepackage.mongo.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends Exception {

	private static final long serialVersionUID = 758227355495556602L;
	
	private String message;
	private Object error;
	
	public BusinessException (String message) {
		super(message);
	}
	
	public BusinessException (String message, Object error) {
		super(message);
		this.error = error;
	}

}
