package lepackage.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import lepackage.mongo.documents.UtenteEntity;

@Repository
public interface UtenteRepository extends MongoRepository<UtenteEntity, String>, UtenteMongoRepository {
	
	@Query("{'email': ?0, 'password': ?1}")
	public UtenteEntity findUtenteByEmailAndPassword(String email, String password);
	
	@Query("{'email': ?0}")
	public UtenteEntity findUtenteByEmail(String email);
	
	@Query("{'username': ?0}")
	public UtenteEntity findUtenteByUsername(String username);
	
}
