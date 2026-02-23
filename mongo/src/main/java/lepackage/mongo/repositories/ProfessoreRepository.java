package lepackage.mongo.repositories;

import java.util.List;

import lepackage.mongo.dto.MateriaConIndirizzoDTO;

public interface ProfessoreRepository {
	
	List<MateriaConIndirizzoDTO> findMateriaPerProfessore(String utenteId);
	
}
