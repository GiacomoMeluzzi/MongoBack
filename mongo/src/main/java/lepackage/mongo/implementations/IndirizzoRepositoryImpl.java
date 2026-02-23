package lepackage.mongo.implementations;

import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import lepackage.mongo.documents.IndirizzoEntity;
import lepackage.mongo.repositories.IndirizzoRepository;

@Component
public class IndirizzoRepositoryImpl implements IndirizzoRepository {

	private final MongoTemplate mongoTemplate;

	public IndirizzoRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Optional<IndirizzoEntity> findIndirizzoByNome(String indirizzoNome) {
        Query query = new Query(
                Criteria.where("nome").is(indirizzoNome)
        );

        return Optional.ofNullable(
                mongoTemplate.findOne(query, IndirizzoEntity.class)
        );
	}

}
