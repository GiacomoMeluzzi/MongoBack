package lepackage.mongo.services;

import org.springframework.stereotype.Service;

import lepackage.mongo.dto.UtenteDTO;
import lepackage.mongo.exceptions.BusinessException;
import lepackage.mongo.exceptions.EmptyFieldsException;
import lepackage.mongo.exceptions.IndirizzoNotFoundException;
import lepackage.mongo.exceptions.NotValidException;
import lepackage.mongo.implementations.IndirizzoRepositoryImpl;

@Service
public class IndirizzoService {

	private IndirizzoRepositoryImpl repo;

	public IndirizzoService(IndirizzoRepositoryImpl repo) {
		this.repo = repo;
	}

	public boolean checkIndirizziExist(UtenteDTO utenteDaControllareDTO) throws Exception {
		try {
		if (utenteDaControllareDTO.getIndirizziIds() == null || utenteDaControllareDTO.getIndirizziIds().length == 0) {
			System.out.println("Campo indirizziIds del DTO vuoto, impossibile proseguire con registrazione.");
			throw new EmptyFieldsException("indirizzi");
		}
		if (utenteDaControllareDTO.getRole().toString().equals("STUDENTE")
				&& utenteDaControllareDTO.getIndirizziIds().length > 1) {
			throw new NotValidException("Uno studente può iscriversi ad una sola facoltà.");
		}
		for (String indirizzoDaControllare : utenteDaControllareDTO.getIndirizziIds()) {
			System.out
					.println("Indirizzo in entrata in IndirizzoService per check esistenza: " + indirizzoDaControllare);
			repo.findIndirizzoById(indirizzoDaControllare).orElseThrow(IndirizzoNotFoundException::new);
			System.out.println("IndirizzoService ha validato l'esistenza dell'indirizzo.");
		}
		System.out.println("Controlli indirizzi in DB superati.");
		return true;
		} catch (EmptyFieldsException | NotValidException | IndirizzoNotFoundException e) {
			System.out.println("Errore a checkIndirizziExist " + e.getMessage());
			throw new BusinessException("Errore a checkIndirizziExist ", e.getMessage());
		} catch (Exception e) {
			System.out.println("Errore generico a checkIndirizziExist" + e.getMessage());
			throw e;
		}
	}
}
