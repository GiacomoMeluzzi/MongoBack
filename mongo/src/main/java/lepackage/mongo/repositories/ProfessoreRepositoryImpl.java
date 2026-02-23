package lepackage.mongo.repositories;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import lepackage.mongo.dto.MateriaConIndirizzoDTO;
import lepackage.mongo.varie.Role;

@Repository
public class ProfessoreRepositoryImpl implements ProfessoreRepository {

	private final MongoTemplate mongoTemplate;
	
	public ProfessoreRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public List<MateriaConIndirizzoDTO> findMateriaPerProfessore(String username) {
	
		Aggregation aggregation = Aggregation.newAggregation(
				
				Aggregation.match(
						Criteria.where("username").is(username)
						.and("role").is(Role.PROFESSORE.toString())
						),
				
				Aggregation.lookup(
						"materie",
						"materieIds",
						"_id",
						"materie"
						),
				Aggregation.unwind("materie"),
				
				Aggregation.lookup(
						"universitari",
						"materie._id",
						"materieIds",
						"studenti"
						),
				
				Aggregation.unwind("studenti"),
				
				Aggregation.match(
						Criteria.where("studenti.role").is(Role.STUDENTE.toString())
						),
				
				Aggregation.lookup(
						"indirizzi",
						"studenti.indirizziIds",
						"_id",
						"indirizzo"
						),
				
				Aggregation.unwind("indirizzo"),
				
				Aggregation.group(
						Fields.from(Fields.field("materiaId", "materie._id"),
				        Fields.field("indirizzoId", "indirizzo._id")))
				        .first("materie.nome").as("materia")
				        .first("indirizzo.nome").as("indirizzo")
				        .push("studenti.username").as("studenti"),
		    
		        Aggregation.project("materia", "indirizzo", "studenti")
				
				);
		
		AggregationResults<MateriaConIndirizzoDTO> results = 
				mongoTemplate.aggregate(aggregation, "universitari", MateriaConIndirizzoDTO.class);
		
		return results.getMappedResults();
	}

}
