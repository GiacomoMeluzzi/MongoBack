package lepackage.mongo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lepackage.mongo.documents.Utente;
import lepackage.mongo.dto.UtenteDTO;
import lepackage.mongo.services.UtenteService;
import lepackage.mongo.varie.Role;

@RestController
@RequestMapping
("/user")
public class MainController {
	
	private UtenteService utenteService;

	
	public MainController (UtenteService utenteService) {
		this.utenteService = utenteService;
	}
	
	@PostMapping("/tryLogin")
	public ResponseEntity<?> tryLogin(@RequestBody UtenteDTO utenteDTO) {
		try {
			return new ResponseEntity<UtenteDTO>(utenteDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(("Errore generico " + e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
}
