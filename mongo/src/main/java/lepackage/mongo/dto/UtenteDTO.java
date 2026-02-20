package lepackage.mongo.dto;

import java.io.Serializable;

import lepackage.mongo.varie.Role;
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
public class UtenteDTO implements Serializable {
	
	private static final long serialVersionUID = -4459122156497069441L;
	private String username;
	private String password;
	@EqualsAndHashCode.Exclude
	private Role role;
	
	public UtenteDTO(String username, Role role) {
		super();
		this.username = username;
		this.role = role;
	}
	
}

