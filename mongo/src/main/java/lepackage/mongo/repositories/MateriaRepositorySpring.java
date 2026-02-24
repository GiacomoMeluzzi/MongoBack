package lepackage.mongo.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import lepackage.mongo.documents.MateriaEntity;
import lepackage.mongo.dto.MateriaStudentiDTO;

@Repository
public interface MateriaRepositorySpring extends MongoRepository<MateriaEntity, String> {

	@Aggregation(pipeline = {
			// faccio il match per professore per username e ruolo (ruolo non è strettamente
			// necessario,
			// ma è meglio se lo faccio per sicurezza)
			"{ $match: { username: ?0, role: 'PROFESSORE' } }",

			// trovo materie per il professore dopo match
			"{ $lookup: { " + "from: 'materie', " + "localField: 'materieIds', " + "foreignField: '_id', "
					+ "as: 'materie' " + "} }",

			// apro le varie materie e cerco studenti che hanno quelle materie
			"{ $unwind: '$materie' }",
			"{ $lookup: { " + "from: 'utenti', " + "let: { materiaId: '$materie._id' }, " + "pipeline: ["
					+ "{ $match: { " + "$expr: { $and: [ " + "{ $eq: ['$role','STUDENTE'] },"
					+ "{ $in: ['$$materiaId','$materieIds'] }" + "] }" + "} }" + "], " + "as: 'materie.studenti' "
					+ "} }",

			// raggruppo per materia
			"{ $project: { " + "materia: '$materie.nome', " + "studenti: '$materie.studenti.username' " + "} }" })
	List<MateriaStudentiDTO> findMateriePerProfessore(String usernameProfessore);
}
