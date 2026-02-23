package lepackage.mongo.repositories;

import java.util.List;

import lepackage.mongo.dto.MateriaConIndirizzoDTO;

public interface UtenteMongoRepository {
	
	public List<MateriaConIndirizzoDTO> findMateriaPerProfessore(String utenteId);
	
	public void salvaUtenteIdInMateria(String materiaId, String utenteId);
	
}
