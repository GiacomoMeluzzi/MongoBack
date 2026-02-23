package lepackage.mongo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import lepackage.mongo.documents.Materia;

@Repository
public interface MateriaRepository extends MongoRepository<Materia, String>{

	@Query("{ 'indirizziIds': ?0, 'utentiIds': ?1 }")
	public Optional<List<Materia>> findMaterieUnIndirizzo(String indirizzoId, String utenteID);
	
	@Query("{}")
	public Optional<List<Materia>> findMateriePiuIndirizzi(String utenteID, String[] indirizziIds);
	
}
