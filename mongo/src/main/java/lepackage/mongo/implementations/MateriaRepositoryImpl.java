package lepackage.mongo.implementations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import lepackage.mongo.documents.MateriaEntity;
import lepackage.mongo.documents.UtenteEntity;
import lepackage.mongo.dto.MateriaStudentiDTO;
import lepackage.mongo.exceptions.EmptyFieldsException;
import lepackage.mongo.exceptions.UserNotFoundException;
import lepackage.mongo.repositories.MateriaRepository;

@Component
public class MateriaRepositoryImpl implements MateriaRepository {

	private final MongoTemplate mongoTemplate;
	private Query query = null;

	public MateriaRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Optional<List<MateriaEntity>> findMaterieStudente(String utenteUsername)
			throws UserNotFoundException, EmptyFieldsException {
		try {
			query = new Query(Criteria.where("username").is(utenteUsername));
			UtenteEntity utenteConMaterie = mongoTemplate.findOne(query, UtenteEntity.class);
			if (utenteConMaterie == null) {
				throw new UserNotFoundException();
			}

			List<String> materiaIds = Arrays.asList(utenteConMaterie.getMaterieIds());
			if (materiaIds == null || materiaIds.size() == 0) {
				throw new EmptyFieldsException("MaterieIds");
			}

			query = new Query(Criteria.where("_id").in(materiaIds));
			List<MateriaEntity> materieDellUtente = mongoTemplate.find(query, MateriaEntity.class);

			return Optional.of(materieDellUtente);

		} catch (UserNotFoundException e) {
			throw e;
		}
	}

	@Override
	public boolean checkMaterieExist(String[] idMaterieDaControllare) throws EmptyFieldsException {
		for (int i = 0; i < idMaterieDaControllare.length; i++) {

			query = new Query(Criteria.where("_id").is(idMaterieDaControllare[i]));
			MateriaEntity materiaTrovataDaControllare = mongoTemplate.findOne(query, MateriaEntity.class);

			if (materiaTrovataDaControllare == null) {
				System.out.println("La materia inserita è vuota.");
				throw new EmptyFieldsException("materia");
			}
		}
		return true;
	}

}
