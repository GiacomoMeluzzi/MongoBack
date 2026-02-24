package lepackage.mongo.services;

import static lepackage.mongo.utilities.Constants.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import lepackage.mongo.documents.MateriaEntity;
import lepackage.mongo.documents.UtenteEntity;
import lepackage.mongo.dto.MateriaStudentiDTO;
import lepackage.mongo.dto.SuperDTO;
import lepackage.mongo.dto.UtenteDTO;
import lepackage.mongo.exceptions.BusinessException;
import lepackage.mongo.exceptions.EmptyFieldsException;
import lepackage.mongo.exceptions.IncorrectRegexException;
import lepackage.mongo.exceptions.NotFoundException;
import lepackage.mongo.exceptions.NotValidException;
import lepackage.mongo.exceptions.UserNotFoundException;
import lepackage.mongo.implementations.MateriaRepositoryImpl;
import lepackage.mongo.implementations.UtenteRepositoryImpl;
import lepackage.mongo.repositories.UtenteRepositorySpring;
import lepackage.mongo.utilities.UtilityClass;
import lepackage.mongo.varie.Role;

@Service
public class UtenteService {

	private UtenteRepositoryImpl utenteRepo;
	private MateriaRepositoryImpl materiaRepo;
	private IndirizzoService indirizzoService;
	private UtenteRepositorySpring utenteSpringRepo;

	public UtenteService(UtenteRepositoryImpl utenteRepo, IndirizzoService indirizzoService,
			MateriaRepositoryImpl materiaRepo, UtenteRepositorySpring utenteSpringRepo) {
		this.utenteRepo = utenteRepo;
		this.indirizzoService = indirizzoService;
		this.materiaRepo = materiaRepo;
		this.utenteSpringRepo = utenteSpringRepo;
	}

	public UtenteDTO findByUsernameAndPassword(UtenteDTO credenzialiUtenteDaRegistrareDTO) throws Exception {
		try {
			UtilityClass.regexCheckUnoFinoAQuattroCampi(TWO_REGEX_ARGUMENTS, LOGIN_REGEX_MAIL, LOGIN_REGEX_PSW, null,
					null, credenzialiUtenteDaRegistrareDTO.getEmail(), credenzialiUtenteDaRegistrareDTO.getPassword(),
					null, null);
			System.out.println("Regex a posto in login, vado a DB.");

			UtenteEntity utenteDaDB = utenteRepo.findUtenteByEmailAndPassword(
					credenzialiUtenteDaRegistrareDTO.getEmail(), credenzialiUtenteDaRegistrareDTO.getPassword());
			System.out.println("UtenteService ha recuperato le credenziali con successo.");

			if (utenteDaDB == null) {
				System.out.println("Utente non trovato a UtenteService.");
				throw new UserNotFoundException();
			}
			System.out.println("Utente trovato, assemblo DTO.");

			return new UtenteDTO(utenteDaDB);

		} catch (UserNotFoundException e) {
			throw new BusinessException("Credenziali errate!", HttpStatus.FORBIDDEN);
		} catch (IncorrectRegexException e) {
			System.out.println("Errore nel check regex a findByUsernameAndPassword " + e.getMessage());
			throw new BusinessException("Errore nel check regex a findByUsernameAndPassword " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Errore generico a findByUsernameAndPassword " + e.getMessage());
			throw e;
		}
	}

	public UtenteDTO doRegistration(UtenteDTO utenteDaRegistrareDTO) throws Exception {
		try {
			UtilityClass.regexCheckUnoFinoAQuattroCampi(THREE_REGEX_ARGUMENTS, LOGIN_REGEX_MAIL, LOGIN_REGEX_USR,
					LOGIN_REGEX_PSW, null, utenteDaRegistrareDTO.getEmail(), utenteDaRegistrareDTO.getUsername(),
					utenteDaRegistrareDTO.getPassword(), null);

			UtenteEntity utenteDaRegistrare = new UtenteEntity();
			try {
				utenteDaRegistrare = utenteDaRegistrareDTO.dtoToUtente();
			} catch (Exception e) {
				throw new Exception();
			}

			if (checkUserExists(utenteDaRegistrareDTO.getUsername(), utenteDaRegistrareDTO.getEmail())) {
				throw new NotValidException("Utente già registrato!");
			}
			if (!indirizzoService.checkIndirizziExist(utenteDaRegistrareDTO)) {
				throw new EmptyFieldsException("Indirizzi");
			}
			if (!materiaRepo.checkMaterieExist(utenteDaRegistrare.getMaterieIds())) {
				throw new NotValidException("La materia selezionata non esiste.");
			}
			System.out.println("Completato controllo indirizzi.");

			utenteRepo.saveUtente(utenteDaRegistrare);
			return new UtenteDTO(utenteDaRegistrare);

		} catch (MongoException e) {
			System.out.println("Eccezione Mongo a UtenteService ");
			throw new BusinessException("Eccezione Mongo a UtenteService " + e.getMessage());
		} catch (IncorrectRegexException e) {
			System.out.println("Errore nel check regex a doRegistration " + e.getMessage());
			throw new BusinessException("Errore nel check regex a doRegistration");
		} catch (NotValidException | EmptyFieldsException e) {
			System.out.println("Errore nei controlli a doRegistration " + e.getMessage());
			throw new BusinessException("Errore nei controlli" + e.getMessage());
		} catch (Exception e) {
			throw e;
		}
	}

	public SuperDTO findMaterieUtente(UtenteDTO utenteProfessore) throws Exception {
		try {
			System.out.println("Inizio controlli campi.");

			UtilityClass.regexCheckUnoFinoAQuattroCampi(ONE_REGEX_ARGUMENT, LOGIN_REGEX_USR, null, null, null,
					utenteProfessore.getUsername(), null, null, null);
			UtenteEntity utenteTrovatoDaDB = trovaUtenteInDB(utenteProfessore);
			UtilityClass.roleCheck(utenteTrovatoDaDB.getRole());

			if (utenteTrovatoDaDB.getIndirizziIds() == null) {
				System.out.println("Errore a findMateriePerProf a indirizziIds.");
				throw new EmptyFieldsException("IndirizziIds");
			}
			if (utenteTrovatoDaDB.getMaterieIds() == null) {
				System.out.println("Errore a findMateriePerProf a MaterieIds.");
				throw new EmptyFieldsException("MaterieIds");
			}
			System.out.println("Controlli campi superati.");

			if (utenteTrovatoDaDB.getRole() == Role.PROFESSORE) {
				List<MateriaStudentiDTO> materieTrovatePerProfessore = utenteSpringRepo
						.findMateriePerProfessore(utenteTrovatoDaDB.getUsername());
				if (materieTrovatePerProfessore == null || materieTrovatePerProfessore.size() == 0) {
					System.out.println("Nessuna materia trovata per professore.");
					throw new NotFoundException();
				}
				return new SuperDTO("Materie trovate per professore.", materieTrovatePerProfessore, HttpStatus.OK);
				
			} else {
				List<MateriaEntity> materieTrovatePerStudente = materiaRepo
						.findMaterieStudente(utenteTrovatoDaDB.getUsername()).orElseThrow(NotFoundException::new);
				if (materieTrovatePerStudente == null || materieTrovatePerStudente.size() == 0) {
					System.out.println("Nessuna materia trovata per professore.");
					throw new NotFoundException();
				}
				return new SuperDTO("Materie trovate per studente.", materieTrovatePerStudente, HttpStatus.OK);
			}
		} catch (IncorrectRegexException | UserNotFoundException | NotFoundException e) {
			throw new BusinessException("Errore a findMateriaPerProf ", e.getMessage());
		} catch (Exception e) {
			throw e;
		}
	}

	public UtenteEntity trovaUtenteInDB(UtenteDTO utenteDaTrovare) throws UserNotFoundException {
		try {
			UtenteEntity utenteTrovato = utenteRepo.findUtenteByUsername(utenteDaTrovare.getUsername());
			if (utenteTrovato == null) {
				throw new UserNotFoundException();
			}
			return utenteTrovato;
		} catch (UserNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	private boolean checkUserExists(String usernameDaControllare, String emailDaControllarea) {
		if ((utenteRepo.findUtenteByUsername(usernameDaControllare) != null)
				|| (utenteRepo.findUtenteByEmail(emailDaControllarea) != null)) {
			System.out.println("L'utente esiste già.");
			return true;
		}
		System.out.println("L'utente non esiste.");
		return false;
	}

}
