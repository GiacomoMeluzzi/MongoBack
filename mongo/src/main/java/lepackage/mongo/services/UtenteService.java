package lepackage.mongo.services;

import static lepackage.mongo.utilities.Constants.LOGIN_REGEX_MAIL;
import static lepackage.mongo.utilities.Constants.LOGIN_REGEX_PSW;
import static lepackage.mongo.utilities.Constants.LOGIN_REGEX_USR;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import lepackage.mongo.documents.Utente;
import lepackage.mongo.dto.MateriaConIndirizzoDTO;
import lepackage.mongo.dto.UtenteDTO;
import lepackage.mongo.exceptions.NotValidException;
import lepackage.mongo.exceptions.UserNotFoundException;
import lepackage.mongo.repositories.UtenteRepository;
import lepackage.mongo.utilities.UtilityClass;

@Service
public class UtenteService {
	
	private UtenteRepository repo;
	
	public UtenteService(UtenteRepository repo) {
		this.repo = repo;
	}
	
	public UtenteDTO findByUsernameAndPassword(UtenteDTO utenteCredentials) throws Exception {
		try {
			UtilityClass.regexCheck(LOGIN_REGEX_MAIL, utenteCredentials.getEmail(), "email");
			UtilityClass.regexCheck(LOGIN_REGEX_PSW, utenteCredentials.getPassword(), "password");
			System.out.println("Regex a posto in login, vado a DB.");
			Utente utenteDaDB = repo.findByEmailAndPassword(utenteCredentials.getEmail(), utenteCredentials.getPassword());
			System.out.println("UtenteService ha recuperato le credenziali con successo.");
			if (utenteDaDB == null) {
				System.out.println("Utente non trovato a UtenteService.");
				throw new UserNotFoundException();
			}
			System.out.println("Utente trovato, assemblo DTO.");
			return new UtenteDTO(utenteDaDB);
		} 
		catch (UserNotFoundException e ) {
			System.out.println("UtenteService, utente non trovato");
	        throw new Exception("Errore generico in UtenteService", e);
		}
		catch (Exception e) {
	        System.out.println("UtenteService lancia un'eccezione generica. -> " + e.getMessage());
	        throw new Exception("Errore generico in UtenteService", e);
	    }
	}
	
	public boolean checkUserExists(String username, String email) throws Exception {
		try {
			if((repo.findByUsername(username) != null) || (repo.findByEmail(email) != null)) {
				System.out.println("L'utente esiste già.");
				return true;
			}
			System.out.println("L'utente non esiste.");
			return false;
		} catch (Exception e) {
			System.out.println("Errore generico in UtenteService al check esistenza utente " + e.getMessage());
			throw new Exception();
		}
	}
	
	public UtenteDTO doRegister(UtenteDTO utenteDTO) throws MongoException, Exception{
		try {
			UtilityClass.regexCheck(LOGIN_REGEX_MAIL, utenteDTO.getEmail(), "email");
			UtilityClass.regexCheck(LOGIN_REGEX_USR, utenteDTO.getUsername(), "username");
			UtilityClass.regexCheck(LOGIN_REGEX_PSW, utenteDTO.getPassword(), "password");
			Utente utenteDaRegistrare = utenteDTO.dtoToUtente();
			if(checkUserExists(utenteDTO.getUsername(), utenteDTO.getEmail())) {
				throw new NotValidException("Utente già registrato!");
			}
			repo.save(utenteDaRegistrare);
			return new UtenteDTO(utenteDaRegistrare);
		} catch (MongoException e) {
			System.out.println("Eccezione Mongo a UtenteService ");
			throw new MongoException(e.getMessage());
		}	
		catch (Exception e) {
			if (e instanceof MongoException || e instanceof NotValidException) {
				throw e;
			}
			System.out.println("Eccezione generica a UtenteService " + e.getMessage());
			throw new Exception();
		}
	}
	
	public List<MateriaConIndirizzoDTO> findMateriePerProf(UtenteDTO utenteDTO) throws Exception {	
		try {
			if (!checkUserExists(utenteDTO.getUsername(), utenteDTO.getEmail())) {
				throw new UserNotFoundException();
			}
			return repo.findMateriaPerProfessore(utenteDTO.getUsername());
		} catch (Exception e) {
			if (e instanceof UserNotFoundException) {
				throw e;
			}
			System.out.println("Eccezione generica a findMateriePerProf " + e.getMessage());
			throw new Exception();
		}
		
	}
	
}
