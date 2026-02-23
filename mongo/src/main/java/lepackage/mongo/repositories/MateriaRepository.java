package lepackage.mongo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import lepackage.mongo.documents.MateriaEntity;

@Repository
public interface MateriaRepository extends MongoRepository<MateriaEntity, String>{

	@Query("{'utentiIds': ?0}")
	public Optional<List<MateriaEntity>> findMaterieStudente(String utenteId);	
}
