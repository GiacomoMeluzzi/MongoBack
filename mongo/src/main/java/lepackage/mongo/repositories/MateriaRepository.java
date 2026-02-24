package lepackage.mongo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lepackage.mongo.documents.MateriaEntity;
import lepackage.mongo.dto.MateriaStudentiDTO;
import lepackage.mongo.exceptions.EmptyFieldsException;
import lepackage.mongo.exceptions.UserNotFoundException;

@Repository
public interface MateriaRepository{

	public Optional<List<MateriaEntity>> findMaterieStudente(String utenteUsername) throws UserNotFoundException, EmptyFieldsException;	
	
	public boolean checkMaterieExist(String[] nomiMaterieDaControllare) throws EmptyFieldsException;
	
}
