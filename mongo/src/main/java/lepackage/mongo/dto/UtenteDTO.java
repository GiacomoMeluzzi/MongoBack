package lepackage.mongo.dto;

import java.io.Serializable;

import lepackage.mongo.documents.Utente;
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
	private String email;
	private String password;
	@EqualsAndHashCode.Exclude
	private String username;
	@EqualsAndHashCode.Exclude
	private Role role;
	@EqualsAndHashCode.Exclude
	private String[] indirizziIds;
	
	public UtenteDTO(String email, Role role, String username) {
		super();
		this.email = email;
		this.role = role;
		this.username = username;
	}

	public UtenteDTO (Utente retrievedUser) {
		this.username = retrievedUser.getUsername();
		this.email = retrievedUser.getEmail();
		this.role = retrievedUser.getRole();
		this.password = null;
	}
	
	public Utente dtoToUtente () throws Exception {
		try {
		Utente utente = new Utente();
		utente.setEmail(email);
		utente.setUsername(username);
		utente.setPassword(password);
		utente.setIndirizziIds(indirizziIds);
		System.out.println("Utente DTO trasfromato in entità.");
		return utente;
		} catch (NullPointerException e) {
			System.out.println("Conversione UtenteDTO a entità fallita, nullpointer.");
			throw new NullPointerException();
		} catch (Exception e) {
			if (e instanceof NullPointerException) {
				throw e;
			}
			System.out.println("Conversione UtenteDTO a entità fallita, errore generico  "+ e.getMessage());
			throw new Exception();
		}
	}
}

