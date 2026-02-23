package lepackage.mongo.implementations;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import lepackage.mongo.documents.UtenteEntity;
import lepackage.mongo.repositories.UtenteRepository;

@Component
public class UtenteRepositoryImpl implements UtenteRepository {

	private final MongoTemplate mongoTemplate;
	private Query query = null;
	//private Update update = null;
	
	public UtenteRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public UtenteEntity findUtenteByEmailAndPassword(String email, String password) {
		query = new Query(Criteria.where("email").is(email).and("password").is(password));
		return mongoTemplate.findOne(query, UtenteEntity.class);
	}
	
	@Override
	public UtenteEntity findUtenteByEmail(String email) {
		query = new Query(Criteria.where("email").is(email));
		return mongoTemplate.findOne(query, UtenteEntity.class);
	}
	
	@Override
	public UtenteEntity findUtenteByUsername(String username) {
		query = new Query(Criteria.where("username").is(username));
		return mongoTemplate.findOne(query, UtenteEntity.class);
	}
	
	@Override
	public void saveUtente(UtenteEntity utenteDaInserire) {
		mongoTemplate.save(utenteDaInserire);	
	}

}
