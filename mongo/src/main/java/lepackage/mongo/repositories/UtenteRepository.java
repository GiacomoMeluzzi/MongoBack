package lepackage.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import lepackage.mongo.documents.Utente;

@Repository
public interface UtenteRepository extends MongoRepository<Utente, String>, ProfessoreRepository {
	
	public Utente findByEmailAndPassword(String email, String password);
	
	public Utente findByEmail(String email);
	
	public Utente findByUsername(String username);
	
}
