package lepackage.mongo.services;

import static lepackage.mongo.utilities.Constants.*;

import java.util.List;

import org.springframework.stereotype.Service;

import lepackage.mongo.documents.MateriaEntity;
import lepackage.mongo.documents.UtenteEntity;
import lepackage.mongo.dto.MateriaDTO;
import lepackage.mongo.dto.MateriaStudentiDTO;
import lepackage.mongo.dto.UtenteDTO;
import lepackage.mongo.exceptions.BusinessException;
import lepackage.mongo.exceptions.EmptyFieldsException;
import lepackage.mongo.exceptions.IncorrectRegexException;
import lepackage.mongo.exceptions.NotFoundException;
import lepackage.mongo.exceptions.NotValidException;
import lepackage.mongo.exceptions.UserNotFoundException;
import lepackage.mongo.repositories.MateriaRepository;
import lepackage.mongo.repositories.MateriaRepositorySpring;
import lepackage.mongo.repositories.UtenteRepository;
import lepackage.mongo.utilities.UtilityClass;

@Service
public class MateriaService {

	private MateriaRepository materiaRepo;
	private MateriaRepositorySpring springRepo;
	private UtenteService utenteService;

	public MateriaService(MateriaRepository materiaRepo, MateriaRepositorySpring springRepo,
			UtenteService utenteService) {
		this.materiaRepo = materiaRepo;
		this.springRepo = springRepo;
		this.utenteService = utenteService;
	}

	public List<MateriaDTO> findMaterieStudente(UtenteDTO utenteInRicercaDTO) throws BusinessException {
		try {
			UtilityClass.regexCheckUnoFinoAQuattroCampi(ONE_REGEX_ARGUMENT, LOGIN_REGEX_USR, null, null, null,
					utenteInRicercaDTO.getUsername(), null, null, null);
			UtenteEntity utenteInDatabase = utenteService.trovaUtenteInDB(utenteInRicercaDTO);
			UtilityClass.roleCheck(utenteInDatabase.getRole());

			if (utenteInDatabase.getIndirizziIds() == null || utenteInRicercaDTO.getIndirizziIds().length == 0) {
				System.out.println("Campo indirizziIds vuoto, impossibile prosegire con fetch materie");
				throw new EmptyFieldsException("IndirizziIds");
			}

			if (utenteInDatabase.getIndirizziIds().length != 1) {
				System.out.println("Uno studente ha più facoltà o nessuna, questo non dovrebbe mai accadre.");
				throw new NotValidException("Uno studente può appartenere a una soka facoltà.");
			}

			System.out.println("Inizio ricerca materie studente " + utenteInDatabase.getUsername());
			List<MateriaEntity> materieTrovate = materiaRepo.findMaterieStudente(utenteInDatabase.getUsername())
					.orElseThrow(NotFoundException::new);
			if (materieTrovate.size() == 0) {
				System.out.println("Nessuna materia trovata.");
				throw new NotFoundException();
			}

			System.out.println("Materie trovate. " + materieTrovate.size());
			return MateriaDTO.listaMaterieToListaDto(materieTrovate, utenteInRicercaDTO.getRole());
		} catch (EmptyFieldsException | NotFoundException | NotValidException | IncorrectRegexException
				| UserNotFoundException e) {
			System.out.println("Errore in findMateriaStudente " + e.getMessage());
			throw new BusinessException("Errore in findMateriaStudente " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Errore generico in findMateriaStudente " + e.getMessage());
			throw e;
		}
	}

	public List<MateriaStudentiDTO> findMateriePerProfessore(String usernameProfessore) {
		try {
			return springRepo.findMateriePerProfessore(usernameProfessore);
		} catch (Exception e) {
			throw e;
		}
	}

}
