package lepackage.mongo.services;

import static lepackage.mongo.utilities.Constants.LOGIN_REGEX_USR;

import java.util.List;

import org.springframework.stereotype.Service;

import lepackage.mongo.documents.Materia;
import lepackage.mongo.dto.MateriaDTO;
import lepackage.mongo.dto.UtenteDTO;
import lepackage.mongo.exceptions.EmptyFieldsException;
import lepackage.mongo.exceptions.IncorrectRegexException;
import lepackage.mongo.exceptions.NotFoundException;
import lepackage.mongo.exceptions.NotValidException;
import lepackage.mongo.repositories.MateriaRepository;
import lepackage.mongo.utilities.UtilityClass;

@Service
public class MateriaService {

	private MateriaRepository repo;

	public MateriaService(MateriaRepository repo) {
		this.repo = repo;
	}

	public List<MateriaDTO> findMaterieStudente(UtenteDTO utenteInRicercaDTO)
			throws NotFoundException, EmptyFieldsException, NotValidException, IncorrectRegexException {

		UtilityClass.regexCheck(LOGIN_REGEX_USR, utenteInRicercaDTO.getUsername(), "username");
		UtilityClass.roleCheck(utenteInRicercaDTO.getRole());

		if (utenteInRicercaDTO.getIndirizziIds() == null || utenteInRicercaDTO.getIndirizziIds().length == 0) {
			System.out.println("Campo indirizziIds vuoto, impossibile prosegire con fetch materie");
			throw new EmptyFieldsException("IndirizziIds");
		}

		if (utenteInRicercaDTO.getIndirizziIds().length != 1) {
			System.out.println("Uno studente ha più facoltà o nessuna, questo non dovrebbe mai accadre.");
			throw new NotValidException("Uno studente può appartenere a una soka facoltà.");
		}

		System.out.println("Inizio ricerca materie studente " + utenteInRicercaDTO.getUsername());
		List<Materia> materieTrovate = repo.findMaterieStudente(utenteInRicercaDTO.getUsername())
				.orElseThrow(NotFoundException::new);
		if (materieTrovate.size() == 0) {
			System.out.println("Nessuna materia trovata.");
			throw new NotFoundException();
		}

		System.out.println("Materie trovate. " + materieTrovate.size());
		return MateriaDTO.materieToDto(materieTrovate, utenteInRicercaDTO.getRole());
	}

}
