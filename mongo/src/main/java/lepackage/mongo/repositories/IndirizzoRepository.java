package lepackage.mongo.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import lepackage.mongo.documents.Indirizzo;

@Repository
public interface IndirizzoRepository extends MongoRepository<Indirizzo, String>{

	public Optional<Indirizzo> findByNome(String nome);
	
}
