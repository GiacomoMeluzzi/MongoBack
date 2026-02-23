package lepackage.mongo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends Exception {

	private static final long serialVersionUID = 758227355495556602L;

	private String message;
	private Object error;
	
	public BusinessException(String message) {
		super(message);
	}
	
}
