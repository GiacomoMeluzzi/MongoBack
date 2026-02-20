package lepackage.mongo.documents;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Materia {
	
	@Id
	private String id;
	private String nome;
	private Utente professore;
	private List<Utente> studenti;
	private List<Indirizzo> indirizzi;
	private List<Appunto> appunti;
	private List<Voti> voti;
	
}	
