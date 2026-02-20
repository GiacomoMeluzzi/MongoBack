package lepackage.mongo.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Voti {
	
	@Id
	private String id;
	private Utente studente;
	
}
