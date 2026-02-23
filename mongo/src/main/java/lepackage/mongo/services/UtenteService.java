package lepackage.mongo.services;

import static lepackage.mongo.utilities.Constants.LOGIN_REGEX_MAIL;
import static lepackage.mongo.utilities.Constants.LOGIN_REGEX_PSW;
import static lepackage.mongo.utilities.Constants.LOGIN_REGEX_USR;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.MongoException;

import lepackage.mongo.documents.UtenteEntity;
import lepackage.mongo.dto.MateriaConIndirizzoDTO;
import lepackage.mongo.dto.UtenteDTO;
import lepackage.mongo.exceptions.EmptyFieldsException;
import lepackage.mongo.exceptions.NotValidException;
import lepackage.mongo.exceptions.UserNotFoundException;
import lepackage.mongo.implementations.UtenteRepositoryImpl;
import lepackage.mongo.utilities.UtilityClass;

@Service
public class UtenteService {

	private UtenteRepositoryImpl utenteRepo;
	private IndirizzoService indirizzoService;

	public UtenteService(UtenteRepositoryImpl utenteRepo,
			IndirizzoService indirizzoService) {
		this.utenteRepo = utenteRepo;
		this.indirizzoService = indirizzoService;
	}

	public UtenteDTO findByUsernameAndPassword(UtenteDTO credenzialiUtenteDaRegistrareDTO) throws Exception {
			try {
			UtilityClass.regexCheck(LOGIN_REGEX_MAIL, credenzialiUtenteDaRegistrareDTO.getEmail(), "email");
			UtilityClass.regexCheck(LOGIN_REGEX_PSW, credenzialiUtenteDaRegistrareDTO.getPassword(), "password");
			System.out.println("Regex a posto in login, vado a DB.");
			UtenteEntity utenteDaDB = utenteRepo.findUtenteByEmailAndPassword(credenzialiUtenteDaRegistrareDTO.getEmail(),
					credenzialiUtenteDaRegistrareDTO.getPassword());
			System.out.println("UtenteService ha recuperato le credenziali con successo.");
			if (utenteDaDB == null) {
				System.out.println("Utente non trovato a UtenteService.");
				throw new UserNotFoundException();
			}
			System.out.println("Utente trovato, assemblo DTO.");
			return new UtenteDTO(utenteDaDB);
			} 
			//business exception			
			catch (Exception e) {
				System.out.println("");
				throw e;
			}
	}

	@Transactional
	public UtenteDTO doRegister(UtenteDTO utenteDaRegistrareDTO) throws Exception {
		try {
			UtilityClass.regexCheck(LOGIN_REGEX_MAIL, utenteDaRegistrareDTO.getEmail(), "email");
			UtilityClass.regexCheck(LOGIN_REGEX_USR, utenteDaRegistrareDTO.getUsername(), "username");
			UtilityClass.regexCheck(LOGIN_REGEX_PSW, utenteDaRegistrareDTO.getPassword(), "password");
			UtenteEntity utenteDaRegistrare;
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
			System.out.println("Materie inserite, registro utente.");
			utenteRepo.saveUtente(utenteDaRegistrare);
			return new UtenteDTO(utenteDaRegistrare);
		} catch (MongoException e) {
			System.out.println("Eccezione Mongo a UtenteService ");
			throw new MongoException(e.getMessage());
		}
	}

	
	//da sistemare
	public List<MateriaConIndirizzoDTO> findMateriePerProf(UtenteDTO utenteProfessore) throws Exception {
		UtilityClass.regexCheck(LOGIN_REGEX_USR, utenteProfessore.getUsername(), "username");
		UtilityClass.roleCheck(utenteProfessore.getRole());
		if (utenteProfessore.getIndirizziIds() == null) {
			throw new EmptyFieldsException("IndirizziIds");
		}
		if (utenteProfessore.getMaterieIds() == null) {
			throw new EmptyFieldsException("MaterieIds");
		}
		if (!checkUserExists(utenteProfessore.getUsername(), utenteProfessore.getEmail())) {
			throw new UserNotFoundException();
		}
		return null;
	}

	private boolean checkUserExists(String usernameDaControllare, String emailDaControllarea) {
		if ((utenteRepo.findUtenteByUsername(usernameDaControllare) != null) || (utenteRepo.findUtenteByEmail(emailDaControllarea) != null)) {
			System.out.println("L'utente esiste già.");
			return true;
		}
		System.out.println("L'utente non esiste.");
		return false;
	}
}
