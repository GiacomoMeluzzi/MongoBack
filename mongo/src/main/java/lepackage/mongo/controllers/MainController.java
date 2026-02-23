package lepackage.mongo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lepackage.mongo.dto.SuperDTO;
import lepackage.mongo.dto.UtenteDTO;
import lepackage.mongo.exceptions.EmptyFieldsException;
import lepackage.mongo.exceptions.IncorrectRegexException;
import lepackage.mongo.exceptions.NotValidException;
import lepackage.mongo.exceptions.UserNotFoundException;
import lepackage.mongo.services.MateriaService;
import lepackage.mongo.services.UtenteService;
import lepackage.mongo.varie.Role;

@RestController
@RequestMapping("/user")
public class MainController {

	// per debug, da sostituire con controllo filtro
	private boolean isLogged = true;

	private UtenteService utenteService;
	private MateriaService materiaService;

	public MainController(UtenteService utenteService, MateriaService materiaService) {
		this.utenteService = utenteService;
		this.materiaService = materiaService;
	}

	@PostMapping("/tryLogin")
	public SuperDTO tryLogin(@RequestBody UtenteDTO utenteDTO) {
		try {
			return new SuperDTO("Login effettuato!", utenteService.findByUsernameAndPassword(utenteDTO), HttpStatus.OK);
		} catch (EmptyFieldsException e) {
			return new SuperDTO("tryLogin " + e.getMessage(), null, HttpStatus.BAD_REQUEST);
		} catch (IncorrectRegexException e) {
			return new SuperDTO("Le credenziali sono state inserite in un formato non supportato " + e.getMessage(),
					null, HttpStatus.BAD_REQUEST);
		} catch (UserNotFoundException e) {
			return new SuperDTO("Credenziali errate.", null, HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new SuperDTO("Errore generico a tryLogin " + e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/tryRegister")
	public SuperDTO tryRegistration(@RequestBody UtenteDTO utenteDTO) {
		try {
			return new SuperDTO("Registrazione effettuata.", utenteService.doRegister(utenteDTO), HttpStatus.OK);
		} catch (EmptyFieldsException e) {
			System.out.println("Indirizzi lasciati vuoti.");
			return new SuperDTO("Errore, campo lasciato vuoto " + e.getMessage(), null, HttpStatus.BAD_REQUEST);
		} catch (IncorrectRegexException e) {
			return new SuperDTO("tryRegistration " + e.getMessage(), null, HttpStatus.BAD_REQUEST);
		} catch (NotValidException e) {
			return new SuperDTO("tryRegistration " + e.getMessage(), null, HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new SuperDTO("Errore generico in controller a tryRegistration. " + e.getMessage(), null,
					HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/selectMaterieUtente")
	public SuperDTO selectMaterieUtente(@RequestBody UtenteDTO utenteDTO) {
		if (!isLogged) {
			System.out.println("L'utente non è loggato, ritorna a pagina login.");
			return new SuperDTO("Login richiesto per accedere a questa pagina.", null, HttpStatus.FORBIDDEN);
		}
		try {
			if (utenteDTO.getRole() == Role.STUDENTE) {
				return new SuperDTO("Materie per studente trovate.", materiaService.findMaterieStudente(utenteDTO),
						HttpStatus.OK);
			}
			return new SuperDTO("Materie per professore trovate.", utenteService.findMateriePerProf(utenteDTO),
					HttpStatus.OK);
		} catch (EmptyFieldsException e) {
			return new SuperDTO("selectMaterieUtente " + e.getMessage(), null, HttpStatus.BAD_REQUEST);
		} catch (IncorrectRegexException e) {
			return new SuperDTO("selectMaterieUtente " + e.getMessage(), null, HttpStatus.BAD_REQUEST);
		} catch (UserNotFoundException e) {
			return new SuperDTO("selectMaterieUtente " + e.getMessage(), null, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new SuperDTO("Errore generico in controller a selectMaterieUtente. " + e.getMessage(), null,
					HttpStatus.BAD_REQUEST);
		}
	}

}
