package lepackage.mongo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lepackage.mongo.dto.MateriaConIndirizzoDTO;
import lepackage.mongo.dto.MateriaDTO;
import lepackage.mongo.dto.SuperDTO;
import lepackage.mongo.dto.UtenteDTO;
import lepackage.mongo.exceptions.EmptyFieldsException;
import lepackage.mongo.exceptions.IncorrectRegexException;
import lepackage.mongo.exceptions.NotValidException;
import lepackage.mongo.exceptions.UserNotFoundException;
import lepackage.mongo.services.IndirizzoService;
import lepackage.mongo.services.MateriaService;
import lepackage.mongo.services.UtenteService;
import static lepackage.mongo.utilities.Constants.*;
import java.util.List;
import lepackage.mongo.utilities.UtilityClass;
import lepackage.mongo.varie.Role;

@RestController
@RequestMapping("/user")
public class MainController {

	// per debug, da sostituire con controllo filtro
	private boolean isLogged = true;

	private UtenteService utenteService;
	private IndirizzoService indirizzoService;
	private MateriaService materiaService;

	public MainController(UtenteService utenteService, IndirizzoService indirizzoService,
			MateriaService materiaService) {
		this.utenteService = utenteService;
		this.indirizzoService = indirizzoService;
		this.materiaService = materiaService;
	}

// fare oggetto dto generico
	@PostMapping("/tryLogin")
	public ResponseEntity<?> tryLogin(@RequestBody UtenteDTO utenteDTO) {
		try {
			return new ResponseEntity<UtenteDTO>(utenteService.findByUsernameAndPassword(utenteDTO), HttpStatus.OK);
		} catch (EmptyFieldsException e) {
			return new ResponseEntity<String>("tryLogin " + e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IncorrectRegexException e) {
			return new ResponseEntity<String>(
					"Le credenziali sono state inserite in un formato non supportato " + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("Credenziali errate.", HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<String>(("Errore generico a tryLogin " + e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/tryRegister")
	public ResponseEntity<SuperDTO> tryRegistration(@RequestBody UtenteDTO utenteDTO) {
		try {
			return new ResponseEntity<SuperDTO>(new SuperDTO("Registrazione effettuata.", utenteService.doRegister(utenteDTO)),
					HttpStatus.OK);
		} catch (EmptyFieldsException e) {
			System.out.println("Indirizzi lasciati vuoti.");
			return new ResponseEntity<SuperDTO>(new SuperDTO(null, "Errore, campo lasciato vuoto " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (IncorrectRegexException e) {
			return new ResponseEntity<SuperDTO>(new SuperDTO(null, "tryRegistration " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (NotValidException e) {
			return new ResponseEntity<SuperDTO>(new SuperDTO(null, "tryRegistration " + e.getMessage()),
					HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<SuperDTO>(
					new SuperDTO(null, "Errore generico in controller a tryRegistration. " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/selectMaterieUtente")
	public ResponseEntity<?> selectMaterieUtente(@RequestBody UtenteDTO utenteDTO) {
		if (!isLogged) {
			System.out.println("L'utente non è loggato, ritorna a pagina login.");
			return new ResponseEntity<String>("Login richiesto per accedere a questa pagina.", HttpStatus.FORBIDDEN);
		}
		try {
			UtilityClass.regexCheck(LOGIN_REGEX_USR, utenteDTO.getUsername(), "username");
			UtilityClass.roleCheck(utenteDTO.getRole());
			if (utenteDTO.getIndirizziIds() == null || utenteDTO.getIndirizziIds().length == 0) {
				System.out.println("Campo indirizziIds vuoto, impossibile prosegire con fetch materie");
				return new ResponseEntity<String>(
						"selectMaterieUtente, il campo indirizziIds ricevuto dal frontend è vuoto.",
						HttpStatus.BAD_REQUEST);
			}
			System.out.println("Check DTO completo.");
			if (utenteDTO.getRole() == Role.STUDENTE) {
				return new ResponseEntity<List<MateriaDTO>>(materiaService.findMaterieStudente(utenteDTO),
						HttpStatus.OK);
			} else if (utenteDTO.getRole() == Role.PROFESSORE) {
				return new ResponseEntity<List<MateriaConIndirizzoDTO>>(utenteService.findMateriePerProf(utenteDTO),
						HttpStatus.OK);
			}
			return null;
		} catch (EmptyFieldsException e) {
			return new ResponseEntity<String>("selectMaterieUtente " + e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IncorrectRegexException e) {
			return new ResponseEntity<String>("selectMaterieUtente " + e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("selectMaterieUtente " + e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>(
					("Errore generico in controller a selectMaterieUtente. " + e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

}
