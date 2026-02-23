package lepackage.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import lepackage.mongo.documents.Utente;

@Repository
public interface UtenteRepository extends MongoRepository<Utente, String>, UtenteMongoRepository {
	
	@Query("{'email': ?0, 'password': ?1}")
	public Utente findUtenteByEmailAndPassword(String email, String password);
	
	@Query("{'email': ?0}")
	public Utente findUtenteByEmail(String email);
	
	@Query("{'username': ?0}")
	public Utente findUtenteByUsername(String username);
	
}
