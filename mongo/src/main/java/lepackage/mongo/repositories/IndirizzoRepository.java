package lepackage.mongo.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import lepackage.mongo.documents.IndirizzoEntity;

@Repository
public interface IndirizzoRepository {

	public Optional<IndirizzoEntity> findIndirizzoById(String indirizzoNome);
	
}
