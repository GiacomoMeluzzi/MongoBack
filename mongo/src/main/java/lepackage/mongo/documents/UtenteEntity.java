package lepackage.mongo.documents;

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
public class UtenteEntity {
	@Id
	private String id;
	private String email;
	private String username;
	private String password;
	private Role role;
	private String[] indirizziIds;
	private String[] materieIds;

}
