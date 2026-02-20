package lepackage.mongo.documents;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lepackage.mongo.varie.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection ="universitari")
public class Utente {
	@Id
	private String id;
	private String username;
	private String email;
	private String password;
	private Role role;
	private List<Indirizzo> corsi;
	private List<Appunto> appunti;

}
