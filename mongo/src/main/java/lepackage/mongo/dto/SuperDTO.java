package lepackage.mongo.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SuperDTO implements Serializable {
	private static final long serialVersionUID = -8033565176179515292L;
	private String message;
	private Object objectDTO;
	private HttpStatus httpStatus;
}
