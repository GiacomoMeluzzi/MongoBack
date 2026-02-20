package lepackage.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import lepackage.mongo.documents.Utente;
import lepackage.mongo.dto.UtenteDTO;

@Repository
public interface UtenteRepository extends MongoRepository<Utente, String>{
	
	public Utente findByUsernameAndPassword(String username, String password);
	
}
