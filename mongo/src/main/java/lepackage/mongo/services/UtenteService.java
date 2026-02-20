package lepackage.mongo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lepackage.mongo.documents.Utente;
import lepackage.mongo.dto.UtenteDTO;
import lepackage.mongo.exceptions.UserNotFoundException;
import lepackage.mongo.repositories.UtenteRepository;

@Service
public class UtenteService {
	
	private UtenteRepository repo;
	
	public UtenteService(UtenteRepository repo) {
		this.repo = repo;
	}
	
	public UtenteDTO findByUsernameAndPassword(UtenteDTO utenteCredentials) throws Exception {
		try {
			Utente utente = repo.findByUsernameAndPassword(utenteCredentials.getUsername(), utenteCredentials.getPassword());
			if (utente == null) {
				throw new UserNotFoundException();
			}
			return new UtenteDTO(utente.getUsername(), utente.getRole());
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
}
