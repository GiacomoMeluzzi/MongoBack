package lepackage.mongo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import lepackage.mongo.documents.Materia;

@Repository
public interface MateriaRepository extends MongoRepository<Materia, String>{

	@Query("{'utentiIds': ?0}")
	public Optional<List<Materia>> findMaterieStudente(String utenteId);	
}
