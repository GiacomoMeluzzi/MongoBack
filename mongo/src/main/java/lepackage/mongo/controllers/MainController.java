package lepackage.mongo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lepackage.mongo.dto.MateriaDTO;
import lepackage.mongo.dto.SuperDTO;
import lepackage.mongo.dto.UtenteDTO;
import lepackage.mongo.exceptions.BusinessException;
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
		} catch (BusinessException e) {
			if (null == e.getError()) {
				return new SuperDTO("Errore a tryLogin.", null, HttpStatus.BAD_REQUEST);
			}
			return new SuperDTO("Credenziali errate!", null, HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new SuperDTO("Errore generico a tryLogin.", null, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/tryRegister")
	public SuperDTO doRegistration(@RequestBody UtenteDTO utenteDTO) {
		try {
			return new SuperDTO("Registrazione effettuata.", utenteService.doRegistration(utenteDTO), HttpStatus.OK);
		} catch (BusinessException e) {
			return new SuperDTO("Errore in controller a doRegistration.", null,
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new SuperDTO("Errore generico in controller a doRegistration.", null,
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
				System.out.println("Utente è studente.");
				return new SuperDTO("Materie per studente trovate.", materiaService.findMaterieStudente(utenteDTO),
						HttpStatus.OK);
			}
			System.out.println("Utente è professore.");
			return new SuperDTO("Materie per professore trovate.", utenteService.findMateriePerProf(utenteDTO),
					HttpStatus.OK);
		} catch (BusinessException e) {
			return new SuperDTO("Errore a selectMaterieUtente", null, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new SuperDTO("Errore generico in controller a selectMaterieUtente.", null,
					HttpStatus.BAD_REQUEST);
		}
	}

}
