package lepackage.mongo.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import lepackage.mongo.documents.MateriaEntity;
import lepackage.mongo.documents.UtenteEntity;
import lepackage.mongo.dto.MateriaStudentiDTO;

@Repository
public interface UtenteRepositorySpring extends MongoRepository<UtenteEntity, String> {

	@Aggregation(pipeline = {
			"{ $match: { username: ?0, role: 'PROFESSORE' } }",
			"{ $lookup: { " + "from: 'materie', " + "localField: 'materieIds', " + "foreignField: '_id', "
					+ "as: 'materieList' " + "} }",
			"{ $unwind: '$materieList' }",
			
			"{ $lookup: { " + "from: 'utenti', " + "let: { materiaId: '$materieList._id' }, " + "pipeline: [ "
					+ "{ $match: { " + "$expr: { $and: [ " + "{ $eq: ['$role', 'STUDENTE'] }, "
					+ "{ $in: ['$$materiaId', '$materieIds'] } " + "] } " + "} }, "
					+ "{ $project: { _id: 0, username: 1 } } " + "], " + "as: 'studentiList' " + "} }",
					
			"{ $project: { " + "_id: 0, " + "materia: '$materieList.nome', " + "studenti: '$studentiList.username' "
					+ "} }"

	})
	List<MateriaStudentiDTO> findMateriePerProfessore(String usernameProfessore);
}
